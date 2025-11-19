package game.objects.weapons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GreatSword extends Weapon {
    static BufferedImage img;

    static {
        try {
            img = ImageIO.read(new File("res/weapons/greatSwordICON.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GreatSword() {
        super(50, 100, 75, "greatSword", "slash", img);
    }
}
