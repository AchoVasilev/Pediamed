package server.utils;

import server.utils.guards.Guard;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static server.constants.ErrorMessages.DATE_IS_AFTER;
import static server.constants.ErrorMessages.DATE_IS_BEFORE;

public class DateTimeUtility {
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

    public static LocalDateTime parseDate(String dateTime) {
        var formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        var formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, new Locale("bg", "BG"));
        var currentDate = LocalDateTime.now().format(formatter);

        Guard.Against.Null(startDate);
        Guard.Against.Null(endDate);
        if (startDate.isBefore(LocalDateTime.parse(currentDate, formatter))) {
            throw new DateTimeException(DATE_IS_BEFORE);
        }
        else if (startDate.isBefore(endDate)) {
            throw new DateTimeException(DATE_IS_BEFORE);
        }

        if (endDate.isAfter(startDate)) {
            throw new DateTimeException(DATE_IS_AFTER);
        }
    }

    public static LocalDateTime validateStartDate(LocalDateTime startDate, LocalDateTime endDate) {
        Guard.Against.Null(startDate);
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        return startDate;
    }

    public static LocalDateTime validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        Guard.Against.Null(endDate);
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        return startDate;
    }
}
