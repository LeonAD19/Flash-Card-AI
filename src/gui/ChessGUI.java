package gui;

import board.Board;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class ChessGUI extends JFrame {
    private final Board board;
    private final JPanel boardPanel;
    private final JButton[][] squares;

    public ChessGUI() {
        board = new Board();
        boardPanel = new JPanel(new GridLayout(8, 8));
        squares = new JButton[8][8];

        initializeGUI();
        drawBoard();
    }

    private void initializeGUI() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
    }

    private void drawBoard() {
        boardPanel.removeAll();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = new JButton();
                square.setFocusPainted(false);
                square.setFont(new Font("Arial", Font.BOLD, 20));

                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    String label = piece.getClass().getSimpleName().substring(0, 1);
                    String color = piece.getColor().equals("white") ? "W" : "B";
                    square.setText(label + color);
                }

                // Alternate square colors
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(new Color(125, 135, 150)); // Dark gray
                }

                boardPanel.add(square);
                squares[row][col] = square;
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGUI gui = new ChessGUI();
            gui.setVisible(true);
        });
    }
}

