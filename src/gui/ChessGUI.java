package gui;

import board.Board;
import pieces.Piece;
import pieces.King;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Chess GUI with full Phase 2 functionality:
 * - 8x8 visual chessboard with alternating colors
 * - Click-to-select and click-to-move interaction
 * - Piece capture functionality
 * - King capture detection and game over popup
 * - Visual feedback and status updates
 * 
 * @author [Your Name]
 * @version 2.0
 */
public class ChessGUI extends JFrame {
    // Constants for board appearance
    private static final int SQUARE_SIZE = 80;
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    private static final Color DARK_SQUARE = new Color(181, 136, 99);
    private static final Color SELECTED_SQUARE = new Color(255, 255, 0, 150);
    
    // Core components
    private final Board board;
    private final JPanel boardPanel;
    private final JButton[][] squares;
    private final JLabel statusLabel;
    
    // Game state tracking
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean gameOver = false;

    /**
     * Constructor - initializes the chess GUI and displays it
     */
    public ChessGUI() {
        // Initialize components first
        board = new Board();
        boardPanel = new JPanel(new GridLayout(8, 8, 0, 0)); // No gaps between squares
        squares = new JButton[8][8];
        statusLabel = new JLabel("White's Turn", SwingConstants.CENTER);

        // Setup GUI
        initializeGUI();
        
        // Draw the board
        drawBoard();
        
        // Force repaint
        repaint();
        
        System.out.println("ChessGUI initialized successfully");
    }

    /**
     * Sets up the main GUI window and layout
     */
    private void initializeGUI() {
        setTitle("Chess Game - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); // Allow resizing
        
        // Set minimum window size
        setMinimumSize(new Dimension(700, 750));
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Status label at top
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        statusLabel.setBackground(new Color(220, 220, 220));
        statusLabel.setOpaque(true);
        statusLabel.setPreferredSize(new Dimension(700, 60));
        mainPanel.add(statusLabel, BorderLayout.NORTH);
        
        // Chess board in center with padding
        JPanel boardContainer = new JPanel(new BorderLayout());
        boardContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        boardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createLoweredBevelBorder()
        ));
        boardPanel.setPreferredSize(new Dimension(640, 640)); // 8 * 80 = 640
        
        boardContainer.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(boardContainer, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Pack to preferred size and center
        pack();
        setLocationRelativeTo(null); // Center window on screen
        
        // Ensure window is visible
        setVisible(true);
        
        // Debug output
        System.out.println("Window size: " + getSize());
        System.out.println("Board panel size: " + boardPanel.getPreferredSize());
    }

    /**
     * Creates the visual chess board with all squares and pieces
     */
    private void drawBoard() {
        boardPanel.removeAll();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = createSquare(row, col);
                boardPanel.add(square);
                squares[row][col] = square;
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }
    
    /**
     * Creates a single chess square button with proper styling and mouse handling
     */
    private JButton createSquare(int row, int col) {
        JButton square = new JButton();
        square.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
        square.setMinimumSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
        square.setMaximumSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
        square.setFocusPainted(false);
        square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        square.setFont(new Font("Arial Unicode MS", Font.BOLD, 32));
        square.setOpaque(true);
        
        // Set alternating square colors
        Color squareColor = (row + col) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
        square.setBackground(squareColor);
        
        // Add piece symbol if there's a piece on this square
        Piece piece = board.getPiece(row, col);
        if (piece != null) {
            String symbol = getPieceSymbol(piece);
            square.setText(symbol);
            
            // Set piece color with better contrast
            if (piece.getColor().equals("white")) {
                square.setForeground(new Color(255, 255, 255)); // White pieces
            } else {
                square.setForeground(new Color(0, 0, 0)); // Black pieces
            }
        } else {
            square.setText("");
        }
        
        // Add mouse interaction
        square.addMouseListener(new SquareMouseListener(row, col));
        
        return square;
    }
    
    /**
     * Inner class to handle mouse clicks on chess squares
     */
    private class SquareMouseListener extends MouseAdapter {
        private final int row;
        private final int col;
        
