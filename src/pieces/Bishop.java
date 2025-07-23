package pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Bishop chess piece.
 * Bishop will be connected to Pieces package
 * color, row, and column are declared
 * included loops to help move bishop and moves
 *   color  = The color of the piece ("white" or "black").
 *   row The row index (0–7).
 *   col The column index (0–7).
 */
public class Bishop extends Piece {

    public Bishop(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> possibleMoves(Piece[][] board) {
        List<int[]> moves = new ArrayList<>();

        int[] rowDirs = {-1, -1, 1, 1};
        int[] colDirs = {-1, 1, -1, 1};

        for (int d = 0; d < 4; d++) {
            int r = row + rowDirs[d];
            int c = col + colDirs[d];

            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (board[r][c] == null) {
                    moves.add(new int[]{r, c});
                } else {
                    if (!board[r][c].getColor().equals(this.color)) {
                        moves.add(new int[]{r, c});
                    }
                    break;
                }
                r += rowDirs[d];
                c += colDirs[d];
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "wB" : "bB";
    }
}
