package br.com.cnab500.model;

import java.time.LocalDate;

public record HeaderRegistro(
    String codigoOriginador,     // 20 pos - 6 zeros + CNPJ do fundo
    String nomeCedente,          // 30 pos
    String numeroBanco,          // 3 pos
    String nomeBanco,            // 15 pos
    LocalDate dataGravacao,
    int sequencialArquivo,
    LocalDate dataCessao,
    String coobrigacao,          // S ou N
    int naturezaOperacao,
    String segmento,             // 3 pos
    int tipoCalculo
) {}
