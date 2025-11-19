package game.objects.weapons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spear extends Weapon {
    static BufferedImage img;

    static {
        try {
            img = ImageIO.read(new File("res/weapons/spearICON.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Spear() {
        super(50, 100, 20, "spear", "thrust", img);
    }

}
