package game.entities;

import game.GamePanel;
import game.entities.enemies.Dummy;
import game.entities.enemies.Enemy;
import game.entities.enemies.Slime;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class EntityLoader {
    static HashMap<String, Enemy> enemies = new HashMap<>();
    static HashMap<String, Hitbox> entityHitBoxes = new HashMap<>();

    public static void loadEntities(String fileName) {
        entityHitBoxes.clear();
        enemies.clear();
        try {
            Scanner scanner = new Scanner(new File("res/level/levels/" + fileName + "/entities"));
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                Enemy enemy;
                switch (line[0]) {
                    case "Dummy" -> enemy = new Dummy(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    case "Slime" -> enemy = new Slime(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    default -> enemy = new Enemy(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                }
                enemies.put((Integer.parseInt(line[1]) * GamePanel.tileDimensions) + " " + (Integer.parseInt(line[2]) * GamePanel.tileDimensions), enemy);
                entityHitBoxes.put(enemy.getHitbox().getX() + " " + enemy.getHitbox().getY(), enemy.getHitbox());
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void drawEntities(Graphics2D g2d) {
        for (String enemy : enemies.keySet()) {
            enemies.get(enemy).draw(g2d);
        }
        for (String hitbox : entityHitBoxes.keySet()) {
            entityHitBoxes.get(hitbox).drawHitBox(g2d);
        }
    }
}