        public SquareMouseListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!gameOver) {
                handleSquareClick(row, col);
            }
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            if (!gameOver) {
                squares[row][col].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            squares[row][col].setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Handles all mouse clicks on chess squares
     * Implements click-to-select, click-to-move functionality
     */
    private void handleSquareClick(int row, int col) {
        if (selectedRow == -1 && selectedCol == -1) {
            // No piece selected - try to select one
            selectPiece(row, col);
        } else {
            // Piece already selected
            if (row == selectedRow && col == selectedCol) {
                // Clicked same square - deselect
                deselectPiece();
            } else {
                // Clicked different square - try to move
                attemptMove(row, col);
            }
        }
    }
    
    /**
     * Attempts to select a piece at the given position
     */
    private void selectPiece(int row, int col) {
        Piece piece = board.getPiece(row, col);
        
        if (piece == null) {
            statusLabel.setText("No piece at that square. Select a piece to move.");
            return;
        }
        
        if (!piece.getColor().equals(board.getCurrentPlayer())) {
            statusLabel.setText("It's " + board.getCurrentPlayer().toUpperCase() + "'s turn!");
            return;
        }
        
        // Valid piece selection
        selectedRow = row;
        selectedCol = col;
        highlightSquare(row, col, true);
        statusLabel.setText(board.getCurrentPlayer().toUpperCase() + " - " + 
                          piece.getClass().getSimpleName() + " selected. Choose destination.");
    }
    
    /**
     * Deselects the currently selected piece
     */
    private void deselectPiece() {
        if (selectedRow != -1 && selectedCol != -1) {
            highlightSquare(selectedRow, selectedCol, false);
        }
        selectedRow = -1;
        selectedCol = -1;
        statusLabel.setText(board.getCurrentPlayer().toUpperCase() + "'s Turn");
    }
    
    /**
     * Attempts to move the selected piece to the target square
     */
    private void attemptMove(int toRow, int toCol) {
        // Check what piece (if any) is at the destination
        Piece capturedPiece = board.getPiece(toRow, toCol);
        
        // Use your existing board logic to attempt the move
        boolean moveSuccessful = board.movePiece(selectedRow, selectedCol, toRow, toCol);
        
        if (moveSuccessful) {
            // Move was successful
            if (capturedPiece != null) {
                // A piece was captured
                if (capturedPiece instanceof King) {
                    // King captured - game over!
                    String winner = capturedPiece.getColor().equals("white") ? "BLACK" : "WHITE";
                    handleGameOver(winner);
                    return;
                }
            }
            
            // Update the visual board and continue game
            drawBoard();
            deselectPiece();
            statusLabel.setText(board.getCurrentPlayer().toUpperCase() + "'s Turn");
            
        } else {
            // Move failed - show error message
            statusLabel.setText("Invalid move! Try again or click the piece to deselect.");
        }
    }
    
    /**
     * Handles game over when a King is captured
     */
    private void handleGameOver(String winner) {
        gameOver = true;
        drawBoard();
        deselectPiece();
        
        statusLabel.setText("GAME OVER - " + winner + " WINS!");
        
        // Show winner popup dialog
        String message = "Game Over!\n\n" + winner + " wins by capturing the King!";
        JOptionPane.showMessageDialog(
            this,
            message,
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Ask if player wants to play again
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Would you like to play again?",
            "New Game?",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        }
    }
    
    /**
     * Highlights or unhighlights a square to show piece selection
     */
    private void highlightSquare(int row, int col, boolean highlight) {
        JButton square = squares[row][col];
        if (highlight) {
            // Highlight the selected square
            square.setBackground(SELECTED_SQUARE);
            square.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else {
            // Restore original square color
            Color originalColor = (row + col) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
            square.setBackground(originalColor);
            square.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }
    }

    /**
     * Gets the Unicode chess symbol for a piece
     * Falls back to text symbols if Unicode isn't supported
     */
    private String getPieceSymbol(Piece piece) {
        String pieceType = piece.getClass().getSimpleName();
        boolean isWhite = piece.getColor().equals("white");
        
        switch (pieceType) {
            case "Pawn":
                return isWhite ? "♙" : "♟";
            case "Rook":
                return isWhite ? "♖" : "♜";
            case "Knight":
                return isWhite ? "♘" : "♞";
            case "Bishop":
                return isWhite ? "♗" : "♝";
            case "Queen":
                return isWhite ? "♕" : "♛";
            case "King":
                return isWhite ? "♔" : "♚";
            default:
                // Fallback to text if Unicode symbols don't work
                String color = isWhite ? "W" : "B";
                return pieceType.substring(0, 1) + color;
        }
    }
    
    /**
     * Resets the game to initial state
     */
    public void resetGame() {
        board.reset();
        gameOver = false;
        deselectPiece();
        drawBoard();
        statusLabel.setText("White's Turn");
    }
    
    /**
     * Gets the current board (useful for testing)
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Main method to launch just the GUI (alternative to Main.java)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new ChessGUI();
        });
    }
}