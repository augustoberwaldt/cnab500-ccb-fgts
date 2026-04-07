package br.com.cnab500.enums;

public enum NaturezaOperacao {
    CESSAO(1),
    CONFIRMACAO(2);

    private final int codigo;

    NaturezaOperacao(int codigo) { this.codigo = codigo; }
    public int getCodigo() { return codigo; }
}
