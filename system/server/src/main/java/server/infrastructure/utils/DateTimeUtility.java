package server.infrastructure.utils;

import server.domain.entities.enums.CabinetWorkDays;
import server.infrastructure.config.exceptions.models.WorkDayException;
import server.infrastructure.utils.guards.Guard;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static server.common.ErrorMessages.DATE_IS_AFTER;
import static server.common.ErrorMessages.DATE_IS_BEFORE;
import static server.common.ErrorMessages.DATE_PASSED;
import static server.common.ErrorMessages.INVALID_WORKDAYS_FOR_CABINET;
import static server.common.ErrorMessages.NOT_SUPPORTED_DAY;

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

    public static void validateWorkDays(List<String> cabinetWorkDays, String startInput, String endInput) {
        Guard.Against.EmptyOrBlank(startInput);
        Guard.Against.EmptyOrBlank(endInput);

        transformWorkDay(startInput);
        transformWorkDay(endInput);

        if (!cabinetWorkDays.contains(startInput)) {
            throw new WorkDayException(String.format(INVALID_WORKDAYS_FOR_CABINET, startInput));
        } else if (!cabinetWorkDays.contains(endInput)) {
            throw new WorkDayException(String.format(INVALID_WORKDAYS_FOR_CABINET, startInput));
        }
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

    private static void transformWorkDay(String input) {
       if (DayOfWeek.MONDAY.name().equals(input)) {
           input = CabinetWorkDays.Понеделник.name();
       } else if (DayOfWeek.TUESDAY.name().equals(input)) {
            input = CabinetWorkDays.Вторник.name();
       } else if (DayOfWeek.WEDNESDAY.name().equals(input)) {
            input = CabinetWorkDays.Сряда.name();
       } else if (DayOfWeek.THURSDAY.name().equals(input)) {
            input = CabinetWorkDays.Четвъртък.name();
       } else if (DayOfWeek.FRIDAY.name().equals(input)) {
            input = CabinetWorkDays.Петък.name();
       } else if (DayOfWeek.SATURDAY.name().equals(input)) {
            input = CabinetWorkDays.Събота.name();
       } else if (DayOfWeek.SUNDAY.name().equals(input)) {
            input = CabinetWorkDays.Неделя.name();
       } else {
            throw new WorkDayException(NOT_SUPPORTED_DAY);
       }
    }
}
