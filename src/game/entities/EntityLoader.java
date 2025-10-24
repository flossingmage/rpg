package game.entities;

import game.entities.enemies.Dummy;
import game.entities.enemies.ErrorEntity;
import game.entities.enemies.Slime;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class EntityLoader {
    public static ArrayList<Hitbox> entityHitBoxes = new ArrayList<>();

    public static void loadEntities(String fileName) {
        entityHitBoxes.clear();
        try {
            Scanner scanner = new Scanner(new File("res/level/levels/" + fileName + "/entities"));
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                Entity entity;
                switch (line[0]) {
                    case "Dummy" -> entity = new Dummy(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    case "Slime" -> entity = new Slime(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    default -> entity = new ErrorEntity(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                }
                entityHitBoxes.add(entity.hitbox);
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void drawEntities(Graphics2D g2d) {
        for (Hitbox hitbox : entityHitBoxes) {
            hitbox.drawHitBox(g2d);
            hitbox.entity.draw(g2d);
            hitbox.entity.run();
        }
    }
}