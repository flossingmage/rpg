package game;

import game.entities.EntityLoader;
import game.level.MapLoader;
import game.entities.player.KeyHandler;
import game.entities.player.MouseHandler;
import game.entities.player.Player;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GamePanel extends JPanel implements Runnable {

    public static int tileDimensions = 32;
    public static int rows = 20;
    public static int columns = 25;

    int screenWidth = tileDimensions * columns;
    int screenHeight = tileDimensions * rows;

    Player player = new Player();
    Thread gameThread;

    int FPSLimit = 60;

    MouseHandler mouseHandler = new MouseHandler(player);


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.addKeyListener(new KeyHandler(player));
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        MapLoader.loadMap("1");
        EntityLoader.loadEntities("1");
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
                //noinspection BusyWait
                sleep(1000 / FPSLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        MapLoader.drawMap(g2d);
        EntityLoader.drawEntities(g2d);
        player.draw(g2d);
    }


}
