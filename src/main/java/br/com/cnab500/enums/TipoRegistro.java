package br.com.cnab500.enums;

public enum TipoRegistro {
    HEADER("0"),
    DETALHE("1"),
    TRAILER("9");

    private final String codigo;

    TipoRegistro(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static TipoRegistro fromCodigo(String codigo) {
        for (TipoRegistro tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoRegistro desconhecido: '" + codigo + "'");
    }
}

