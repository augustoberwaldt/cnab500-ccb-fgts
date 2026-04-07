package br.com.cnab500.adapters.out.cnab;

import br.com.cnab500.application.port.out.Cnab500SerializerPort;
import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.HeaderRegistro;

import java.math.BigDecimal;
import java.util.Objects;

import static br.com.cnab500.util.CnabFormatter.alphaLeft;
import static br.com.cnab500.util.CnabFormatter.blanks;
import static br.com.cnab500.util.CnabFormatter.formatDate;
import static br.com.cnab500.util.CnabFormatter.formatMoney;
import static br.com.cnab500.util.CnabFormatter.numericRight;
import static br.com.cnab500.util.CnabFormatter.zeros;

public class Cnab500FixedWidthSerializer implements Cnab500SerializerPort {

    private static final int LINE_SIZE = 500;
    private static final String HEADER_ID = "0";
    private static final String DETALHE_ID = "1";
    private static final String TRAILER_ID = "9";

    private static final int HEADER_BRANCOS_FINAIS = 363;
    private static final int TRAILER_BRANCOS = 493;
    private static final int SEQUENCIAL_REGISTRO_SIZE = 6;

    @Override
    public String serializeHeader(HeaderRegistro header, int sequencialRegistro) {

        Objects.requireNonNull(header, "header é obrigatório");

        var sb = new StringBuilder(LINE_SIZE);
        appendHeaderIdentificacao(sb);
        appendHeaderCedente(sb, header);
        appendHeaderOperacao(sb, header);
        sb.append(blanks(HEADER_BRANCOS_FINAIS));
        sb.append(numericRight(sequencialRegistro, SEQUENCIAL_REGISTRO_SIZE));
        return sb.toString();
    }

    @Override
    public String serializeDetalhe(DetalheRegistro detalhe, int sequencialRegistro) {
        Objects.requireNonNull(detalhe, "detalhe é obrigatório");

        var sb = new StringBuilder(LINE_SIZE);
        appendDetalheIdentificacao(sb, detalhe);
        appendDetalheDatasEValores(sb, detalhe);
        appendDetalheSacado(sb, detalhe);
        appendDetalheFinalizacao(sb, detalhe, sequencialRegistro);
        return sb.toString();
    }

    @Override
    public String serializeTrailer(int sequencialRegistro) {
        var sb = new StringBuilder(LINE_SIZE);
        sb.append(TRAILER_ID);
        sb.append(blanks(TRAILER_BRANCOS));
        sb.append(numericRight(sequencialRegistro, SEQUENCIAL_REGISTRO_SIZE));
        return sb.toString();
    }

    @Override
    public int lineSize() {
        return LINE_SIZE;
    }

    private void appendHeaderIdentificacao(StringBuilder sb) {
        sb.append(HEADER_ID);
        sb.append("1");
        sb.append(alphaLeft("REMESSA", 7));
        sb.append(numericRight(1, 2));
        sb.append(alphaLeft("COBRANCA", 15));
    }

    private void appendHeaderCedente(StringBuilder sb, HeaderRegistro h) {
        sb.append(numericRight(h.codigoOriginador(), 20));
        sb.append(alphaLeft(h.nomeCedente(), 30));
        sb.append(numericRight(h.numeroBanco(), 3));
        sb.append(alphaLeft(h.nomeBanco(), 15));
        sb.append(formatDate(h.dataGravacao()));
        sb.append(blanks(5));
        sb.append(alphaLeft("SFR", 3));
    }

    private void appendHeaderOperacao(StringBuilder sb, HeaderRegistro h) {
        sb.append(numericRight(h.sequencialArquivo(), 7));
        sb.append(formatDate(h.dataCessao()));
        sb.append(alphaLeft(h.coobrigacao(), 1));
        sb.append(numericRight(h.naturezaOperacao(), 1));
        sb.append(alphaLeft(h.segmento(), 3));
        sb.append(numericRight(h.tipoCalculo(), 1));
    }

    private void appendDetalheIdentificacao(StringBuilder sb, DetalheRegistro d) {
        sb.append(DETALHE_ID);
        sb.append(numericRight(d.tipoInscricaoCedente().getCodigo(), 2));
        sb.append(numericRight(d.inscricaoCedente(), 14));
        sb.append(alphaLeft(d.numeroContrato(), 12));
        sb.append(zeros(3));
        sb.append(zeros(3));
        sb.append(blanks(7));
        sb.append(zeros(5));
        sb.append(alphaLeft(d.seuNumero(), 25));
        sb.append(zeros(11));
        sb.append(zeros(11));
        sb.append(zeros(1));
    }

    private void appendDetalheDatasEValores(StringBuilder sb, DetalheRegistro d) {

        sb.append(formatMoney(d.valorLiquidacao(), 13, 2));
        sb.append(formatDate(d.dataLiquidacao()));
        sb.append(zeros(8));
        sb.append(blanks(2));
        sb.append(zeros(3));
        sb.append(zeros(3));
        sb.append(zeros(3));
        sb.append(zeros(3));
        sb.append(numericRight(d.ocorrencia().getCodigo(), 2));
        sb.append(formatDate(d.dataVencimento()));
        sb.append(formatMoney(d.valorNominal(), 13, 2));
        sb.append(zeros(3));
        sb.append(zeros(5));
        sb.append(alphaLeft("", 8));
        sb.append(blanks(2));
        sb.append(numericRight(d.especieTitulo(), 2));
        sb.append(alphaLeft(d.identificacaoAceite(), 1));
        sb.append(formatDate(d.dataEmissao()));
        sb.append(zeros(2));
        sb.append(zeros(2));
        sb.append(zeros(13));
        sb.append(zeros(8));
        sb.append(zeros(13));
        sb.append(formatMoney(d.valorAquisicao(), 13, 2));
        sb.append(zeros(13));
        sb.append(numericRight(d.indice(), 3));
        sb.append(formatMoney(zeroIfNull(d.correcaoIndice()), 7, 4));
    }

    private void appendDetalheSacado(StringBuilder sb, DetalheRegistro d) {
        sb.append(numericRight(d.tipoInscricaoSacado().getCodigo(), 2));
        sb.append(numericRight(d.inscricaoSacado(), 14));
        sb.append(alphaLeft(d.nomeSacado(), 40));
        sb.append(alphaLeft(d.enderecoSacado(), 40));
        sb.append(alphaLeft("", 12));
        sb.append(numericRight(d.cepSacado(), 8));
        sb.append(zeros(8));
        sb.append(zeros(3));
        sb.append(zeros(2));
        sb.append(zeros(14));
        sb.append(alphaLeft("", 40));
        sb.append(zeros(13));
        sb.append(zeros(12));
        sb.append(zeros(14));
        sb.append(zeros(3));
    }

    private void appendDetalheFinalizacao(StringBuilder sb, DetalheRegistro d, int sequencialRegistro) {
        sb.append(alphaLeft(d.tipoAtivo(), 1));
        sb.append(blanks(4));
        sb.append(numericRight(sequencialRegistro, SEQUENCIAL_REGISTRO_SIZE));
    }

    private BigDecimal zeroIfNull(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}

