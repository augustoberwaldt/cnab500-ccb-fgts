package br.com.cnab500.parser;

public class CnabParseException extends RuntimeException {
    public CnabParseException(String message) { super(message); }
    public CnabParseException(String message, Throwable cause) { super(message, cause); }
}
