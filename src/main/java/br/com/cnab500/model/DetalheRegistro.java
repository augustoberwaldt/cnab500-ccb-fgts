package br.com.cnab500.model;

import br.com.cnab500.enums.TipoInscricao;
import br.com.cnab500.enums.TipoOcorrencia;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DetalheRegistro(
    TipoInscricao tipoInscricaoCedente,
    String inscricaoCedente,         // CNPJ/CPF
    String numeroContrato,           // CCB - 12 pos
    String seuNumero,                // Identificação do título - 25 pos
    BigDecimal valorLiquidacao,      // 13 pos, 2 dec
    LocalDate dataLiquidacao,
    TipoOcorrencia ocorrencia,
    LocalDate dataVencimento,
    BigDecimal valorNominal,         // valor de face - 13 pos, 2 dec
    String especieTitulo,            // 2 pos - default "70"
    String identificacaoAceite,      // A ou N
    LocalDate dataEmissao,
    BigDecimal valorAquisicao,       // 13 pos, 2 dec
    int indice,                      // 3 pos
    BigDecimal correcaoIndice,       // 7 pos, 4 dec (pode ser null)
    TipoInscricao tipoInscricaoSacado,
    String inscricaoSacado,          // CNPJ/CPF
    String nomeSacado,               // 40 pos
    String enderecoSacado,           // 40 pos
    String cepSacado,                // 8 pos
    String tipoAtivo                 // P-Performado (pode ser null)
) {
    public DetalheRegistro {
        if (especieTitulo == null || especieTitulo.isBlank()) especieTitulo = "70";
        if (identificacaoAceite == null || identificacaoAceite.isBlank()) identificacaoAceite = "A";
        if (tipoAtivo == null) tipoAtivo = "P";
    }
}
