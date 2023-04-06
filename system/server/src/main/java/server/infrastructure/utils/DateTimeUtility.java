package server.infrastructure.utils;

import server.infrastructure.utils.guards.Guard;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static server.common.ErrorMessages.DATE_IS_AFTER;
import static server.common.ErrorMessages.DATE_IS_BEFORE;
import static server.common.ErrorMessages.DATE_PASSED;

public class DateTimeUtility {
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern(DATE_TIME_FORMAT, new Locale("bg", "BG"));

    public static LocalDateTime parseDate(String dateTime) {
        var formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        var currentDate = LocalDateTime.now().format(dateTimeFormatter);

        Guard.Against.Null(startDate);
        Guard.Against.Null(endDate);
        if (startDate.isBefore(LocalDateTime.parse(currentDate, dateTimeFormatter))) {
            throw new DateTimeException(DATE_PASSED);
        } else if (startDate.isAfter(endDate)) {
            throw new DateTimeException(DATE_IS_AFTER);
        }

        if (endDate.isBefore(startDate)) {
            throw new DateTimeException(DATE_IS_BEFORE);
        }
    }

    public static void validateWorkDays(List<String> cabinetWorkDays, List<String> eventInputDays) {

    }

    public static LocalDateTime validateStartDate(LocalDateTime startDate, LocalDateTime endDate) {
        Guard.Against.Null(startDate);
        if (startDate.isAfter(endDate)) {
            throw new DateTimeException(DATE_IS_AFTER);
        }

        return validateForCurrentDate(startDate);
    }

    public static LocalDateTime validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        Guard.Against.Null(endDate);
        if (endDate.isBefore(startDate)) {
            throw new DateTimeException(DATE_IS_BEFORE);
        }

        return validateForCurrentDate(endDate);
    }

    private static LocalDateTime validateForCurrentDate(LocalDateTime dateTime) {
        var currentDate = LocalDateTime.now().format(dateTimeFormatter);
        if (dateTime.isBefore(LocalDateTime.parse(currentDate, dateTimeFormatter))) {
            throw new DateTimeException(DATE_PASSED);
        }

        return dateTime;
    }
}
