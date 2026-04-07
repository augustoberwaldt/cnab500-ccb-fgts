package br.com.cnab500.generator;

import br.com.cnab500.adapters.out.cnab.Cnab500FixedWidthSerializer;
import br.com.cnab500.adapters.out.fs.FileSystemCnabLineGateway;
import br.com.cnab500.application.port.in.GenerateCnab500UseCase;
import br.com.cnab500.application.port.out.CnabLineGatewayPort;
import br.com.cnab500.application.service.GenerateCnab500Service;
import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.HeaderRegistro;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Cnab500Generator {

    private final Cnab500FixedWidthSerializer serializer;
    private final GenerateCnab500UseCase generateUseCase;
    private final CnabLineGatewayPort lineGateway;

    public Cnab500Generator() {
        this(
            new GenerateCnab500Service(new Cnab500FixedWidthSerializer()),
            new FileSystemCnabLineGateway()
        );
    }

    public Cnab500Generator(GenerateCnab500UseCase generateUseCase, CnabLineGatewayPort lineGateway) {
        this.serializer = new Cnab500FixedWidthSerializer();
        this.generateUseCase = Objects.requireNonNull(generateUseCase, "generateUseCase é obrigatório");
        this.lineGateway = Objects.requireNonNull(lineGateway, "lineGateway é obrigatório");
    }

    public String generateHeader(HeaderRegistro h, int sequencialRegistro) {
        return serializer.serializeHeader(h, sequencialRegistro);
    }

    public String generateDetalhe(DetalheRegistro d, int sequencialRegistro) {
        return serializer.serializeDetalhe(d, sequencialRegistro);
    }

    public String generateTrailer(int sequencialRegistro) {
        return serializer.serializeTrailer(sequencialRegistro);
    }

    public List<String> generate(HeaderRegistro header, List<DetalheRegistro> detalhes) {
        return generateUseCase.generate(header, detalhes);
    }

    public void writeToFile(String fileName, HeaderRegistro header, List<DetalheRegistro> detalhes) throws IOException {
        writeToFile(Path.of(fileName), header, detalhes);
    }

    public void writeToFile(Path path, HeaderRegistro header, List<DetalheRegistro> detalhes) throws IOException {
        Objects.requireNonNull(path, "path é obrigatório");
        var lines = generate(header, detalhes);
        lineGateway.writeAllLines(path, lines);
    }
}
