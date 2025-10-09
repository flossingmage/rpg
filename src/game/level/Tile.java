package game.level;

import java.awt.image.BufferedImage;

public class Tile {
    BufferedImage img;
    public boolean walkable;

    public Tile(BufferedImage img, boolean walkable) {
        this.img = img;
        this.walkable = walkable;
    }
}