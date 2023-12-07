package jeu;

import model.PacmanGame;

import java.awt.*;

public class CaseTP extends Case{

    private int targetX;
    private int targetY;

    private boolean isActivated;

    public CaseTP(int targetX, int targetY) {
        this.targetX = targetX*60;
        this.targetY = targetY*60;
        this.isActivated = true;
    }

    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }

    @Override
    public void event(Joueur player) {
        if (isActivated){
            PacmanGame.teleportation(targetX, targetY);
            isActivated = false;
        }
    }
}
