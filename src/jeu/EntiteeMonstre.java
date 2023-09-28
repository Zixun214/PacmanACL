package jeu;

import model.PacmanPainter;

import java.util.Random;

public class EntiteeMonstre extends EntiteePNJ {
    public int positionX;
    public int positionY;

    public EntiteeMonstre() {
        this.positionX = random.nextInt(PacmanPainter.WIDTH);
        this.positionY = random.nextInt(PacmanPainter.HEIGHT);
    }
}
