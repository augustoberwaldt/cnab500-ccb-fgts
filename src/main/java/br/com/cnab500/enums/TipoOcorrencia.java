package br.com.cnab500.enums;

public enum TipoOcorrencia {
    AQUISICAO("01", "Aquisição de direito creditório"),
    LIQUIDACAO_NORMAL("03", "Baixa total na data de vencimento ou em atraso"),
    BAIXA_RENEGOCIACAO("10", "Baixa total sem contrapartida financeira"),
    ENTRADA_RENEGOCIACAO("12", "Entrada de recebível sem desembolso financeiro"),
    LIQUIDACAO_ANTECIPADA("13", "Baixa total prévia ao vencimento"),
    LIQUIDACAO_PARCIAL("14", "Baixa parcial em qualquer data"),
    RESOLUCAO_FRAUDE("70", "Baixa total por fraude"),
    RESOLUCAO_CANCELAMENTO("71", "Baixa total por cancelamento"),
    BAIXA_OBITO("72", "Baixa total por óbito do devedor"),
    RECOMPRA("83", "Recompra com contrapartida do cedente original"),
    ALIENACAO_TERCEIROS("76", "Alienação para terceiros");

    private final String codigo;
    private final String descricao;

    TipoOcorrencia(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    public String getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }
}
