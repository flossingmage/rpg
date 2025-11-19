package game.objects.weapons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sword extends Weapon {

    static BufferedImage img;

    static {
        try {
            img = ImageIO.read(new File("res/weapons/swordICON.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Sword() {
        super(30, 75, 50, "sword", "slash", img);
    }
}
