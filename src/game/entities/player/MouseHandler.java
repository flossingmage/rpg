package game.entities.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    Player player;

    public MouseHandler(Player player) {
        this.player = player;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        player.MouseClicked();
        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println(mouseX + " " + mouseY);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e){
        int mouseX = e.getX();
        int mouseY = e.getY();
        player.setDirection(mouseX,mouseY);
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