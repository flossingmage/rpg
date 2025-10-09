package game.entities;

import game.GamePanel;

import java.awt.*;

public class Hitbox {
    int x,y,length,height;
    Entity entity;

    public Hitbox(int x, int y, int length, int height, Entity entity){
        this.x = x * GamePanel.tileDimensions + (GamePanel.tileDimensions/2 - length);
        this.y = y * GamePanel.tileDimensions + ((GamePanel.tileDimensions - height * 2) % GamePanel.tileDimensions) ;
        this.length = length * 2;
        this.height = height * 2;
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

    public void drawHitBox(Graphics2D g2d){
        g2d.setColor(Color.pink);
        g2d.drawRect(x,y,length,height);
    }
}