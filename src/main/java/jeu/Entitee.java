package jeu;

import java.util.Random;

public abstract class Entitee {

    public int positionX;
    public int positionY;

    public static final Random random = new Random();

    public Entitee(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

}
