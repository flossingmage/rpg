import game.GamePanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame screen = new JFrame("window");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel game = new GamePanel();
        screen.add(game);

        screen.pack();
        screen.setResizable(false);
        screen.setVisible(true);

        game.startThreads();
        game.run();
    }
}