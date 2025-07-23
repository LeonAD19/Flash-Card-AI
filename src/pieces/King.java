package pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a King chess piece.
 * King will be connected to Pieces package
 * color, row, and column are declared
 * included loops to help move king and moves
 * color = The color of the piece ("white" or "black").
 * row The row index (0–7).
 * col The column index (0–7).
 */
public class King extends Piece {

    public King(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> possibleMoves(Piece[][] board) {
        List<int[]> moves = new ArrayList<>();

        int[] rowOffsets = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] colOffsets = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int i = 0; i < 8; i++) {
            int r = row + rowOffsets[i];
            int c = col + colOffsets[i];

            if (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (board[r][c] == null || !board[r][c].getColor().equals(this.color)) {
                    moves.add(new int[]{r, c});
                }
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "wK" : "bK";
    }
}