package server.infrastructure.utils.guards;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Guard {
    public static class Against {
        public static String EmptyOrBlank(String parameter, String message) {
            Guard.Against.Null(parameter, message);
            if (parameter.isEmpty() || parameter.isBlank()) {
                throw new IllegalArgumentException(message);
            }

            return parameter;
        }

        public static String EmptyOrBlank(String parameter) {
            Guard.Against.Null(parameter);
            if (parameter.isEmpty() || parameter.isBlank()) {
                throw new IllegalArgumentException(String.format("Parameter %s cannot be empty or blank", parameter));
            }

            return parameter;
        }

        public static <T> T Null(T input, String message) {
            if (input == null) {
                throw new IllegalArgumentException(message);
            }

            return input;
        }

        public static <T> T Null(T input) {
            if (input == null) {
                throw new IllegalArgumentException("Parameter cannot be null");
            }

            return input;
        }

        public static UUID NullOrEmpty(UUID input, String message) {
            if (input == null) {
                throw new IllegalArgumentException(message);
            }

            return UUID.fromString(EmptyOrBlank(input.toString(), message));
        }

        public static UUID NullOrEmpty(UUID input) {
            if (input == null) {
                throw new IllegalArgumentException("Parameter cannot be null");
            }

            return UUID.fromString(EmptyOrBlank(input.toString()));
        }

        public static <T> Iterable<T> NullOrEmpty(Iterable<T> input) {
            Guard.Against.Null(input);
            if (!input.iterator().hasNext()) {
                throw new IllegalArgumentException("Parameter cannot be empty");
            }

            return input;
        }

        public static <T> Iterable<T> NullOrEmpty(Iterable<T> input, String message) {
            Guard.Against.Null(input);
            if (!input.iterator().hasNext()) {
                throw new IllegalArgumentException(message);
            }

            return input;
        }

        public static Integer Zero(Integer input, String message) {
            Guard.Against.Null(input, message);
            if (input <= 0) {
                throw new IllegalArgumentException(message);
            }

            return input;
        }

        public static Integer Zero(Integer input) {
            Guard.Against.Null(input);
            if (input <= 0) {
                throw new IllegalArgumentException(String.format("%s cannot be less or equal to zero", input));
            }

            return input;
        }

        public static BigDecimal Zero(BigDecimal input, String message) {
            Guard.Against.Null(input, message);
            if (input.compareTo(new BigDecimal(0)) <= 0) {
                throw new IllegalArgumentException(message);
            }

            return input;
        }

        public static BigDecimal Zero(BigDecimal input) {
            Guard.Against.Null(input);
            if (input.compareTo(new BigDecimal(0)) <= 0) {
                throw new IllegalArgumentException(String.format("%s cannot be less or equal to zero", input));
            }

            return input;
        }
    }
}
