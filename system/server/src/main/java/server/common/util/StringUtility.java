package server.common.util;

import java.util.List;

public class StringUtility {
    private static final List<String> characters = List.of("<", ">", "!", "%", "-", "/", "\\", "?", "$", ",", ".", "|");

    public static String sanitize(String string) {
        var result = "";
        for (String character : characters) {
            result = string.indexOf(character) > 0 ? string.replace(character, "") : string;
        }

        return result;
    }
}
