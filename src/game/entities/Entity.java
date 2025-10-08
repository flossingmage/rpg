package game.entities;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
    protected int x;
    protected int y;
    protected int collisionBuffer = 6;

    protected static BufferedImage ErrorImg;

    static {
        try {
            ErrorImg = ImageIO.read(new File("res/enemies/error.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * check if entity can move in a given direction
     * @param direction
     * @return
     */
    public boolean entityCollision(String direction) {
        switch (direction) {
            case "up" -> {
                if (!entitiesInArea(x - GamePanel.tileDimensions + collisionBuffer, y - GamePanel.tileDimensions, GamePanel.tileDimensions , 2 * GamePanel.tileDimensions - collisionBuffer - 2).isEmpty())
                    return false;
            }
            case "down" -> {
                if (!entitiesInArea(x - GamePanel.tileDimensions + collisionBuffer, y, GamePanel.tileDimensions , 2 * GamePanel.tileDimensions - collisionBuffer - 2).isEmpty())
                    return false;
            }
            case "left" -> {
                if (!entitiesInArea(x - GamePanel.tileDimensions, y - GamePanel.tileDimensions + collisionBuffer, 2 * GamePanel.tileDimensions - collisionBuffer - 2, GamePanel.tileDimensions ).isEmpty())
                    return false;
            }
            case "right" -> {
                if (!entitiesInArea(x, y - GamePanel.tileDimensions + collisionBuffer, 2 * GamePanel.tileDimensions - collisionBuffer - 2, GamePanel.tileDimensions).isEmpty())
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns a list of all entities in a given area
     * @param x
     * @param y
     * @param height
     * @param length
     * @return ArrayList<Entity>
     */
    public ArrayList<Entity> entitiesInArea(int x, int y, int height, int length) {
        ArrayList<Entity> entities = new ArrayList<>();

        for (int loopx = x; loopx < x + length; loopx++) {
            for (int loopy = y; loopy < y + height; loopy++) {
                if (EntityLoader.enemies.containsKey(loopx + " " + loopy))
                    entities.add(EntityLoader.enemies.get(loopx + " " + loopy));
            }
        }
        return entities;
    }

}