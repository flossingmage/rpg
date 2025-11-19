package game.entities.player;

import game.GamePanel;
import game.entities.Entity;
import game.entities.EntityLoader;
import game.level.MapLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player extends Entity {
    int speed = 2;
    static int playerDimension = 30;
    int tick = 0;

    static BufferedImage playerIdl;


    static {
        try {
            playerIdl = ImageIO.read(new File("res/player/humanBasic.png"));
        } catch (IOException e) {
            System.out.println("Can't load player imgs");
        }
    }

    public Player() {
        super(100, 100, playerDimension, playerDimension);
    }

    public void setVelocity(boolean up, boolean down, boolean left, boolean right) {
        vx = 0;
        vy = 0;
        if (up) vy -= speed;
        if (down) vy += speed;
        if (left) vx -= speed;
        if (right) vx += speed;
    }

    public void MouseClicked() {
        isAttacking = true;
    }

    public void changeMap(int x, int y) {
        for (String[] exit : MapLoader.getExits()) {
            if (Integer.parseInt(exit[0]) == x && Integer.parseInt(exit[1]) == y) {
                System.out.println("change map to " + exit[2]);
                MapLoader.loadMap(exit[2]);
                EntityLoader.loadEntities(exit[2]);
                getHitbox().setX(Integer.parseInt(exit[3]) * GamePanel.tileDimensions);
                getHitbox().setY(Integer.parseInt(exit[4]) * GamePanel.tileDimensions);
                break;
            }
        }
    }

    public void setDirection(int mouseX, int mouseY) {
        if (!isAttacking) {
            int relativeX = mouseX - getHitbox().getX();
            int relativeY = mouseY - getHitbox().getY();
            if (relativeX > relativeY) {
                if (relativeX < -relativeY) {
                    direction = "up";

                } else {
                    direction = "right";
                }

            }
            if (relativeX < relativeY) {
                if (relativeX > -relativeY) {
                    direction = "down";

                } else {
                    direction = "left";
                }
            }
        }
    }

//    //todo make relive to hitbox and move up to entity
//    public void drawAttackHitBox(Graphics2D g2d) {
//        g2d.setColor(Color.blue);
//        switch (direction) {
//            case "up" ->
//                    g2d.drawRect(x + playerDimension / 2 - GamePanel.tileDimensions - attackThickness / 2, y - range - GamePanel.tileDimensions, attackThickness + GamePanel.tileDimensions, range + GamePanel.tileDimensions);
//            case "down" ->
//                    g2d.drawRect(x + playerDimension / 2 - GamePanel.tileDimensions - attackThickness / 2, y + playerDimension, attackThickness + GamePanel.tileDimensions, range);
//            case "left" ->
//                    g2d.drawRect(x - range - GamePanel.tileDimensions, y + playerDimension / 2 - GamePanel.tileDimensions, range + GamePanel.tileDimensions, attackThickness + GamePanel.tileDimensions);
//            case "right" ->
//                    g2d.drawRect(x + playerDimension, y + playerDimension / 2 - GamePanel.tileDimensions, range, attackThickness + GamePanel.tileDimensions);
//
//        }
//
//    }

    //todo make relive to hitbox and move up to entity
    public void drawAttack(Graphics2D g2d) {
        g2d.setColor(Color.red);
        switch (direction) {
            case "up" -> g2d.drawRect(getHitbox().getX() + playerDimension / 2 - attackThickness / 2, getHitbox().getY() - range, attackThickness, range);
            case "down" ->
                    g2d.drawRect(getHitbox().getX() + playerDimension / 2 - attackThickness / 2, getHitbox().getY() + playerDimension, attackThickness, range);
            case "left" -> g2d.drawRect(getHitbox().getX() - range, getHitbox().getY() + playerDimension / 2, range, attackThickness);
            case "right" -> g2d.drawRect(getHitbox().getX() + playerDimension, getHitbox().getY() + playerDimension / 2, range, attackThickness);
        }
    }


//    // Flip the image vertically
//    AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
//tx.translate(0, -image.getHeight(null));
//    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//    image = op.filter(image, null);
//
//
//// Flip the image horizontally
//    tx = AffineTransform.getScaleInstance(-1, 1);
//tx.translate(-image.getWidth(null), 0);
//    op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//    image = op.filter(image, null);
//
//// Flip the image vertically and horizontally; equivalent to rotating the image 180 degrees
//    tx = AffineTransform.getScaleInstance(-1, -1);
//tx.translate(-image.getWidth(null), -image.getHeight(null));
//    op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//    image = op.filter(image, null);


    public void draw(Graphics2D g2d) {
        g2d.drawImage(playerIdl, getHitbox().getX() + ((Math.abs(playerDimension - GamePanel.tileDimensions)) / 2), getHitbox().getY() + (Math.abs(playerDimension - GamePanel.tileDimensions) / 2), playerDimension, playerDimension, null);
        if (isAttacking) {
            tick++;
            if (tick == 30) {
                isAttacking = false;
                tick = 0;
            }
            drawAttack(g2d);
        }
    }

    @Override
    public void run() {
        entityMove();
        if (isAttacking)
            attack(this);
    }
}