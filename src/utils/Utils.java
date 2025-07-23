package utils;

/**
 * Utility class for handling chess-related conversions and validations.
 */
public class Utils {

    /**
     * Converts a string like "E2" to a Position object.
     *
     * @param input A chess notation string (e.g., "E2")
     * @return A Position object, or null if invalid.
     */
    public static Position notationToPosition(String input) {
        if (input == null || input.length() != 2)
            return null;

        char file = Character.toUpperCase(input.charAt(0));
        char rank = input.charAt(1);

        int col = file - 'A';
        int row = 8 - Character.getNumericValue(rank); // row 0 = rank 8

        if (col < 0 || col > 7 || row < 0 || row > 7)
            return null;

        return new Position(row, col);
    }

    /**
     * Converts a Position object to chess notation (e.g., E2).
     *
     * @param pos A valid Position object
     * @return A string like "E2"
     */
    public static String positionToNotation(Position pos) {
        if (pos == null || !pos.isValid()) return null;

        char file = (char) ('A' + pos.getCol());
        int rank = 8 - pos.getRow();
        return String.valueOf(file) + rank;
    }

    /**
     * Checks if a string is in valid chess notation format (e.g., "A1" to "H8").
     *
     * @param s the input string
     * @return true if valid
     */
    public static boolean isValidNotation(String s) {
        if (s == null || s.length() != 2) return false;

        char file = Character.toUpperCase(s.charAt(0));
        char rank = s.charAt(1);

        return file >= 'A' && file <= 'H' && rank >= '1' && rank <= '8';
    }
}

