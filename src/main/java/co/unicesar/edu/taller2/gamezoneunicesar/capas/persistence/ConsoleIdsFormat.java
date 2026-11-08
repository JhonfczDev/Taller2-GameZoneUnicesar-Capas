package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Format used in the accessories file to store the ids of the compatible
 * consoles in a single column, separated by "|".
 */
final class ConsoleIdsFormat {

    private static final String JOIN_SEPARATOR = "|";
    private static final String SPLIT_SEPARATOR = "\\|";

    private ConsoleIdsFormat() {
    }

    /**
     * Joins the console ids into the text stored in the file.
     *
     * @param consoleIds the ids to join
     * @return the joined text, or an empty text if there are no ids
     */
    static String join(List<String> consoleIds) {
        if (consoleIds == null || consoleIds.isEmpty()) {

            return "";
        }

        return String.join(JOIN_SEPARATOR, consoleIds);
    }

    /**
     * Splits the text stored in the file into the console ids.
     *
     * @param rawField the text read from the file
     * @return the list of ids, empty if the text is empty
     */
    static List<String> split(String rawField) {
        List<String> consoleIds = new ArrayList<>();
        if (rawField == null || rawField.isBlank()) {
            return consoleIds;
        }
        for (String consoleId : rawField.split(SPLIT_SEPARATOR)) {
            if (!consoleId.isBlank()) {
                consoleIds.add(consoleId.trim());
            }
        }
        return consoleIds;
    }
}
