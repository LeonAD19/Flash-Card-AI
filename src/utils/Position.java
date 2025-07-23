package utils;

/**
 * Represents a position on the chess board.
 * Provides utility methods for position handling and validation.
 */
public class Position {
    private int row;
    private int col;

    /**
     * Constructs a new position with the specified row and column.
     *
     * @param row The row position (0-7).
     * @param col The column position (0-7).
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Constructs a new position from chess notation (e.g., "e4").
     *
     * @param notation The chess notation string (e.g., "e4", "A1").
     * @throws IllegalArgumentException if the notation is invalid.
     */
    public Position(String notation) {
        int[] coords = notationToCoords(notation);
        if (coords == null) {
            throw new IllegalArgumentException("Invalid chess notation: " + notation);
        }
        this.row = coords[0];
        this.col = coords[1];
    }

    /**
     * Gets the row position.
     *
     * @return The row (0-7).
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column position.
     *
     * @return The column (0-7).
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the row position.
     *
     * @param row The new row (0-7).
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets the column position.
     *
     * @param col The new column (0-7).
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Checks if this position is valid (within board bounds).
     *
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Converts this position to chess notation.
     *
     * @return The chess notation string (e.g., "e4").
     */
    public String toNotation() {
        if (!isValid()) {
            return null;
        }
        char fileChar = (char) ('A' + col);
        char rankChar = (char) ('1' + (7 - row));
        return "" + fileChar + rankChar;
    }

    /**
     * Converts chess notation to board coordinates.
     *
     * @param notation The chess notation string (e.g., "e4", "A1").
     * @return An array containing [row, col] coordinates, or null if invalid.
     */
    public static int[] notationToCoords(String notation) {
        if (notation == null || notation.length() != 2) {
            return null;
        }
        
        char fileChar = notation.toUpperCase().charAt(0);
        char rankChar = notation.charAt(1);
        
        // Validate file (A-H)
        if (fileChar < 'A' || fileChar > 'H') {
            return null;
        }
        
        // Validate rank (1-8)
        if (rankChar < '1' || rankChar > '8') {
            return null;
        }
        
        int col = fileChar - 'A'; // A=0, B=1, ..., H=7
        int row = 8 - Character.getNumericValue(rankChar); // 8=0, 7=1, ..., 1=7
        
        return new int[]{row, col};
    }

    /**
     * Calculates the distance between two positions.
     *
     * @param other The other position.
     * @return The Manhattan distance between the positions.
     */
    public int distanceTo(Position other) {
        return Math.abs(this.row - other.row) + Math.abs(this.col - other.col);
    }

    /**
     * Checks if this position equals another position.
     *
     * @param obj The object to compare with.
     * @return true if the positions are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }

    /**
     * Returns a hash code for this position.
     *
     * @return A hash code value.
     */
    @Override
    public int hashCode() {
        return row * 8 + col;
    }

    /**
     * Returns a string representation of this position.
     *
     * @return A string representing the position.
     */
    @Override
    public String toString() {
        return "Position{" + toNotation() + " (" + row + ", " + col + ")}";
    }
}