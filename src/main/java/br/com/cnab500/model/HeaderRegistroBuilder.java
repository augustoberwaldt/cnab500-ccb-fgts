package br.com.cnab500.model;

import java.time.LocalDate;

public class HeaderRegistroBuilder {

    private String codigoOriginador;
    private String nomeCedente;
    private String numeroBanco;
    private String nomeBanco;
    private LocalDate dataGravacao;
    private int sequencialArquivo;
    private LocalDate dataCessao;
    private String coobrigacao = "S";
    private int naturezaOperacao;
    private String segmento;
    private int tipoCalculo;

    public HeaderRegistroBuilder codigoOriginador(String codigoOriginador) {
        this.codigoOriginador = codigoOriginador;
        return this;
    }

    public HeaderRegistroBuilder nomeCedente(String nomeCedente) {
        this.nomeCedente = nomeCedente;
        return this;
    }

    public HeaderRegistroBuilder numeroBanco(String numeroBanco) {
        this.numeroBanco = numeroBanco;
        return this;
    }

    public HeaderRegistroBuilder nomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
        return this;
    }

    public HeaderRegistroBuilder dataGravacao(LocalDate dataGravacao) {
        this.dataGravacao = dataGravacao;
        return this;
    }

    public HeaderRegistroBuilder sequencialArquivo(int sequencialArquivo) {
        this.sequencialArquivo = sequencialArquivo;
        return this;
    }

    public HeaderRegistroBuilder dataCessao(LocalDate dataCessao) {
        this.dataCessao = dataCessao;
        return this;
    }

    public HeaderRegistroBuilder coobrigacao(String coobrigacao) {
        this.coobrigacao = coobrigacao;
        return this;
    }

    public HeaderRegistroBuilder naturezaOperacao(int naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
        return this;
    }

    public HeaderRegistroBuilder segmento(String segmento) {
        this.segmento = segmento;
        return this;
    }

    public HeaderRegistroBuilder tipoCalculo(int tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
        return this;
    }

    public HeaderRegistro build() {
        validate();
        return new HeaderRegistro(
            codigoOriginador,
            nomeCedente,
            numeroBanco,
            nomeBanco,
            dataGravacao,
            sequencialArquivo,
            dataCessao,
            coobrigacao,
            naturezaOperacao,
            segmento,
            tipoCalculo
        );
    }

    private void validate() {
        if (codigoOriginador == null) throw new IllegalStateException("codigoOriginador é obrigatório");
        if (nomeCedente == null) throw new IllegalStateException("nomeCedente é obrigatório");
        if (numeroBanco == null) throw new IllegalStateException("numeroBanco é obrigatório");
        if (nomeBanco == null) throw new IllegalStateException("nomeBanco é obrigatório");
        if (dataGravacao == null) throw new IllegalStateException("dataGravacao é obrigatória");
        if (dataCessao == null) throw new IllegalStateException("dataCessao é obrigatória");
        if (segmento == null) throw new IllegalStateException("segmento é obrigatório");
    }

    public static HeaderRegistroBuilder builder() {
        return new HeaderRegistroBuilder();
    }
}

