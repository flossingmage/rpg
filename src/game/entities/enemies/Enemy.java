package game.entities.enemies;

import game.GamePanel;
import game.entities.Entity;
import game.entities.Hitbox;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {

    boolean isAttacked;
    boolean isAttacking;

    int animationTime = 30;
    int animationTick = 0;

    int agroRange;
    Entity target;
    int speed = 1;


    public Enemy(int x, int y, int agroRange) {
        super(x, y);
        this.agroRange = agroRange;
    }

    public Enemy(int x, int y, int length, int height, int agroRange) {
        super(x, y, length, height);
        this.agroRange = agroRange;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(getDrawnImg(), x, y, GamePanel.tileDimensions, GamePanel.tileDimensions, null);
        g2d.drawRect(x,y,GamePanel.tileDimensions,GamePanel.tileDimensions);
    }

    public BufferedImage getDrawnImg() {
        return ErrorImg;
    }

    public void attacked() {
        isAttacked = true;
        System.out.println("attacked");
    }

    public void scanForPlayer() {
        Hitbox player = GamePanel.player.getHitbox();
        if ((player.getX() < x + agroRange * GamePanel.tileDimensions && player.getX() > x - agroRange * GamePanel.tileDimensions) && (player.getY() < y + agroRange * GamePanel.tileDimensions && player.getY() > y - agroRange * GamePanel.tileDimensions)){
            target = GamePanel.player;
            System.out.println("found player");
        }
    }

    public void run() {
        if (target != null) {
            goToTarget();
        } else {
            scanForPlayer();
        }
    }

    public void goToTarget(){
        setVelocity();
        entityMove();

    }

    public void setVelocity() {
        if (target.getHitbox().getX() > getHitbox().getX()){
            vx = speed;
        }else if (target.getHitbox().getX() < getHitbox().getX()) {
            vx = -speed;
        }else{
            vx = 0;
        }

        if (target.getHitbox().getY() > getHitbox().getY()){
            vy = speed;
        }else if (target.getHitbox().getY() < getHitbox().getY()){
            vy = -speed;
        }else{
            vy = 0;
        }
    }


}