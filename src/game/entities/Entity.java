package game.entities;

import game.GamePanel;

public class Entity {
    protected int x;
    protected int y;

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean entityCollision(String direction) {
        for (int two = -GamePanel.tileDimensions; two < GamePanel.tileDimensions; two++) {
            for (int one = 0; one < GamePanel.tileDimensions + 1; one++) {
                switch (direction){
                    case "up" -> {
                        if (EntityLoader.enemies.containsKey(((x + two) + " " + (y - one)))) return false;
                    }
                    case "down" -> {
                        if (EntityLoader.enemies.containsKey(((x + two) + " " + (y + one)))) return false;
                    }
                    case "left" -> {
                        if (EntityLoader.enemies.containsKey(((x - one) + " " + (y + two)))) return false;
                    }
                    case "right" -> {
                        if (EntityLoader.enemies.containsKey(((x + one) + " " + (y + two)))) return false;
                    }
                }
            }
        }
        return true;
    }
}