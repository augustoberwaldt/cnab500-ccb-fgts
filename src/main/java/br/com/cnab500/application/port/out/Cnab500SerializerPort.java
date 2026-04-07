package br.com.cnab500.application.port.out;

import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.HeaderRegistro;

public interface Cnab500SerializerPort {

    String serializeHeader(HeaderRegistro header, int sequencialRegistro);

    String serializeDetalhe(DetalheRegistro detalhe, int sequencialRegistro);

    String serializeTrailer(int sequencialRegistro);

    int lineSize();
}

