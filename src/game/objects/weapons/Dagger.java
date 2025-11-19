package game.objects.weapons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dagger extends Weapon{
    static BufferedImage img;

    static {
        try {
            img = ImageIO.read(new File("res/weapons/daggerICON.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Dagger() {
        super(30, 30, 30, "dagger", "slash", img);
    }
}
