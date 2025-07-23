package pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Queen chess piece.
 * Queen will be connected to Pieces package
 * color, row, and column are declared
 * included loops to help move queen and moves
 * color  = The color of the piece ("white" or "black").
 * row The row index (0–7).
 * col The column index (0–7).
 */
public class Queen extends Piece {

    public Queen(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> possibleMoves(Piece[][] board) {
        List<int[]> moves = new ArrayList<>();

        int[] rowDirs = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] colDirs = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int d = 0; d < 8; d++) {
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
        return color.equals("white") ? "wQ" : "bQ";
    }
}