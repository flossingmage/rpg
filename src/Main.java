import game.GamePanel;

import javax.swing.*;

void main() {

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