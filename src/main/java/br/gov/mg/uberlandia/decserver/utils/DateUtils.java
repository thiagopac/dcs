package br.gov.mg.uberlandia.decserver.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String MONTH_DAY_DATE_FORMAT = "dd/MM";
    public static SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Retorna o mes corrente de 1 a 12.
     */
    public static int mesCorrente() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int anoCorrente() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static LocalDate firstDayOfMonth(int year, int month) {
        return YearMonth.of(year, month).atDay(1);
    }

    public static LocalDate lastDayOfMonth(int year, int month) {
        return YearMonth.of(year, month).atEndOfMonth();
    }

    public static String formatarData(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return localDate.format(formatter);
    }

    public static String formatarDataMesDia(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MONTH_DAY_DATE_FORMAT);
        return localDate.format(formatter);
    }

    public static Date convertToBrazilTimeZone(Date dateToConvert) {
        Instant instant = dateToConvert.toInstant();
        ZoneId brazilZone = ZoneId.of("America/Sao_Paulo");
        LocalDateTime localDateTimeBrazil = instant.atZone(brazilZone).toLocalDateTime();
        return Date.from(localDateTimeBrazil.atZone(brazilZone).toInstant());
    }

}
