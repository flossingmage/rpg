package game.entities.player;

import game.GamePanel;
import game.entities.Entity;
import game.entities.EntityLoader;
import game.level.MapLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player extends Entity {

    int vx, vy;
    int speed = 2;
    int playerDimension = 30;

    boolean isAttacking = false;

    int tick = 0;

    BufferedImage playerIdl;

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
            String direction;
            if (vy < 0) {
                direction = "up";
            }else{
                direction = "down";
            }
            if (entityCollision(direction) && ( MapLoader.getMap().containsKey(left + " " + y) && MapLoader.getMap().containsKey(right + " " + y)) && (MapLoader.getMap().get(left + " " + y).walkable) && (MapLoader.getMap().get(right + " " + y).walkable)) {
                this.y += vy;
            }
            if (vx < 0) {
                direction = "left";
            }else{
                direction = "right";
            }
            if (entityCollision(direction) && (MapLoader.getMap().containsKey(x + " " + up) && MapLoader.getMap().containsKey(x + " " + down)) && (MapLoader.getMap().get(x + " " + up).walkable) && (MapLoader.getMap().get(x + " " + down).walkable)) {
                this.x += vx;
            }
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
        isAttacking = true;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.drawImage(playerIdl, x, y, 32, 32, null);
        if (isAttacking) {
            tick++;
            if (tick == 60) {
                isAttacking = false;
                tick = 0;
            }
            g2d.drawRect((x + playerDimension), (y + playerDimension / 2), 30, 10);
        }
    }
}
