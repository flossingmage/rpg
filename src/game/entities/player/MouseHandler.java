package game.entities.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    Player player;

    public MouseHandler(Player player) {
        this.player = player;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        player.MouseClicked();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
