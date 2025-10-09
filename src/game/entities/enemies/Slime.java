package game.entities.enemies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Slime extends Enemy {

    static BufferedImage idl;
    static BufferedImage attackLEnd;
    static BufferedImage attackLMid;
    static BufferedImage attackREnd;
    static BufferedImage attackRMid;

    static {
        try {
            idl = ImageIO.read(new File("res/enemies/slime/slimeIdl.png"));
            attackLEnd = ImageIO.read(new File("res/enemies/slime/attackLEnd.png"));
            attackLMid = ImageIO.read(new File("res/enemies/slime/attackLMid.png"));
            attackREnd = ImageIO.read(new File("res/enemies/slime/attackREnd.png"));
            attackRMid = ImageIO.read(new File("res/enemies/slime/attackRMid.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Slime(int x, int y) {
        super(x, y, 16,9);
    }

    @Override
    public BufferedImage getDrawnImg(){
        return idl;
    }
}