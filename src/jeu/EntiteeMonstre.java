package jeu;

import model.PacmanPainter;

import java.util.Random;

public class EntiteeMonstre extends EntiteePNJ {
    public static final Random random = new Random();
    public int positionX;
    public int positionY;

    public EntiteeMonstre() {
        this.positionX = random.nextInt(PacmanPainter.WIDTH);
        this.positionY = random.nextInt(PacmanPainter.HEIGHT);
    }
}
