package game;

import game.Level.MapLoader;
import game.player.KeyHandler;
import game.player.Player;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GamePanel extends JPanel implements Runnable {

    public static int tileDimensions = 32;
    public static int rows = 20;
    public static int columns = 25;

    int screenWidth = tileDimensions * columns;
    int screenHeight = tileDimensions * rows;

    MapLoader mapLoader = new MapLoader();
    Player player = new Player();
    Thread gameThread;

    int FPSLimit = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.addKeyListener(new KeyHandler(player));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        MapLoader.loadMap("1");
    }

    public void startThreads() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        while (gameThread != null) {
            player.playerMove();
            repaint();
            try {
                sleep(1000 / FPSLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        mapLoader.drawMap(g2d);
        player.draw(g2d);
    }


}
