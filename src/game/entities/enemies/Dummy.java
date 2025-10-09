package game.entities.enemies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dummy extends Enemy {
    static BufferedImage idl;
    static BufferedImage hit1;
    static BufferedImage hit2;

    static {
        try {
            idl = ImageIO.read(new File("res/enemies/dummy/dummy0.png"));
            hit1 = ImageIO.read(new File("res/enemies/dummy/dummy1.png"));
            hit2 = ImageIO.read(new File("res/enemies/dummy/dummy2.png"));
        } catch (IOException e) {
            System.out.println("can't load dummy imgs");
        }
    }

    public Dummy(int x, int y) {
        super(x, y, 8,16);
    }

    @Override
    public BufferedImage getDrawnImg() {
        if (isAttacked) {
            animationTick++;
            if (animationTick == animationTime) {
                isAttacked = false;
                animationTick = 0;
            } else if (animationTick > (animationTime / 4) * 3) {
                return hit1;
            } else if (animationTick > (animationTime / 2)) {
                return hit2;
            } else {
                return hit1;
            }
        }
        return idl;
    }
}