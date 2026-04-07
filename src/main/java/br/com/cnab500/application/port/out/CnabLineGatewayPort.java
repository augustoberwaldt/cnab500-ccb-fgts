package br.com.cnab500.application.port.out;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface CnabLineGatewayPort {
    List<String> readAllLines(Path path) throws IOException;

    void writeAllLines(Path path, List<String> lines) throws IOException;
}

