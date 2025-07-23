/**
 * Main class to start the chess game application.
 * Entry point for the console-based chess game.
 * 
 * @author [Your Name]
 * @version 1.0
 */
public class Main {
    /**
     * Main method to start the chess game.
     * Creates a new game instance and starts it.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a new game instance
        game.Game chessGame = new game.Game();
        
        // Start the game
        chessGame.start();
    }
}



