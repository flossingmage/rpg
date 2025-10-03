package game.entities;

import game.GamePanel;
import game.entities.enemies.Dummy;
import game.entities.enemies.Enemy;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class EntityLoader {
    static HashMap<String, Enemy> enemies = new HashMap<>();

    public static void loadEntities(String fileName) {
        enemies.clear();
        try {
            Scanner scanner = new Scanner(new File("res/level/levels/" + fileName + "/entities"));
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                enemies.put((Integer.parseInt(line[1]) * GamePanel.tileDimensions) + " " + (Integer.parseInt(line[2]) * GamePanel.tileDimensions), new Dummy(Integer.parseInt(line[1]), Integer.parseInt(line[2])));
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
    }
}
