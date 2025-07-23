package pieces;

import java.util.List;

/**
 * Abstract base class for all chess pieces.
 * Defines common attributes and methods that all chess pieces share.
 */
public abstract class Piece {
    protected String color;
    protected int row;
    protected int col;

    /**
     * Constructs a new piece with the specified color and position.
     *
     * @param color The color of the piece ("white" or "black").
     * @param row The initial row position (0-7).
     * @param col The initial column position (0-7).
     */
    public Piece(String color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the color of this piece.
     *
     * @return The piece color ("white" or "black").
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the current row position of this piece.
     *
     * @return The row position (0-7).
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the current column position of this piece.
     *
     * @return The column position (0-7).
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the position of this piece.
     *
     * @param row The new row position (0-7).
     * @param col The new column position (0-7).
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Calculates all possible moves for this piece from its current position.
     * This method must be implemented by each concrete piece class.
     *
     * @param board The current board state.
     * @return A list of possible moves, where each move is represented as [row, col].
     */
    public abstract List<int[]> possibleMoves(Piece[][] board);

    /**
     * Checks if a position is within the board boundaries.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return true if the position is valid, false otherwise.
     */
    protected boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Checks if a square is empty or contains an enemy piece.
     *
     * @param board The game board.
     * @param row The row to check.
     * @param col The column to check.
     * @return true if the square is empty or contains an enemy piece, false otherwise.
     */
    protected boolean canMoveTo(Piece[][] board, int row, int col) {
        if (!isValidPosition(row, col)) {
            return false;
        }

        Piece targetPiece = board[row][col];
        return targetPiece == null || !targetPiece.getColor().equals(this.color);
    }

    /**
     * Checks if a square contains an enemy piece.
     *
     * @param board The game board.
     * @param row The row to check.
     * @param col The column to check.
     * @return true if the square contains an enemy piece, false otherwise.
     */
    protected boolean isEnemyPiece(Piece[][] board, int row, int col) {
        if (!isValidPosition(row, col)) {
            return false;
        }

        Piece targetPiece = board[row][col];
        return targetPiece != null && !targetPiece.getColor().equals(this.color);
    }

    /**
     * Checks if a square is empty.
     *
     * @param board The game board.
     * @param row The row to check.
     * @param col The column to check.
     * @return true if the square is empty, false otherwise.
     */
    protected boolean isEmpty(Piece[][] board, int row, int col) {
        if (!isValidPosition(row, col)) {
            return false;
        }

        return board[row][col] == null;
    }

    /**
     * Returns a string representation of this piece.
     *
     * @return A string in the format "color PieceType".
     */
    @Override
    public String toString() {
        return color + " " + this.getClass().getSimpleName();
    }

    /**
     * Checks if two pieces are equal based on their color, type, and position.
     *
     * @param obj The object to compare with.
     * @return true if the pieces are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Piece piece = (Piece) obj;
        return row == piece.row && col == piece.col && color.equals(piece.color);
    }

    /**
     * Generates a hash code for this piece.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return color.hashCode() + row * 8 + col;
    }
}

