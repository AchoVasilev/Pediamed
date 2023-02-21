package server.utils.guards;

import java.util.UUID;

public abstract class Guard {
    public static class Against {
        public static String EmptyOrBlank(String parameter, String message) {
            if (parameter.isEmpty() || parameter.isBlank()) {
                throw new IllegalArgumentException(message);
            }

            return parameter;
        }

        public static String EmptyOrBlank(String parameter) {
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
    }
}
