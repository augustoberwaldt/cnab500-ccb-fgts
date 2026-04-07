package br.com.cnab500.adapters.out.cnab;

import br.com.cnab500.application.port.out.Cnab500RecordParserPort;
import br.com.cnab500.enums.TipoInscricao;
import br.com.cnab500.enums.TipoOcorrencia;
import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.HeaderRegistro;
import br.com.cnab500.parser.CnabParseException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Cnab500FixedWidthRecordParser implements Cnab500RecordParserPort {

    private static final int LINE_SIZE = 500;
    private static final DateTimeFormatter FMT_DDMMAAAA = DateTimeFormatter.ofPattern("ddMMyyyy");

    @Override
    public HeaderRegistro parseHeader(String line, int lineNum) {
        try {
            return new HeaderRegistro(
                line.substring(26, 46).trim(),
                line.substring(46, 76).trim(),
                line.substring(76, 79).trim(),
                line.substring(79, 94).trim(),
                parseDate(line.substring(94, 102)),
                parseInt(line.substring(110, 117)),
                parseDate(line.substring(117, 125)),
                line.substring(125, 126).trim(),
                parseInt(line.substring(126, 127)),
                line.substring(127, 130).trim(),
                parseInt(line.substring(130, 131))
            );
        } catch (CnabParseException e) {
            throw e;
        } catch (Exception e) {
            throw new CnabParseException("Erro no header linha %d: %s".formatted(lineNum, e.getMessage()), e);
        }
    }

    @Override
    public DetalheRegistro parseDetalhe(String line, int lineNum) {
        try {
            return new DetalheRegistro(
                parseTipoInscricao(line.substring(1, 3)),
                line.substring(3, 17).trim(),
                line.substring(17, 29).trim(),
                line.substring(47, 72).trim(),
                parseMoney(line.substring(95, 108), 2),
                parseDate(line.substring(108, 116)),
                parseTipoOcorrencia(line.substring(138, 140)),
                parseDate(line.substring(140, 148)),
                parseMoney(line.substring(148, 161), 2),
                line.substring(179, 181).trim(),
                line.substring(181, 182).trim(),
                parseDate(line.substring(182, 190)),
                parseMoney(line.substring(228, 241), 2),
                parseInt(line.substring(254, 257)),
                parseMoney(line.substring(257, 264), 4),
                parseTipoInscricao(line.substring(264, 266)),
                line.substring(266, 280).trim(),
                line.substring(280, 320).trim(),
                line.substring(320, 360).trim(),
                line.substring(372, 380).trim(),
                line.substring(489, 490).trim()
            );
        } catch (CnabParseException e) {
            throw e;
        } catch (Exception e) {
            throw new CnabParseException("Erro no detalhe linha %d: %s".formatted(lineNum, e.getMessage()), e);
        }
    }

    @Override
    public int lineSize() {
        return LINE_SIZE;
    }

    private LocalDate parseDate(String s) {
        if (s == null || s.isBlank() || s.equals("0".repeat(s.length()))) {
            return null;
        }
        try {
            return LocalDate.parse(s.trim(), FMT_DDMMAAAA);
        } catch (DateTimeParseException e) {
            throw new CnabParseException("Data inválida: '%s'".formatted(s), e);
        }
    }

    private int parseInt(String s) {
        if (s == null || s.isBlank() || s.equals("0".repeat(s.length()))) {
            return 0;
        }
        return Integer.parseInt(s.trim());
    }

    private BigDecimal parseMoney(String s, int dec) {
        if (s == null || s.isBlank() || s.equals("0".repeat(s.length()))) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(Long.parseLong(s.trim()), dec);
    }

    private TipoInscricao parseTipoInscricao(String cod) {
        return switch (cod.trim()) {
            case "01" -> TipoInscricao.CPF;
            case "02" -> TipoInscricao.CNPJ;
            default -> throw new CnabParseException("TipoInscricao desconhecido: '%s'".formatted(cod));
        };
    }

    private TipoOcorrencia parseTipoOcorrencia(String cod) {
        String codigo = cod.trim();
        for (TipoOcorrencia tipo : TipoOcorrencia.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new CnabParseException("TipoOcorrencia desconhecido: '%s'".formatted(cod));
    }
}

