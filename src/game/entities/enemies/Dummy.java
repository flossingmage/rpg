package game.entities.enemies;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dummy extends Enemy {
    BufferedImage idl;
    BufferedImage hit1;
    BufferedImage hit2;

    BufferedImage drawnImg;
    int attackedTime = 30;

    boolean isAttacked;
    int attackedTick = 0;

    public Dummy(int x, int y) {
        super(x, y);
        try {
            idl = ImageIO.read(new File("res/enemies/dummy/dummy0.png"));
            hit1 = ImageIO.read(new File("res/enemies/dummy/dummy1.png"));
            hit2 = ImageIO.read(new File("res/enemies/dummy/dummy2.png"));
        } catch (IOException e) {
            System.out.println("can't load dummy imgs");
        }
        drawnImg = idl;
    }

    @Override
    public void attacked() {
        isAttacked = true;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawRect(x * GamePanel.tileDimensions, y * GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions);
        if (isAttacked) {
            attackedTick++;
            if (attackedTick == attackedTime) {
                drawnImg = idl;
                isAttacked = false;
                attackedTick = 0;
            } else if (attackedTick > (attackedTime / 4) * 3) {
                drawnImg = hit1;
            } else if (attackedTick > (attackedTime / 2)) {
                drawnImg = hit2;
            } else {
                drawnImg = hit1;
            }
        }

        g2d.drawImage(drawnImg, x * GamePanel.tileDimensions, y * GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions, null);
    }

}
