package game.entities;

import game.GamePanel;
import game.entities.enemies.Dummy;
import game.entities.enemies.ErrorEntity;
import game.entities.enemies.Slime;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EntityLoader {
    public static ArrayList<Hitbox> entityHitBoxes = new ArrayList<>();

    public static void loadEntities(String fileName) {
        entityHitBoxes.clear();
        Scanner scanner;

        try {
            scanner = new Scanner(new File("res/level/levels/" + fileName + "/entities"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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
            entityHitBoxes.add(GamePanel.player.getHitbox());
            scanner.close();
    }


    // make for loop safe;
    public static void drawEntities(Graphics2D g2d) {
        for (Hitbox hitbox : entityHitBoxes) {
                hitbox.entity.draw(g2d);
                hitbox.drawHitBox(g2d);
        }
    }

    public static void entitiesRun() {
            for (Hitbox hitbox : new ArrayList<>(entityHitBoxes))
                hitbox.entity.run();
    }
}