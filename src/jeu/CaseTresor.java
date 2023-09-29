package jeu;

import model.PacmanGame;
import model.PacmanPainter;

import java.util.Random;

public class CaseTresor extends Case {

    public int positionX;
    public int positionY;

    public CaseTresor() {
        initialiserCaseTresor();
    }

    public void initialiserCaseTresor() {
        //Position de la case Trésor
        this.positionX = 30 * 29; this.positionY = 30 * (random.nextInt(14) + 2);
        if (this.positionY % 60 == 0) this.positionY += 30; if (this.positionX % 60 == 0) this.positionX += 30;
        this.positionX = this.positionX - PacmanGame.cercleDiametre / 2; this.positionY = this.positionY - PacmanGame.cercleDiametre / 2;
    }
}
