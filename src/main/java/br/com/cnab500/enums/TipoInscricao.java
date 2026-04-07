package br.com.cnab500.enums;

public enum TipoInscricao {
    CPF("01"),
    CNPJ("02");

    private final String codigo;

    TipoInscricao(String codigo) { this.codigo = codigo; }
    public String getCodigo() { return codigo; }
}
