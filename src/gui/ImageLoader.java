package gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class responsible for loading and providing chess piece images.
 */
public class ImageLoader {
    private static final Map<String, ImageIcon> imageMap = new HashMap<>();

    static {
        // Load images for each piece type and color
        loadImage("white_pawn", "resources/images/white_pawn.png");
        loadImage("black_pawn", "resources/images/black_pawn.png");
        // Repeat for other pieces: rook, knight, bishop, queen, king
        // loadImage("white_rook", "resources/images/white_rook.png");
        // loadImage("black_rook", "resources/images/black_rook.png");
    }

    private static void loadImage(String key, String path) {
        ImageIcon icon = new ImageIcon(path);
        imageMap.put(key, icon);
    }

    /**
     * Returns the image icon for the given piece and color key.
     *
     * @param key e.g. "white_pawn"
     * @return the ImageIcon for that piece
     */
    public static ImageIcon getImage(String key) {
        return imageMap.get(key);
    }
}
