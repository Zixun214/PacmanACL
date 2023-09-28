package jeu;

import model.PacmanPainter;

import java.util.Random;

public class CaseTresor extends Case{

    public int positionX;
    public int positionY;

    public CaseTresor() {
        this.positionX = random.nextInt((PacmanPainter.WIDTH - 100) - (PacmanPainter.WIDTH - 200))  + (PacmanPainter.WIDTH - 200);
        this.positionY = random.nextInt(PacmanPainter.HEIGHT);
    }
}
