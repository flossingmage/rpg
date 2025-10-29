package game.entities.enemies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ErrorEntity extends Enemy {

    static BufferedImage sprit;

    static {
        try {
            sprit = ImageIO.read(new File("res/enemies/error.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ErrorEntity(int x, int y) {
        super(x, y, 0);
    }

    @Override
    public BufferedImage getDrawnImg() {
        return sprit;
    }
}
