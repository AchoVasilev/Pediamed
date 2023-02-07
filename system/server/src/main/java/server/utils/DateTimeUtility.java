package server.utils;

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

        if (startDate.isBefore(LocalDateTime.parse(currentDate, formatter))) {
            throw new DateTimeException(DATE_IS_BEFORE);
        }

        if (endDate.isBefore(startDate)) {
            throw new DateTimeException(DATE_IS_AFTER);
        }
    }
}
