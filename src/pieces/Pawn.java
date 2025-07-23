package pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pawn piece in chess.
 * Pawns move forward one square, or two squares on their first move.
 * They capture diagonally forward.
 */
public class Pawn extends Piece {
    private boolean hasMoved;

    /**
     * Constructs a new Pawn with the specified color and position.
     *
     * @param color The color of the pawn ("white" or "black").
     * @param row The initial row position (0-7).
     * @param col The initial column position (0-7).
     */
    public Pawn(String color, int row, int col) {
        super(color, row, col);
        this.hasMoved = false;
    }

    /**
     * Calculates all possible moves for this pawn from its current position.
     * Includes forward moves and diagonal captures.
     *
     * @param board The current board state.
     * @return A list of possible moves, where each move is represented as [row, col].
     */
    @Override
    public List<int[]> possibleMoves(Piece[][] board) {
        List<int[]> moves = new ArrayList<>();
        
        // Direction of movement depends on color
        int direction = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;
        
        // Forward moves
        addForwardMoves(board, moves, direction, startRow);
        
        // Diagonal captures
        addDiagonalCaptures(board, moves, direction);
        
        return moves;
    }

    /**
     * Adds forward moves to the list of possible moves.
     *
     * @param board The current board state.
     * @param moves The list to add moves to.
     * @param direction The direction of movement (-1 for white, 1 for black).
     * @param startRow The starting row for this color.
     */
    private void addForwardMoves(Piece[][] board, List<int[]> moves, int direction, int startRow) {
        int newRow = row + direction;
        
        // One square forward
        if (isValidPosition(newRow, col) && isEmpty(board, newRow, col)) {
            moves.add(new int[]{newRow, col});
            
            // Two squares forward (only if on starting row and path is clear)
            if (row == startRow) {
                int twoSquaresRow = row + (2 * direction);
                if (isValidPosition(twoSquaresRow, col) && isEmpty(board, twoSquaresRow, col)) {
                    moves.add(new int[]{twoSquaresRow, col});
                }
            }
        }
    }

    /**
     * Adds diagonal capture moves to the list of possible moves.
     *
     * @param board The current board state.
     * @param moves The list to add moves to.
     * @param direction The direction of movement (-1 for white, 1 for black).
     */
    private void addDiagonalCaptures(Piece[][] board, List<int[]> moves, int direction) {
        int newRow = row + direction;
        
        // Diagonal left capture
        int leftCol = col - 1;
        if (isValidPosition(newRow, leftCol) && isEnemyPiece(board, newRow, leftCol)) {
            moves.add(new int[]{newRow, leftCol});
        }
        
        // Diagonal right capture
        int rightCol = col + 1;
        if (isValidPosition(newRow, rightCol) && isEnemyPiece(board, newRow, rightCol)) {
            moves.add(new int[]{newRow, rightCol});
        }
    }

    /**
     * Sets the position of this pawn and marks it as having moved.
     *
     * @param row The new row position (0-7).
     * @param col The new column position (0-7).
     */
    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        this.hasMoved = true;
    }

    /**
     * Checks if this pawn has moved from its starting position.
     *
     * @return true if the pawn has moved, false otherwise.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Checks if this pawn can be promoted (reached the opposite end of the board).
     *
     * @return true if the pawn can be promoted, false otherwise.
     */
    public boolean canPromote() {
        return (color.equals("white") && row == 0) || (color.equals("black") && row == 7);
    }
}

