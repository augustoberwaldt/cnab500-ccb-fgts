package br.com.cnab500.application.port.in;

import br.com.cnab500.model.CnabArquivo;

import java.util.List;

public interface ParseCnab500UseCase {
    CnabArquivo parseLines(List<String> lines);
}

