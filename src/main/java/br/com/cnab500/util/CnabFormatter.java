package br.com.cnab500.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class CnabFormatter {

    private static final DateTimeFormatter FMT_DDMMAAAA = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final DateTimeFormatter FMT_AAAAMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    private CnabFormatter() {}

    public static String alphaLeft(String value, int size) {
        if (value == null) value = "";
        value = removeSpecialChars(value.toUpperCase());
        if (value.length() > size) return value.substring(0, size);
        return String.format("%-" + size + "s", value);
    }

    public static String numericRight(long value, int size) {
        String s = String.valueOf(value);
        if (s.length() > size) return s.substring(s.length() - size);
        return String.format("%" + size + "s", s).replace(' ', '0');
    }

    public static String numericRight(String value, int size) {
        if (value == null) value = "";
        value = value.replaceAll("[^0-9]", "");
        if (value.length() > size) return value.substring(value.length() - size);
        return String.format("%" + size + "s", value).replace(' ', '0');
    }

    public static String zeros(int size) {
        return "0".repeat(size);
    }

    public static String blanks(int size) {
        return " ".repeat(size);
    }

    public static String formatDate(LocalDate date) {
        if (date == null) return zeros(8);
        return date.format(FMT_DDMMAAAA);
    }

    public static String formatDateYMD(LocalDate date) {
        if (date == null) return zeros(8);
        return date.format(FMT_AAAAMMDD);
    }

    public static String formatMoney(BigDecimal value, int intSize, int decSize) {
        if (value == null) value = BigDecimal.ZERO;
        long cents = value.movePointRight(decSize).longValue();
        return numericRight(Math.abs(cents), intSize);
    }

    private static String removeSpecialChars(String s) {
        return s.replaceAll("[^A-Z0-9 /\\-.]", "")
                .replace("Ç", "C").replace("Ã", "A").replace("Á", "A")
                .replace("É", "E").replace("Ê", "E").replace("Í", "I")
                .replace("Ó", "O").replace("Ô", "O").replace("Ú", "U");
    }

    public static String buildFileName(String cnpj, LocalDate data, String tipo, int sequencial) {
        return cnpj.replaceAll("[^0-9]", "") + "_" +
               data.format(FMT_AAAAMMDD) + "_" +
               tipo + "_" +
               String.format("%03d", sequencial) + ".REM";
    }
}
