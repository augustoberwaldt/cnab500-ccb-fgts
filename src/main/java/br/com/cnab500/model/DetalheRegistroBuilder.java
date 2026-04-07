package br.com.cnab500.model;

import br.com.cnab500.enums.TipoInscricao;
import br.com.cnab500.enums.TipoOcorrencia;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DetalheRegistroBuilder {

    private TipoInscricao tipoInscricaoCedente;
    private String inscricaoCedente;
    private String numeroContrato;
    private String seuNumero;
    private BigDecimal valorLiquidacao = BigDecimal.ZERO;
    private LocalDate dataLiquidacao;
    private TipoOcorrencia ocorrencia;
    private LocalDate dataVencimento;
    private BigDecimal valorNominal = BigDecimal.ZERO;
    private String especieTitulo = "70";
    private String identificacaoAceite = "A";
    private LocalDate dataEmissao;
    private BigDecimal valorAquisicao = BigDecimal.ZERO;
    private int indice = 0;
    private BigDecimal correcaoIndice;
    private TipoInscricao tipoInscricaoSacado;
    private String inscricaoSacado;
    private String nomeSacado;
    private String enderecoSacado;
    private String cepSacado;
    private String tipoAtivo = "P";

    public DetalheRegistroBuilder cedente(TipoInscricao tipo, String inscricao) {
        this.tipoInscricaoCedente = tipo;
        this.inscricaoCedente = inscricao;
        return this;
    }

    public DetalheRegistroBuilder contrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
        return this;
    }

    public DetalheRegistroBuilder seuNumero(String seuNumero) {
        this.seuNumero = seuNumero;
        return this;
    }

    public DetalheRegistroBuilder liquidacao(BigDecimal valor, LocalDate data) {
        this.valorLiquidacao = valor;
        this.dataLiquidacao = data;
        return this;
    }

    public DetalheRegistroBuilder ocorrencia(TipoOcorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
        return this;
    }

    public DetalheRegistroBuilder vencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public DetalheRegistroBuilder valorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
        return this;
    }

    public DetalheRegistroBuilder especieTitulo(String especieTitulo) {
        this.especieTitulo = especieTitulo;
        return this;
    }

    public DetalheRegistroBuilder aceite(String identificacaoAceite) {
        this.identificacaoAceite = identificacaoAceite;
        return this;
    }

    public DetalheRegistroBuilder emissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
        return this;
    }

    public DetalheRegistroBuilder valorAquisicao(BigDecimal valorAquisicao) {
        this.valorAquisicao = valorAquisicao;
        return this;
    }

    public DetalheRegistroBuilder indice(int indice) {
        this.indice = indice;
        return this;
    }

    public DetalheRegistroBuilder correcaoIndice(BigDecimal correcaoIndice) {
        this.correcaoIndice = correcaoIndice;
        return this;
    }

    public DetalheRegistroBuilder sacado(TipoInscricao tipo, String inscricao, String nome) {
        this.tipoInscricaoSacado = tipo;
        this.inscricaoSacado = inscricao;
        this.nomeSacado = nome;
        return this;
    }

    public DetalheRegistroBuilder enderecoSacado(String endereco, String cep) {
        this.enderecoSacado = endereco;
        this.cepSacado = cep;
        return this;
    }

    public DetalheRegistroBuilder tipoAtivo(String tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
        return this;
    }

    public DetalheRegistro build() {
        validate();
        return new DetalheRegistro(
            tipoInscricaoCedente, inscricaoCedente, numeroContrato, seuNumero,
            valorLiquidacao, dataLiquidacao, ocorrencia, dataVencimento,
            valorNominal, especieTitulo, identificacaoAceite, dataEmissao,
            valorAquisicao, indice, correcaoIndice,
            tipoInscricaoSacado, inscricaoSacado, nomeSacado,
            enderecoSacado, cepSacado, tipoAtivo
        );
    }

    private void validate() {
        if (tipoInscricaoCedente == null) throw new IllegalStateException("tipoInscricaoCedente é obrigatório. Use cedente()");
        if (inscricaoCedente == null) throw new IllegalStateException("inscricaoCedente é obrigatório. Use cedente()");
        if (numeroContrato == null) throw new IllegalStateException("numeroContrato é obrigatório. Use contrato()");
        if (seuNumero == null) throw new IllegalStateException("seuNumero é obrigatório. Use seuNumero()");
        if (ocorrencia == null) throw new IllegalStateException("ocorrencia é obrigatória. Use ocorrencia()");
        if (dataVencimento == null) throw new IllegalStateException("dataVencimento é obrigatório. Use vencimento()");
        if (dataEmissao == null) throw new IllegalStateException("dataEmissao é obrigatória. Use emissao()");
        if (tipoInscricaoSacado == null) throw new IllegalStateException("tipoInscricaoSacado é obrigatório. Use sacado()");
        if (inscricaoSacado == null) throw new IllegalStateException("inscricaoSacado é obrigatório. Use sacado()");
        if (nomeSacado == null) throw new IllegalStateException("nomeSacado é obrigatório. Use sacado()");
        if (enderecoSacado == null) throw new IllegalStateException("enderecoSacado é obrigatório. Use enderecoSacado()");
        if (cepSacado == null) throw new IllegalStateException("cepSacado é obrigatório. Use enderecoSacado()");
    }

    public static DetalheRegistroBuilder builder() {
        return new DetalheRegistroBuilder();
    }
}
