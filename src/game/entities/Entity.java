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
    // todo remove x, y
    protected int vx, vy;
    protected String direction;
    protected boolean isAttacking = false;

    protected int health = 100;

    //temp stats will be stored in weapon object later
    protected int range = 40;
    protected int attackThickness = 10;
    protected int knockBack = 30;
    protected int damage = 50;

    Hitbox hitbox;

    // for entities the size of the game tile
    public Entity(int x, int y) {
        hitbox = new Hitbox(x, y, GamePanel.tileDimensions, GamePanel.tileDimensions, this);
    }

    // for entities that arn't the size of the game
    public Entity(int x, int y, int length, int height) {
        hitbox = new Hitbox(x, y, length, height, this);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void setDirection() {
        direction = "left";
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
            if (CollisionHandler.entityCollision(moveDirection, getHitbox()) && (CollisionHandler.canWalkOn(hitboxBounds[0], y) && CollisionHandler.canWalkOn(hitboxBounds[1], y))) {
                hitbox.y += vy;
            }

            if (vx <= 0) {
                moveDirection = "left";
                x = left;
            } else {
                moveDirection = "right";
                x = right;
            }
            if (CollisionHandler.entityCollision(moveDirection, getHitbox()) && (CollisionHandler.canWalkOn(x, hitboxBounds[2]) && CollisionHandler.canWalkOn(x, hitboxBounds[3]))) {
                hitbox.x += vx;
            }

            if (this instanceof Player) {
                ((Player) this).changeMap(x, y);
            }
        }
    }

    public void attack(Entity attacker) {
        ArrayList<Entity> enemiesAttacked = new ArrayList<>();
        switch (direction) {
            case "up" -> {
                enemiesAttacked = CollisionHandler.entitiesInArea(hitbox.getX() + hitbox.getLength() / 2 - GamePanel.tileDimensions - attackThickness / 2, hitbox.getY() - range - GamePanel.tileDimensions, range + GamePanel.tileDimensions, attackThickness + GamePanel.tileDimensions);
                enemiesAttacked.removeIf(entity -> (entity.getHitbox().getY() + entity.getHitbox().getHeight() < hitbox.getY() - range || entity.getHitbox().getX() + entity.getHitbox().getLength() < (hitbox.getX()  + hitbox.getLength() / 2 - attackThickness / 2)));
            }
            case "down" -> {
                enemiesAttacked = CollisionHandler.entitiesInArea(hitbox.getX()  + hitbox.getLength() / 2 - GamePanel.tileDimensions - attackThickness / 2 - 2, hitbox.getY() + hitbox.getHeight() - 4, attackThickness + GamePanel.tileDimensions + 4, range);
                enemiesAttacked.removeIf(entity -> entity.getHitbox().getX() + entity.getHitbox().getLength() < (hitbox.getX()  + hitbox.getLength() / 2 - attackThickness / 2));
            }
            case "left" -> {
                enemiesAttacked = CollisionHandler.entitiesInArea(hitbox.getX()  - range - GamePanel.tileDimensions, hitbox.getY() + hitbox.getHeight() / 2 - GamePanel.tileDimensions, attackThickness + GamePanel.tileDimensions, range + GamePanel.tileDimensions);
                enemiesAttacked.removeIf(entity -> entity.getHitbox().getX() + entity.getHitbox().getLength() < hitbox.getX()  - range);
            }
            case "right" ->
                    enemiesAttacked = CollisionHandler.entitiesInArea(hitbox.getX()  + hitbox.getLength() - 2, hitbox.getY() + hitbox.getHeight() / 2 - GamePanel.tileDimensions, range, attackThickness + GamePanel.tileDimensions);

        }

        for (Entity entity : enemiesAttacked) {
            if (entity instanceof Enemy) ((Enemy) entity).attacked(attacker);
        }
    }

    public abstract void draw(Graphics2D g2d);

    public abstract void run();

    public int getKnockBack() {
        return knockBack;
    }

    public String getDirection() {
        return direction;
    }

    public int getDamage() {
        return damage;
    }
}