package game.entities;

import game.GamePanel;

import java.awt.*;

public class Hitbox {
    int x, y, length, height;
    Entity entity;

    public Hitbox(int x, int y, int length, int height, Entity entity) {
        this.x = x + Math.abs((GamePanel.tileDimensions - length) / 2);
        this.y = y + Math.abs(GamePanel.tileDimensions - height);
        this.length = length;
        this.height = height;
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void drawHitBox(Graphics2D g2d) {
        g2d.setColor(Color.pink);
        g2d.drawRect(x, y, length, height);
    }

    // todo need to remake this
    public int[] getHitboxBounds() {
        int[] hitbox = new int[4];
        //left
        hitbox[0] = (getX() - (getX() % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        //right
        hitbox[1] = ((getX() + getLength()) - ((getX() + getLength()) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        //up
        hitbox[2] = (getY() - (getY() % GamePanel.tileDimensions)) / GamePanel.tileDimensions;
        //down
        hitbox[3] = ((getY() + GamePanel.tileDimensions - (GamePanel.tileDimensions - getHeight())) - ((getY() - (GamePanel.tileDimensions - getHeight())) % GamePanel.tileDimensions)) / GamePanel.tileDimensions;

        return hitbox;
    }
}