package game;

import java.util.Scanner;
import board.Board;

/**
 * Main game class that manages the chess game flow.
 * Handles user input, game initialization, and the main game loop.
 */
public class Game {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Scanner scanner;
    private boolean gameRunning;

    /**
     * Initializes a new chess game with board and players.
     */
    public Game() {
        this.board = new Board();
        this.whitePlayer = new Player("white");
        this.blackPlayer = new Player("black");
        this.scanner = new Scanner(System.in);
        this.gameRunning = false;
    }

    /**
     * Starts the game by displaying welcome message and initializing the board.
     */
    public void start() {
        System.out.println("=== WELCOME TO CONSOLE CHESS ===");
        System.out.println("Enter moves in format: FROM TO (e.g., E2 E4)");
        System.out.println("Type 'help' for commands, 'quit' to exit");
        System.out.println("=====================================\n");
        
        gameRunning = true;
        play();
    }

    /**
     * Main game loop that alternates turns and processes player moves.
     */
    public void play() {
        while (gameRunning) {
            // Update player piece lists
            whitePlayer.updateAvailablePieces(board.getBoard());
            blackPlayer.updateAvailablePieces(board.getBoard());
            
            // Display current board state
            board.display();
            
            // Get current player
            Player currentPlayer = board.getCurrentPlayer().equals("white") ? whitePlayer : blackPlayer;
            
            // Prompt for user input
            System.out.print(currentPlayer.getColor().toUpperCase() + "'s turn - Enter move: ");
            String input = scanner.nextLine().trim();

            // Process the input
            if (!processInput(input)) {
                // If input processing failed, continue to next iteration
                continue;
            }
        }
        
        end();
    }

    /**
     * Ends the game and cleans up resources.
     */
    public void end() {
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    /**
     * Processes user input and executes the appropriate action.
     *
     * @param input The user's input string.
     * @return true if the input was processed successfully, false otherwise.
     */
    private boolean processInput(String input) {
        if (input.isEmpty()) {
            System.out.println("Please enter a command.");
            return false;
        }

        // Convert to lowercase for command processing
        String command = input.toLowerCase();

        // Handle special commands
        switch (command) {
            case "quit":
            case "exit":
                gameRunning = false;
                return true;
                
            case "help":
                displayHelp();
                return false;
                
            case "reset":
                board.reset();
                System.out.println("Board reset to starting position.");
                return false;
                
            case "display":
                // Board will be displayed in next loop iteration
                return false;
                
            case "status":
                displayGameStatus();
                return false;
                
            default:
                // Try to process as a chess move
                return processMove(input);
        }
    }

    /**
     * Processes a chess move input in standard notation.
     *
     * @param input The move input string (e.g., "E2 E4").
     * @return true if the move was processed successfully, false otherwise.
     */
    private boolean processMove(String input) {
        // Basic format validation using regex
        if (!input.matches("^[A-Ha-h][1-8]\\s+[A-Ha-h][1-8]$")) {
            System.out.println("Invalid move format. Use format: FROM TO (e.g., E2 E4)");
            return false;
        }

        // Parse the move
        String[] parts = input.split("\\s+");
        if (parts.length != 2) {
            System.out.println("Invalid move format. Use format: FROM TO (e.g., E2 E4)");
            return false;
        }

        String fromSquare = parts[0].toUpperCase();
        String toSquare = parts[1].toUpperCase();

        // Convert notation to coordinates
        int[] fromCoords = Board.notationToCoords(fromSquare);
        int[] toCoords = Board.notationToCoords(toSquare);

        if (fromCoords == null || toCoords == null) {
            System.out.println("Invalid square notation. Use A1-H8 format.");
            return false;
        }

        int fromRow = fromCoords[0];
        int fromCol = fromCoords[1];
        int toRow = toCoords[0];
        int toCol = toCoords[1];

        // Validate that the move is different positions
        if (fromRow == toRow && fromCol == toCol) {
            System.out.println("Source and destination squares cannot be the same.");
            return false;
        }

        // Attempt to make the move
        boolean moveSuccess = board.movePiece(fromRow, fromCol, toRow, toCol);
        
        if (moveSuccess) {
            System.out.println("Move executed: " + fromSquare + " to " + toSquare);
            // Check for game ending conditions could be added here
        } else {
            System.out.println("Invalid move. Try again.");
        }

        return false; // Always return false to continue the game loop
    }

    /**
     * Displays the current game status including piece counts.
     */
    private void displayGameStatus() {
        System.out.println("\n=== GAME STATUS ===");
        System.out.println("Current turn: " + board.getCurrentPlayer().toUpperCase());
        System.out.println("White pieces: " + whitePlayer.getAvailablePieceCount());
        System.out.println("Black pieces: " + blackPlayer.getAvailablePieceCount());
        System.out.println("White captured: " + whitePlayer.getCapturedPieceCount());
        System.out.println("Black captured: " + blackPlayer.getCapturedPieceCount());
        System.out.println("==================\n");
    }

    /**
     * Displays help information for the user.
     */
    private void displayHelp() {
        System.out.println("\n=== CHESS GAME HELP ===");
        System.out.println("Commands:");
        System.out.println("  [FROM] [TO] - Make a move (e.g., E2 E4)");
        System.out.println("  help        - Show this help message");
        System.out.println("  reset       - Reset the board to starting position");
        System.out.println("  display     - Redisplay the current board");
        System.out.println("  status      - Show game status");
        System.out.println("  quit/exit   - Exit the game");
        System.out.println();
        System.out.println("Move Format:");
        System.out.println("  - Use standard chess notation (A1-H8)");
        System.out.println("  - Files (columns): A, B, C, D, E, F, G, H");
        System.out.println("  - Ranks (rows): 1, 2, 3, 4, 5, 6, 7, 8");
        System.out.println("  - Example: E2 E4 (move piece from E2 to E4)");
        System.out.println();
        System.out.println("Piece Symbols:");
        System.out.println("  - wp/bp = White/Black Pawn");
        System.out.println("  - wR/bR = White/Black Rook");
        System.out.println("  - wN/bN = White/Black Knight");
        System.out.println("  - wB/bB = White/Black Bishop");
        System.out.println("  - wQ/bQ = White/Black Queen");
        System.out.println("  - wK/bK = White/Black King");
        System.out.println("========================\n");
    }

    /**
     * Gets the current game board.
     *
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the white player.
     *
     * @return The white player.
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Gets the black player.
     *
     * @return The black player.
     */
    public Player getBlackPlayer() {
        return blackPlayer;
    }

    /**
     * Checks if the game is currently running.
     *
     * @return true if the game is running, false otherwise.
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Stops the current game.
     */
    public void stopGame() {
        gameRunning = false;
    }
}

