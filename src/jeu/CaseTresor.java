package jeu;

import model.PacmanGame;
import model.PacmanPainter;

import java.awt.*;
import java.util.Random;

public class CaseTresor extends Case {

    public CaseTresor() {
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

}
