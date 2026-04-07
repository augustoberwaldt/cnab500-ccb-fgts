package br.com.cnab500.application.port.out;

import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.HeaderRegistro;

public interface Cnab500RecordParserPort {
    HeaderRegistro parseHeader(String line, int lineNum);

    DetalheRegistro parseDetalhe(String line, int lineNum);

    int lineSize();
}

