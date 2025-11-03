package game.entities;

import game.GamePanel;
import game.level.MapLoader;

import java.util.ArrayList;

import static game.entities.EntityLoader.entityHitBoxes;

public class CollisionHandler {

    static final int collisionBuffer = 2;

    /**
     * check if entity can move in a given direction
     * todo remake
     * @param direction the direction the entity is trying to move
     * @return true if the entity can move in the given direction, false otherwise
     */
    public static boolean entityCollision(String direction, Hitbox entityHitbox) {
        try {
            switch (direction) {
                case "up" -> {
                    if (!entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions + collisionBuffer, entityHitbox.getY() - GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions + entityHitbox.length - collisionBuffer - 2).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions + collisionBuffer, entityHitbox.getY() - GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions + entityHitbox.length - collisionBuffer - 2).getFirst().getHitbox();
                        if (entityHitbox.getY() < hitboxes.getY() + hitboxes.getHeight() && entityHitbox.getX() < hitboxes.getX() + hitboxes.getLength() - collisionBuffer)
                            return false;
                    }
                }
                case "down" -> {
                    if (!entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions + collisionBuffer, entityHitbox.getY(), entityHitbox.getHeight(), GamePanel.tileDimensions + entityHitbox.length - collisionBuffer - 2).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions + collisionBuffer, entityHitbox.getY(), GamePanel.tileDimensions, GamePanel.tileDimensions + entityHitbox.length - collisionBuffer - 2).getFirst().getHitbox();
                        if (entityHitbox.getY() < hitboxes.getY() + hitboxes.getHeight() && entityHitbox.getX() < hitboxes.getX() + hitboxes.getLength() - collisionBuffer)
                            return false;
                    }
                }
                case "left" -> {
                    if (!entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions, entityHitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + entityHitbox.height - collisionBuffer - 2, GamePanel.tileDimensions).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions, entityHitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + entityHitbox.height - collisionBuffer - 2, GamePanel.tileDimensions).getFirst().getHitbox();
                        if (entityHitbox.getX() < hitboxes.getX() + hitboxes.getLength() && entityHitbox.getY() < hitboxes.getY() + hitboxes.getHeight() - collisionBuffer)
                            return false;
                    }
                }
                case "right" -> {
                    if (!entitiesInArea(entityHitbox.getX(), entityHitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + entityHitbox.height - collisionBuffer - 2, entityHitbox.length + collisionBuffer).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(entityHitbox.getX(), entityHitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + entityHitbox.height - collisionBuffer - 2, entityHitbox.length).getFirst().getHitbox();
                        if (entityHitbox.getX() < hitboxes.getX() + hitboxes.getLength() && entityHitbox.getY() < hitboxes.getY() + hitboxes.getHeight() - collisionBuffer)
                            return false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("update error CollisionHandler.java: 53");
        }
        return true;
    }

    public static boolean canWalkOn(int x, int y) {
        String key = x + " " + y;
        return (MapLoader.getMap().containsKey(key) && MapLoader.getMap().get(key).walkable);
    }

    /**
     * Returns a list of all entities in a given area
     * @param x      top left x coordinate of area
     * @param y      top left y coordinate of area
     * @param height height of the area
     * @param length length of the area
     * @return ArrayList<Entity>
     */
    public static ArrayList<Entity> entitiesInArea(int x, int y, int height, int length) {
        ArrayList<Entity> entities = new ArrayList<>();
        for (Hitbox hitbox : entityHitBoxes) {
            if (checkBound(hitbox.getX(), x, x + length) && checkBound(hitbox.getY(), y, y + height)) {
                entities.add(hitbox.entity);
            }
        }
        return entities;
    }

    public static boolean checkBound(int check, int min, int max) {
        return ((check > min) && (check < max));
    }
}
