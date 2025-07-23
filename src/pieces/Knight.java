package pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Knight chess piece.
 * Knight will be connected to Pieces package
 * color, row, and column are declared
 *  * included loops to help move knight and moves
 *  color  = The color of the piece ("white" or "black").
 *  row The row index (0–7).
 *  col The column index (0–7).
 */
public class Knight extends Piece {

    public Knight(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> possibleMoves(Piece[][] board) {
        List<int[]> moves = new ArrayList<>();

        int[][] offsets = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] offset : offsets) {
            int r = row + offset[0];
            int c = col + offset[1];

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
        return color.equals("white") ? "wN" : "bN";
    }
}