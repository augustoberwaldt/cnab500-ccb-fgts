package br.com.cnab500.application.service;

import br.com.cnab500.application.port.in.ParseCnab500UseCase;
import br.com.cnab500.application.port.out.Cnab500RecordParserPort;
import br.com.cnab500.enums.TipoRegistro;
import br.com.cnab500.exeption.NotImplementedException;
import br.com.cnab500.model.CnabArquivo;
import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.HeaderRegistro;
import br.com.cnab500.parser.CnabParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParseCnab500Service implements ParseCnab500UseCase {

    private final Cnab500RecordParserPort parser;

    public ParseCnab500Service(Cnab500RecordParserPort parser) {
        this.parser = Objects.requireNonNull(parser, "parser é obrigatório");
    }

    @Override
    public CnabArquivo parseLines(List<String> lines) {

        validateInput(lines);

        HeaderRegistro header = null;
        List<DetalheRegistro> detalhes = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            int lineNum = i + 1;
            String line = lines.get(i);

            validateLineSize(line, lineNum);

            TipoRegistro tipo = resolveTipoRegistro(line, lineNum);

            switch (tipo) {
                case HEADER -> header = processHeader(header, line, lineNum);
                case DETALHE -> detalhes.add(processDetalhe(line, lineNum));
                case TRAILER -> processTrailer(line, lineNum);
            }
        }

        ensureHeaderExists(header);

        return new CnabArquivo(header, detalhes, lines.size());
    }

    // =========================
    // Validações
    // =========================

    private void validateInput(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new CnabParseException("Arquivo vazio ou nulo");
        }
    }

    private void validateLineSize(String line, int lineNum) {
        int expected = parser.lineSize();
        if (line.length() != expected) {
            throw new CnabParseException(
                    "Linha %d tem %d caracteres (esperado %d)"
                            .formatted(lineNum, line.length(), expected)
            );
        }
    }

    private void ensureHeaderExists(HeaderRegistro header) {
        if (header == null) {
            throw new CnabParseException("Header (tipo 0) não encontrado");
        }
    }

    // =========================
    // Processamento
    // =========================

    private TipoRegistro resolveTipoRegistro(String line, int lineNum) {
        String tipoCodigo = line.substring(0, 1);
        try {
            return TipoRegistro.fromCodigo(tipoCodigo);
        } catch (IllegalArgumentException e) {
            throw new CnabParseException(
                    "Tipo desconhecido '%s' na linha %d".formatted(tipoCodigo, lineNum),
                    e
            );
        }
    }

    private HeaderRegistro processHeader(HeaderRegistro currentHeader, String line, int lineNum) {
        if (currentHeader != null) {
            throw new CnabParseException("Múltiplos headers (linha %d)".formatted(lineNum));
        }
        return parser.parseHeader(line, lineNum);
    }

    private DetalheRegistro processDetalhe(String line, int lineNum) {
        return parser.parseDetalhe(line, lineNum);
    }

    private void processTrailer(String line, int lineNum) {
        // Hook para futura validação de trailer
        throw new NotImplementedException("Not Implemented Exception");
    }
}