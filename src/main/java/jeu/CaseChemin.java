package jeu;

import java.awt.*;

public class CaseChemin extends Case{

    public CaseChemin(){
        super();
    }
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }
}