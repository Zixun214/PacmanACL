package jeu;

import java.awt.*;

public abstract class Case {

    protected boolean blocking = false; //La case est-elle bloquante ?
    //TODO: Attribut pour la texture ?
    public Case(){}

    public void event(Joueur player){
        return;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public boolean isTresor() {
        return false;
    }

    public boolean isSpecial() {
        return true;
    }

    public abstract Color getColor();

    public void setBlocking(boolean isBlocking) {
        this.blocking = isBlocking;
    }
}