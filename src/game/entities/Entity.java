package game.entities;

import game.GamePanel;
import game.entities.enemies.Enemy;
import game.entities.player.Player;
import game.level.MapLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static game.entities.EntityLoader.entityHitBoxes;

public abstract class Entity {
    protected int x, y, vx, vy;
    protected int collisionBuffer = 2;
    protected String direction;
    protected boolean isAttacking = false;

    //temp stats will be stored in weapon object later
    protected int range = 40;
    protected int attackThickness = 10;


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
                    if (!entitiesInArea(hitbox.getX() - GamePanel.tileDimensions + collisionBuffer, hitbox.getY() - GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions + hitbox.length - collisionBuffer - 2).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(hitbox.getX() - GamePanel.tileDimensions + collisionBuffer, hitbox.getY() - GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions + hitbox.length - collisionBuffer - 2).getFirst().getHitbox();
                        if (hitbox.getY() < hitboxes.getY() + hitboxes.getHeight() && hitbox.getX() < hitboxes.getX() + hitboxes.getLength() - collisionBuffer)
                            return false;
                    }
                }
                case "down" -> {
                    if (!entitiesInArea(hitbox.getX() - GamePanel.tileDimensions + collisionBuffer, hitbox.getY(), hitbox.getHeight(), GamePanel.tileDimensions + hitbox.length - collisionBuffer - 2).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(hitbox.getX() - GamePanel.tileDimensions + collisionBuffer, hitbox.getY(), GamePanel.tileDimensions, GamePanel.tileDimensions + hitbox.length - collisionBuffer - 2).getFirst().getHitbox();
                        if (hitbox.getY() < hitboxes.getY() + hitboxes.getHeight() && hitbox.getX() < hitboxes.getX() + hitboxes.getLength() - collisionBuffer)
                            return false;
                    }
                }
                case "left" -> {
                    if (!entitiesInArea(hitbox.getX() - GamePanel.tileDimensions, hitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + hitbox.height - collisionBuffer - 2, GamePanel.tileDimensions).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(hitbox.getX() - GamePanel.tileDimensions, hitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + hitbox.height - collisionBuffer - 2, GamePanel.tileDimensions).getFirst().getHitbox();
                        if (hitbox.getX() < hitboxes.getX() + hitboxes.getLength() && hitbox.getY() < hitboxes.getY() + hitboxes.getHeight() - collisionBuffer)
                            return false;
                    }
                }
                case "right" -> {
                    if (!entitiesInArea(hitbox.getX(), hitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + hitbox.height - collisionBuffer - 2, hitbox.length + collisionBuffer).isEmpty()) {
                        Hitbox hitboxes = entitiesInArea(hitbox.getX(), hitbox.getY() - GamePanel.tileDimensions + collisionBuffer, GamePanel.tileDimensions + hitbox.height - collisionBuffer - 2, hitbox.length).getFirst().getHitbox();
                        if (hitbox.getX() < hitboxes.getX() + hitboxes.getLength() && hitbox.getY() < hitboxes.getY() + hitboxes.getHeight() - collisionBuffer)
                            return false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("update error Entity.java: 83");
        }
        return true;
    }

    public void attack() {
        ArrayList<Entity> enemiesAttacked = new ArrayList<>();
        switch (direction) {
            case "up" -> {
                enemiesAttacked = entitiesInArea(x + hitbox.getLength() / 2 - GamePanel.tileDimensions - attackThickness / 2, y - range - GamePanel.tileDimensions, range + GamePanel.tileDimensions, attackThickness + GamePanel.tileDimensions);
                enemiesAttacked.removeIf(entity -> (entity.getHitbox().getY() + entity.getHitbox().getHeight() < y - range || entity.getHitbox().getX() + entity.getHitbox().getLength() < (x + hitbox.getLength() / 2 - attackThickness / 2)));
            }
            case "down" -> {
                enemiesAttacked = entitiesInArea(x + hitbox.getLength() / 2 - GamePanel.tileDimensions - attackThickness / 2 - 2, y + hitbox.getHeight(), attackThickness + GamePanel.tileDimensions, range);
                enemiesAttacked.removeIf(entity -> entity.getHitbox().getX() + entity.getHitbox().getLength() < (x + hitbox.getLength() / 2 - attackThickness / 2));
            }
            case "left" -> {
                enemiesAttacked = entitiesInArea(x - range - GamePanel.tileDimensions, y + hitbox.getHeight() / 2 - GamePanel.tileDimensions, attackThickness + GamePanel.tileDimensions, range + GamePanel.tileDimensions);
                enemiesAttacked.removeIf(entity -> entity.getHitbox().getX() + entity.getHitbox().getLength() < x - range);
            }
            case "right" ->
                    enemiesAttacked = entitiesInArea(x + hitbox.getLength() - 2, y + hitbox.getHeight() / 2 - GamePanel.tileDimensions, range, attackThickness + GamePanel.tileDimensions);

        }

        for (Entity entity : enemiesAttacked) {
            if (entity instanceof Enemy) ((Enemy) entity).attacked();
        }
    }

    public void entityMove() {
        int x;
        int y;
        String moveDirection;

        // determine the tiles the player is on
        int left = ((hitbox.getX() + vx) - ((hitbox.getX() + vx) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        int up = ((hitbox.getY() + vy) - ((hitbox.getY() + vy) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        int down = ((hitbox.getY() + vy + GamePanel.tileDimensions - (GamePanel.tileDimensions - hitbox.getHeight())) - ((hitbox.getY() + vy - (GamePanel.tileDimensions - hitbox.getHeight())) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        int right = ((hitbox.getX() + hitbox.getLength() + vx) - ((hitbox.getX() + hitbox.getLength() + vx) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;

        if (!isAttacking) {
            if (vy <= 0) {
                moveDirection = "up";
                y = up;
            } else {
                moveDirection = "down";
                y = down;
            }

            int[] hitboxBounds = hitbox.getHitboxBounds();
            if (entityCollision(moveDirection) && (canWalkOn(hitboxBounds[0], y) && canWalkOn(hitboxBounds[1], y))) {
                this.y += vy;
                hitbox.y += vy;
            }

            if (vx <= 0) {
                moveDirection = "left";
                x = left;
            } else {
                moveDirection = "right";
                x = right;
            }
            if (entityCollision(moveDirection) && (canWalkOn(x, hitboxBounds[2]) && canWalkOn(x, hitboxBounds[3]))) {
                this.x += vx;
                hitbox.x += vx;
            }

            if (this instanceof Player) {
                ((Player) this).changeMap(x, y);
            }
        }
    }

    public boolean canWalkOn(int x, int y) {
        String key = x + " " + y;
        return (MapLoader.getMap().containsKey(key) && MapLoader.getMap().get(key).walkable);
    }

    public void setDirection() {
        direction = "left";
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

        for (Hitbox hitbox : entityHitBoxes) {
            if (checkbound(hitbox.getX(), x, x + length) && checkbound(hitbox.getY(), y, y + height)) {
                entities.add(hitbox.entity);
            }
        }
        return entities;
    }

    public boolean checkbound(int check, int min, int max) {
        return ((check > min) && (check < max));
    }

    public abstract void draw(Graphics2D g2d);

    public abstract void run();
}