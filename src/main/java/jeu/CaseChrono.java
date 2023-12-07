package jeu;

import model.PacmanGame;

import java.awt.*;

public class CaseChrono extends Case{

    public Color color;
    public boolean eventOn;

    public CaseChrono(){
        super();
        this.color = Color.ORANGE;
        this.eventOn = true;
    }
    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void event(Joueur player){
        if(this.eventOn == true) {
            PacmanGame.DURATION += 30;
            removeCaseFromMap();
            this.eventOn = false;
        }
    }

    public void removeCaseFromMap(){
        this.color = Color.GREEN;
    }
}
