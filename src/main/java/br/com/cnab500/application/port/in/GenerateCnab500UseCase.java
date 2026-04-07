package br.com.cnab500.application.port.in;

import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.HeaderRegistro;

import java.util.List;

public interface GenerateCnab500UseCase {
    List<String> generate(HeaderRegistro header, List<DetalheRegistro> detalhes);
}

