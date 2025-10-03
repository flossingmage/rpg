package game.entities.player;

import game.GamePanel;
import game.entities.Entity;
import game.entities.EntityLoader;
import game.entities.enemies.Enemy;
import game.level.MapLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Player extends Entity {

    int vx, vy;
    int speed = 2;
    int playerDimension = 30;
    String[] direction = new String[2];

    boolean isAttacking = false;

    int tick = 0;

    BufferedImage playerIdl;

    //temp
    int range = 40;
    int attackThickness = 10;

    public Player() {
        super(100, 100);
        try {
            playerIdl = ImageIO.read(new File("res/player/playerIdl.png"));
        } catch (IOException e) {
            System.out.println("Can't load player imgs");
        }
    }

    public void setVelocity(boolean up, boolean down, boolean left, boolean right) {
        vx = 0;
        vy = 0;
        if (up) vy -= speed;
        if (down) vy += speed;
        if (left) vx -= speed;
        if (right) vx += speed;
    }

    public void MouseClicked() {
        isAttacking = true;
        System.out.println("click");
    }

    public void playerMove() {
        int x;
        int y;

        int left = ((this.x + vx) - ((this.x + vx) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        int up = ((this.y + vy) - ((this.y + vy) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        int down = ((this.y + playerDimension + vy) - ((this.y + playerDimension + vy) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        int right = ((this.x + playerDimension + vx) - ((this.x + playerDimension + vx) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;

        if (vx < 0) {
            x = left;
        } else if (vx > 0) {
            x = right;
        } else {
            x = (left + right) / 2;
        }
        if (vy < 0) {
            y = up;
        } else {
            y = down;
        }
        if (!isAttacking) {
            if (vy < 0) {
                direction[0] = "up";
            } else if (vy > 0) {
                direction[0] = "down";

            } else {
                direction[0] = "null";

            }
            if (vx < 0) {
                direction[1] = "left";
            } else if (vx > 0) {
                direction[1] = "right";
            } else {
                direction[1] = "null";
            }

            if (entityCollision(direction[0]) && (MapLoader.getMap().containsKey(left + " " + y) && MapLoader.getMap().containsKey(right + " " + y)) && (MapLoader.getMap().get(left + " " + y).walkable) && (MapLoader.getMap().get(right + " " + y).walkable))
                this.y += vy;
            if (entityCollision(direction[1]) && (MapLoader.getMap().containsKey(x + " " + up) && MapLoader.getMap().containsKey(x + " " + down)) && (MapLoader.getMap().get(x + " " + up).walkable) && (MapLoader.getMap().get(x + " " + down).walkable))
                this.x += vx;
        }

        changeMap(x, y);
    }


    public void changeMap(int x, int y) {
        for (String[] exit : MapLoader.getExits()) {
            if (Integer.parseInt(exit[0]) == x && Integer.parseInt(exit[1]) == y) {
                System.out.println("change map to " + exit[2]);
                MapLoader.loadMap(exit[2]);
                EntityLoader.loadEntities(exit[2]);
                this.x = Integer.parseInt(exit[3]) * GamePanel.tileDimensions;
                this.y = Integer.parseInt(exit[4]) * GamePanel.tileDimensions;
                break;
            }
        }
    }

    public void attack() {
        ArrayList<Entity> enemiesAttacked = new ArrayList<>();
        if (direction[1].equals("null")) {
            if (direction[0].equals("up"))
                enemiesAttacked = entitiesInArea(x + playerDimension / 2 - GamePanel.tileDimensions, y - range, attackThickness + GamePanel.tileDimensions, range);
            if (direction[0].equals("down"))
                enemiesAttacked = entitiesInArea(x + playerDimension / 2 - GamePanel.tileDimensions, y + playerDimension, attackThickness + GamePanel.tileDimensions, range);
        }
        if (direction[1].equals("left"))
            enemiesAttacked = entitiesInArea(x - range, y + playerDimension / 2 - GamePanel.tileDimensions, range, attackThickness);
        if (direction[1].equals("right") || (direction[1].equals("null") && direction[0].equals("null")))
            enemiesAttacked = entitiesInArea(x + playerDimension, y + playerDimension / 2 - GamePanel.tileDimensions, range, attackThickness);
        for (Entity entity : enemiesAttacked) {
            if (entity instanceof Enemy) ((Enemy) entity).attacked();
        }
    }


    public void drawAttack(Graphics2D g2d) {
        if (direction[1].equals("null")) {
            if (direction[0].equals("up")) g2d.drawRect(x + playerDimension / 2, y - range, attackThickness, range);
            if (direction[0].equals("down")) g2d.drawRect(x + playerDimension / 2, y + playerDimension, attackThickness, range);
        }
        if (direction[1].equals("left")) g2d.drawRect(x - range, y + playerDimension / 2, range, attackThickness);
        if (direction[1].equals("right") || (direction[1].equals("null") && direction[0].equals("null")))
            g2d.drawRect(x + playerDimension, y + playerDimension / 2, range, attackThickness);
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.drawImage(playerIdl, x + ((Math.abs(playerDimension - GamePanel.tileDimensions)) / 2), y + (Math.abs(playerDimension - GamePanel.tileDimensions) / 2), playerDimension, playerDimension, null);
        if (isAttacking) {
            tick++;
            if (tick == 60) {
                isAttacking = false;
                tick = 0;
            }
            attack();
            drawAttack(g2d);
        }
    }
}
