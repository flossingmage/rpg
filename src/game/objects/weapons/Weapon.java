package game.objects.weapons;

import game.objects.SuperObject;

import java.awt.image.BufferedImage;

public class Weapon extends SuperObject {
    int attackDamage;
    int range;
    int attackWidth;
    String attackType;
    BufferedImage img;

    public Weapon(int attackDamage, int range, int attackWidth, String name, String attackType, BufferedImage img){
        super(name);
        this.attackDamage = attackDamage;
        this.range = range;
        this.attackWidth = attackWidth;
        this.attackType = attackType;
        this.img = img;
    }
}
