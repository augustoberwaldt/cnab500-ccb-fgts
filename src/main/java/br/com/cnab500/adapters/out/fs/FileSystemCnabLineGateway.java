package br.com.cnab500.adapters.out.fs;

import br.com.cnab500.application.port.out.CnabLineGatewayPort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileSystemCnabLineGateway implements CnabLineGatewayPort {

    @Override
    public List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    @Override
    public void writeAllLines(Path path, List<String> lines) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        Files.write(path, lines, StandardCharsets.UTF_8);
    }
}

