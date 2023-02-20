package server.constants;

public class ErrorMessages {
    public static final String EMAIL_ALREADY_EXISTS = "Потребител с имейл %s съществува";
    public static final String REQUIRED_FIELD = "Полето е задължително!";
    public static final String INVALID_EMAIL = "Въведете валиден имейл!";
    public static final String INVALID_CREDENTIALS = "Невалидни имейл или парола";
    public static final String ENTITY_NOT_FOUND = "Липсват данни";
    public static final String CABINET_NOT_FOUND = "Този кабинет не съществува";
    public static final String SCHEDULE_NOT_FOUND = "Този график не съществува";
    public static final String MINIMUM_LENGTH_REQUIRED = "Полето трябва да е с минимална дължина от 4 символа";
    public static final String PHONE_FIELD_LENGTH = "Полето трябва да е с дължина между 10 и 13 символа";
    public static final String DATE_IS_BEFORE = "Не може да създавате часове с изминали дата/час";
    public static final String DATE_IS_AFTER = "Не може крайната дата/час да е преди началната";
    public static final String EVENTS_NOT_GENERATED = "Създаването на нови часове е неуспешно";
    public static final String EVENTS_GENERATED = "Успешно генерирахте часове от %s до %s с интервал %s";
}
