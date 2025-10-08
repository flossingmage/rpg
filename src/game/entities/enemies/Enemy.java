package game.entities.enemies;

import game.GamePanel;
import game.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    boolean isAttacked;
    boolean isAttacking;

    int animationTime = 30;
    int animationTick = 0;

    

    public Enemy(int x, int y) {
        super(x, y);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawRect(x * GamePanel.tileDimensions, y * GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions);
        g2d.drawImage(getDrawnImg(), x * GamePanel.tileDimensions, y * GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions, null);
    }

    public BufferedImage getDrawnImg() {
        return ErrorImg;
    }

    public void attacked() {
        isAttacked = true;
    }
    
}
