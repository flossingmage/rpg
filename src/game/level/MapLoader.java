package game.level;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MapLoader {

    static Tile grass;

    static {
        try {
            grass = new Tile(ImageIO.read(new File("res/level/tiles/grass.png")), true);
            water = new Tile(ImageIO.read(new File("res/level/tiles/water.png")), false);
            sand = new Tile(ImageIO.read(new File("res/level/tiles/sand.jpg")), true);
            tree = new Tile(ImageIO.read(new File("res/level/tiles/tree.png")), false);
            dirt_road = new Tile(ImageIO.read(new File("res/level/tiles/Dirt_road.png")), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Tile water;
    static Tile sand;
    static Tile dirt_road;
    static Tile tree;

    static HashMap<String, Tile> map = new HashMap<>();
    static ArrayList<String[]> exits = new ArrayList<>();

    public static void loadMap(String MapName) {
        map.clear();
        exits.clear();

        try {
            Scanner scan = new Scanner(new File("res/level/levels/" + MapName + "/map"));

            int y = 0;
            for (int loop = 0; loop < 20; loop++) {
                int x = 0;
                String[] line = scan.nextLine().split(" ");
                for (String tile : line) {
                    switch (tile) {
                        case "0":
                            map.put(x + " " + y, grass);
                            break;
                        case "1":
                            map.put(x + " " + y, water);
                            break;
                        case "2":
                            map.put(x + " " + y, sand);
                            break;
                        case "3":
                            map.put(x + " " + y, dirt_road);
                            break;
                        case "4":
                            map.put(x + " " + y, tree);
                            break;
                    }
                    x += 1;
                }
                y += 1;
            }
            while (scan.hasNextLine()) {
                exits.add(scan.nextLine().split(" "));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("can't get the map");

        }
    }

    public static void drawMap(Graphics2D g2d) {
        for (int x = 0; x < GamePanel.columns; x++) {
            for (int y = 0; y < GamePanel.rows; y++)
                try {
                    if (map.containsKey(x + " " + y))
                        g2d.drawImage(map.get(x + " " + y).img, x * GamePanel.tileDimensions, y * GamePanel.tileDimensions, GamePanel.tileDimensions, GamePanel.tileDimensions, null);
                } catch (Exception e) {
                    System.out.println("NullPointerException @MapLoader 86");
                }
        }
    }

    public static HashMap<String, Tile> getMap() {
        return map;
    }

    public static ArrayList<String[]> getExits() {
        return exits;
    }
}