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
     *
     * @param direction the direction the entity is trying to move
     * @return true if the entity can move in the given direction, false otherwise
     */
    public static boolean entityCollision(String direction, Hitbox entityHitbox) {
        switch (direction) {
            case "up" -> {
                ArrayList<Entity> entities = entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions + collisionBuffer, entityHitbox.getY() - GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions + entityHitbox.length - 2 * collisionBuffer);
                if (!entities.isEmpty()) {
                    if (entityCollisionCheckXBoundUpDown(entities, entityHitbox))
                        return false;
                }
            }
            case "down" -> {
                ArrayList<Entity> entities = entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions + collisionBuffer, entityHitbox.getY() + entityHitbox.getHeight() - collisionBuffer, 5 + collisionBuffer, GamePanel.tileDimensions + entityHitbox.length - 2 * collisionBuffer);
                if (!entities.isEmpty()) {
                    if (entityCollisionCheckXBoundUpDown(entities, entityHitbox) && entityCollisionCheckYBoundDown(entities, entityHitbox))
                        return false;
                }
            }
            case "left" -> {
                ArrayList<Entity> entities = entitiesInArea(entityHitbox.getX() - GamePanel.tileDimensions, entityHitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + entityHitbox.height - 2 * collisionBuffer, GamePanel.tileDimensions);
                if (!entities.isEmpty()) {
                    if (entityCollisionCheckXBoundLeft(entities, entityHitbox))
                        return false;
                }
            }
            case "right" -> {
                if (!entitiesInArea(entityHitbox.getX(), entityHitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + entityHitbox.height - 2 * collisionBuffer, entityHitbox.length).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean entityCollisionCheckXBoundLeft(ArrayList<Entity> entities, Hitbox entityHitbox) {
        return entities.getFirst().getHitbox().getX() + entities.getFirst().getHitbox().getLength() > entityHitbox.getX();
    }

    public static boolean entityCollisionCheckXBoundUpDown(ArrayList<Entity> entities, Hitbox entityHitbox) {
        return entities.getFirst().getHitbox().getX() + entities.getFirst().getHitbox().getLength() - collisionBuffer > entityHitbox.getX();
    }

    public static boolean entityCollisionCheckYBoundDown(ArrayList<Entity> entities, Hitbox entityHitbox) {
        return entities.getFirst().getHitbox().getY() < entityHitbox.getY() + entityHitbox.height + collisionBuffer;
    }

    public static boolean canWalkOn(int x, int y) {
        String key = x + " " + y;
        return (MapLoader.getMap().containsKey(key) && MapLoader.getMap().get(key).walkable);
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
