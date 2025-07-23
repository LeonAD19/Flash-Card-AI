package game;

import pieces.Piece;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the chess game.
 * Tracks the player's color and available pieces.
 */
public class Player {
    private String color;
    private List<Piece> availablePieces;
    private List<Piece> capturedPieces;

    /**
     * Constructs a new player with the specified color.
     *
     * @param color The color of the player ("white" or "black").
     */
    public Player(String color) {
        this.color = color;
        this.availablePieces = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
    }

    /**
     * Gets the color of this player.
     *
     * @return The player's color ("white" or "black").
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the list of available pieces for this player.
     *
     * @return A list of pieces that are still on the board.
     */
    public List<Piece> getAvailablePieces() {
        return new ArrayList<>(availablePieces);
    }
    /**
     * Gets the list of captured pieces from the opponent.
     *
     * @return A list of pieces that this player has captured.
     */
    public List<Piece> getCapturedPieces() {
        return new ArrayList<>(capturedPieces);
    }

    /**
     * Adds a piece to the player's available pieces.
     *
     * @param piece The piece to add.
     */
    public void addPiece(Piece piece) {
        if (piece != null && piece.getColor().equals(color)) {
            availablePieces.add(piece);
        }
    }

    /**
     * Removes a piece from the player's available pieces (when captured).
     *
     * @param piece The piece to remove.
     */
    public void removePiece(Piece piece) {
        availablePieces.remove(piece);
    }

    /**
     * Adds a captured piece to the player's captured pieces list.
     *
     * @param piece The piece that was captured.
     */
    public void addCapturedPiece(Piece piece) {
        if (piece != null && !piece.getColor().equals(color)) {
            capturedPieces.add(piece);
        }
    }

    /**
     * Gets the number of available pieces.
     *
     * @return The count of pieces still on the board.
     */
    public int getAvailablePieceCount() {
        return availablePieces.size();
    }

    /**
     * Gets the number of captured pieces.
     *
     * @return The count of captured pieces.
     */
    public int getCapturedPieceCount() {
        return capturedPieces.size();
    }

    /**
     * Checks if the player has any available pieces.
     *
     * @return true if the player has pieces on the board, false otherwise.
     */
    public boolean hasAvailablePieces() {
        return !availablePieces.isEmpty();
    }

    /**
     * Updates the available pieces list based on the current board state.
     * This method should be called after each move to keep the list current.
     *
     * @param board The current board state.
     */
    public void updateAvailablePieces(Piece[][] board) {
        availablePieces.clear();
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    availablePieces.add(piece);
                }
            }
        }
    }

    /**
     * Returns a string representation of this player.
     *
     * @return A string representing the player.
     */
    @Override
    public String toString() {
        return color.toUpperCase() + " Player (" + availablePieces.size() + " pieces)";
    }
}

