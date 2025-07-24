/**
 * Main class to start the chess game application.
 * Entry point for both console-based and GUI-based chess game.
 * 
 * @author [Your Name]
 * @version 2.0
 */
public class Main {
    /**
     * Main method to start the chess game.
     * Launches the GUI version by default, or console version if specified.
     *
     * @param args Command line arguments:
     *             --console or -c to run console version
     *             --gui or -g to run GUI version (default)
     */
    public static void main(String[] args) {
        // Check command line arguments
        boolean useConsole = false;
        
        for (String arg : args) {
            if (arg.equals("--console") || arg.equals("-c")) {
                useConsole = true;
                break;
            }
        }
        
        if (useConsole) {
            // Launch console version
            System.out.println("Starting console chess game...");
            game.Game chessGame = new game.Game();
            chessGame.start();
        } else {
            // Launch GUI version (default)
            System.out.println("Starting GUI chess game...");
            
            // Create and show the GUI on the Event Dispatch Thread
            javax.swing.SwingUtilities.invokeLater(() -> {
                new gui.ChessGUI();
            });
        }
    }
}



