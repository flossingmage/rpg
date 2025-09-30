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

    public Dummy(int x, int y) {
        super(x, y);
        try {
            idl = ImageIO.read(new File("res/enemies/dummy/dummy0.png"));
            hit1 = ImageIO.read(new File("res/enemies/dummy/dummy1.png"));
            hit2 = ImageIO.read(new File("res/enemies/dummy/dummy2.png"));
        } catch (IOException e) {
            System.out.println("can't load dummy imgs");
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(idl, x * GamePanel.tileDimensions, y * GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions, null);
    }

}
