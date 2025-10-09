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

    Hitbox hitbox;

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
        hitbox = new Hitbox(x, y, GamePanel.tileDimensions, GamePanel.tileDimensions, this);
    }

    public Entity(int x, int y, int length, int height) {
        this.x = x;
        this.y = y;
        hitbox = new Hitbox(x, y, length, height, this);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    /**
     * check if entity can move in a given direction
     *
     * @param direction the direction the entity is trying to move
     * @return true if the entity can move in the given direction, false otherwise
     */
    public boolean entityCollision(String direction) {
        try {
        switch (direction) {
                case "up" -> {
                    if (!entitiesInArea(x - GamePanel.tileDimensions + collisionBuffer, y - GamePanel.tileDimensions, GamePanel.tileDimensions, 2 * GamePanel.tileDimensions - collisionBuffer - 2).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(x - GamePanel.tileDimensions + collisionBuffer, y - GamePanel.tileDimensions, GamePanel.tileDimensions, 2 * GamePanel.tileDimensions - collisionBuffer - 2).getFirst().getHitbox();
                        if (y < hitboxes.getY() + hitboxes.getHeight() && x < hitboxes.getX() + hitboxes.getLength() - collisionBuffer)
                            return false;
                    }
                }
                case "down" -> {
                    if (!entitiesInArea(x - GamePanel.tileDimensions + collisionBuffer, y, GamePanel.tileDimensions, 2 * GamePanel.tileDimensions - collisionBuffer - 2).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(x - GamePanel.tileDimensions + collisionBuffer, y, GamePanel.tileDimensions, 2 * GamePanel.tileDimensions - collisionBuffer - 2).getFirst().getHitbox();
                        if (y < hitboxes.getY() + hitboxes.getHeight() && x < hitboxes.getX() + hitboxes.getLength() - collisionBuffer)
                            return false;
                    }
                }
                case "left" -> {
                    if (!entitiesInArea(x - GamePanel.tileDimensions, y - GamePanel.tileDimensions + collisionBuffer, 2 * GamePanel.tileDimensions - collisionBuffer - 2, GamePanel.tileDimensions).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(x - GamePanel.tileDimensions, y - GamePanel.tileDimensions + collisionBuffer, 2 * GamePanel.tileDimensions - collisionBuffer - 2, GamePanel.tileDimensions).getFirst().getHitbox();
                        if (x < hitboxes.getX() + hitboxes.getLength() && y < hitboxes.getY() + hitboxes.getHeight() - collisionBuffer)
                            return false;
                    }
                }
                case "right" -> {
                    if (!entitiesInArea(x, y - GamePanel.tileDimensions + collisionBuffer, 2 * GamePanel.tileDimensions - collisionBuffer - 2, GamePanel.tileDimensions).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(x, y - GamePanel.tileDimensions + collisionBuffer, 2 * GamePanel.tileDimensions - collisionBuffer - 2, GamePanel.tileDimensions).getFirst().getHitbox();
                        if (x < hitboxes.getX() + hitboxes.getLength() && y < hitboxes.getY() + hitboxes.getHeight() - collisionBuffer)
                            return false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("update error Entity.java: 83");
        }
        return true;
    }

    /**
     * Returns a list of all entities in a given area
     *
     * @param x      top left x coordinate of area
     * @param y      top left y coordinate of area
     * @param height height of the area
     * @param length length of the area
     * @return ArrayList<Entity>
     */
    public ArrayList<Entity> entitiesInArea(int x, int y, int height, int length) {
        ArrayList<Entity> entities = new ArrayList<>();

        for (int loopx = x; loopx < x + length; loopx++) {
            for (int loopy = y; loopy < y + height; loopy++) {
                if (EntityLoader.entityHitBoxes.containsKey(loopx + " " + loopy))
                    entities.add(EntityLoader.entityHitBoxes.get(loopx + " " + loopy).entity);
            }
        }
        return entities;
    }
}