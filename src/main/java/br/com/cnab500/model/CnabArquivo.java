package br.com.cnab500.model;

import java.util.List;

/**
 * Representa um arquivo CNAB 500 parseado com header, detalhes e metadados do trailer.
 */
public record CnabArquivo(
    HeaderRegistro header,
    List<DetalheRegistro> detalhes,
    int totalRegistros
) {}
