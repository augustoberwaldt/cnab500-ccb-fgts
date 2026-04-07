package br.com.cnab500.parser;

import br.com.cnab500.adapters.out.cnab.Cnab500FixedWidthRecordParser;
import br.com.cnab500.adapters.out.fs.FileSystemCnabLineGateway;
import br.com.cnab500.application.port.in.ParseCnab500UseCase;
import br.com.cnab500.application.port.out.CnabLineGatewayPort;
import br.com.cnab500.application.service.ParseCnab500Service;
import br.com.cnab500.model.CnabArquivo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Parser de arquivos CNAB 500 no leiaute CCB FGTS.
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 * var parser = new Cnab500Parser();
 * CnabArquivo arquivo = parser.parseFile(Path.of("arquivo.rem"));
 * System.out.println(arquivo.header().nomeCedente());
 * arquivo.detalhes().forEach(d -> System.out.println(d.nomeSacado()));
 * }</pre>
 */
public class Cnab500Parser {

    private final ParseCnab500UseCase parseUseCase;
    private final CnabLineGatewayPort lineGateway;

    public Cnab500Parser() {
        this(
            new ParseCnab500Service(new Cnab500FixedWidthRecordParser()),
            new FileSystemCnabLineGateway()
        );
    }

    public Cnab500Parser(ParseCnab500UseCase parseUseCase, CnabLineGatewayPort lineGateway) {
        this.parseUseCase = Objects.requireNonNull(parseUseCase, "parseUseCase é obrigatório");
        this.lineGateway = Objects.requireNonNull(lineGateway, "lineGateway é obrigatório");
    }

    public CnabArquivo parseFile(Path path) throws IOException {
        return parseLines(lineGateway.readAllLines(path));
    }

    public CnabArquivo parseLines(List<String> lines) {
        return parseUseCase.parseLines(lines);
    }
}
