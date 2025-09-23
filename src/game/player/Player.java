package game.player;

import game.GamePanel;
import game.Level.MapLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player {

    int x, y, vx, vy;
    int speed = 2;
    int playerDimension = 30;

    BufferedImage playerIdl;

    public Player() {
        this.x = 100;
        this.y = 100;
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
        if ((MapLoader.getMap().containsKey(left + " " + y) && MapLoader.getMap().containsKey(right + " " + y)) && (MapLoader.getMap().get(left + " " + y).walkable) && (MapLoader.getMap().get(right + " " + y).walkable)) {
            this.y += vy;
        }
        if ((MapLoader.getMap().containsKey(x + " " + up) && MapLoader.getMap().containsKey(x + " " + down)) && (MapLoader.getMap().get(x + " " + up).walkable) && (MapLoader.getMap().get(x + " " + down).walkable)) {
            this.x += vx;
        }

        changeMap(x, y);
    }

    public void changeMap(int x, int y) {
        for (String[] exit : MapLoader.getExits()) {
            if (Integer.parseInt(exit[0]) == x && Integer.parseInt(exit[1]) == y) {
                System.out.println("change map to " + exit[2]);
                MapLoader.loadMap(exit[2]);
                this.x = Integer.parseInt(exit[3]) * GamePanel.tileDimensions;
                this.y = Integer.parseInt(exit[4]) * GamePanel.tileDimensions;
                break;
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.green);
        g2d.drawImage(playerIdl, x, y, 32, 32, null);
    }
}
