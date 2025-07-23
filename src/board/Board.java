package board;

import pieces.Piece;
import pieces.Rook;
import pieces.Knight;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;
import pieces.Pawn;

/**
 * Represents the chessboard as an 8x8 array of Pieces.
 * Manages the game state, piece placement, and move validation.
 */
public class Board {
    private Piece[][] board;
    private String currentPlayer;

    /**
     * Constructs a new chess board and initializes it with pieces in starting positions.
     */
    public Board() {
        board = new Piece[8][8];
        currentPlayer = "white"; // Start with white player
        initializeBoard();
    }

    /**
     * Initializes the board with pieces in their starting positions.
     */
    public void initializeBoard() {
        // Clear the board first
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = null;
            }
        }

        // Place black pieces (top of board, ranks 7-8)
        // Black major pieces on rank 8 (row 0)
        board[0][0] = new Rook("black", 0, 0);
        board[0][1] = new Knight("black", 0, 1);
        board[0][2] = new Bishop("black", 0, 2);
        board[0][3] = new Queen("black", 0, 3);
        board[0][4] = new King("black", 0, 4);
        board[0][5] = new Bishop("black", 0, 5);
        board[0][6] = new Knight("black", 0, 6);
        board[0][7] = new Rook("black", 0, 7);

        // Black pawns on rank 7 (row 1)
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn("black", 1, col);
        }

        // Place white pieces (bottom of board, ranks 1-2)
        // White pawns on rank 2 (row 6)
        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn("white", 6, col);
        }

        // White major pieces on rank 1 (row 7)
        board[7][0] = new Rook("white", 7, 0);
        board[7][1] = new Knight("white", 7, 1);
        board[7][2] = new Bishop("white", 7, 2);
        board[7][3] = new Queen("white", 7, 3);
        board[7][4] = new King("white", 7, 4);
        board[7][5] = new Bishop("white", 7, 5);
        board[7][6] = new Knight("white", 7, 6);
        board[7][7] = new Rook("white", 7, 7);
    }

    /**
     * Displays the current board state in the console.
     */
    public void display() {
        System.out.println("\n=== CHESS BOARD ===");
        System.out.println("Current player: " + currentPlayer.toUpperCase());
        System.out.println();
        
        // Top coordinate labels
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        
        for (int row = 0; row < 8; row++) {
            // Left rank number
            System.out.print((8 - row) + " |");
            
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    System.out.print("   ");
                } else {
                    // Display piece according to specification: wP, bR, etc.
                    String color = board[row][col].getColor();
                    String pieceType = board[row][col].getClass().getSimpleName();
                    
                    char colorChar = color.charAt(0); // 'w' or 'b'
                    char pieceChar = pieceType.charAt(0); // 'P', 'R', 'N', etc.
                    
                    System.out.print(" " + colorChar + pieceChar);
                }
                System.out.print("|");
            }
            
            // Right rank number
            System.out.println(" " + (8 - row));
            System.out.println("  +---+---+---+---+---+---+---+---+");
        }
        
        // Bottom coordinate labels
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println();
    }

    /**
     * Gets the piece at the specified board position.
     *
     * @param row The row of the piece (0-7).
     * @param col The column of the piece (0-7).
     * @return The piece at the given position, or null if empty.
     * @throws IndexOutOfBoundsException if the position is invalid.
     */
    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            throw new IndexOutOfBoundsException("Invalid board position: (" + row + ", " + col + ")");
        }
        return board[row][col];
    }

    /**
     * Gets the current board state as a 2D array.
     * Used by Player class to update available pieces.
     *
     * @return The 2D array representing the board.
     */
    public Piece[][] getBoard() {
        return board;
    }

    /**
     * Moves a piece from one position to another if the move is valid.
     *
     * @param fromRow The starting row of the piece.
     * @param fromCol The starting column of the piece.
     * @param toRow The destination row.
     * @param toCol The destination column.
     * @return true if the move was successful, false otherwise.
     */
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        // Check bounds
        if (fromRow < 0 || fromRow >= 8 || fromCol < 0 || fromCol >= 8 ||
            toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8) {
            return false;
        }

        Piece piece = board[fromRow][fromCol];
        if (piece == null) {
            System.out.println("No piece at the specified position.");
            return false;
        }

        // Check if it's the correct player's turn
        if (!piece.getColor().equals(currentPlayer)) {
            System.out.println("It's " + currentPlayer + "'s turn.");
            return false;
        }

        // Check if the destination has a piece of the same color
        Piece destinationPiece = board[toRow][toCol];
        if (destinationPiece != null && destinationPiece.getColor().equals(piece.getColor())) {
            System.out.println("Cannot capture your own piece.");
            return false;
        }

        // Check if the move is valid according to piece movement rules
        for (int[] move : piece.possibleMoves(board)) {
            if (move[0] == toRow && move[1] == toCol) {
                // Capture notification
                if (destinationPiece != null) {
                    System.out.println(currentPlayer + " captures " + 
                                     destinationPiece.getColor() + " " + 
                                     destinationPiece.getClass().getSimpleName());
                }
                
                // Execute the move
                board[toRow][toCol] = piece;
                board[fromRow][fromCol] = null;
                piece.setPosition(toRow, toCol);
                
                // Switch turns
                currentPlayer = currentPlayer.equals("white") ? "black" : "white";
                
                return true;
            }
        }
        
        System.out.println("Invalid move for " + piece.getClass().getSimpleName());
        return false;
    }

    /**
     * Converts chess notation (e.g., "e4") to board coordinates.
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
     * Converts board coordinates to chess notation.
     *
     * @param row The row (0-7).
     * @param col The column (0-7).
     * @return The chess notation string (e.g., "e4"), or null if invalid.
     */
    public static String coordsToNotation(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return null;
        }
        
        char fileChar = (char) ('A' + col);
        char rankChar = (char) ('1' + (7 - row));
        
        return "" + fileChar + rankChar;
    }

    /**
     * Gets the current player whose turn it is.
     *
     * @return The current player ("white" or "black").
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player (used for testing or special scenarios).
     *
     * @param player The player to set as current ("white" or "black").
     */
    public void setCurrentPlayer(String player) {
        if (player.equals("white") || player.equals("black")) {
            this.currentPlayer = player;
        }
    }

    /**
     * Resets the board to the initial game state.
     */
    public void reset() {
        currentPlayer = "white";
        initializeBoard();
    }

    /**
     * Checks if the specified position is within the board bounds.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Checks if a square is empty.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return true if the square is empty, false otherwise.
     */
    public boolean isEmpty(int row, int col) {
        if (!isValidPosition(row, col)) {
            return false;
        }
        return board[row][col] == null;
    }

    /**
     * Gets the total number of pieces on the board.
     *
     * @return The count of pieces currently on the board.
     */
    public int getPieceCount() {
        int count = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Gets the number of pieces for a specific color.
     *
     * @param color The color to count ("white" or "black").
     * @return The count of pieces for the specified color.
     */
    public int getPieceCount(String color) {
        int count = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null && board[row][col].getColor().equals(color)) {
                    count++;
                }
            }
        }
        return count;
    }
}

