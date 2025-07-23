package pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * Rook piece implementation.
 */
public class Rook extends Piece {

    public Rook(String color, int row, int col) {
        super(color, row, col);
    }

    /**
     * Returns valid moves for the rook (vertical and horizontal).
     *
     * @param board The board state.
     * @return List of valid moves.
     */

    @Override
    public List<int[]> possibleMoves(Piece[][] board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (board[r][c] == null) {
                    moves.add(new int[]{r, c});
                } else {
                    if (!board[r][c].getColor().equals(color)) {
                        moves.add(new int[]{r, c});
                    }
                    break;
                }
                r += dir[0];
                c += dir[1];
            }
        }
        return moves;
    }
}
