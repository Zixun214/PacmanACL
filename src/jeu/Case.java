package jeu;

import java.util.Random;

public abstract class Case {

    public static final Random random = new Random();
    protected boolean blocking = false; //La case est-elle bloquante ?
    //TODO: Attribut pour la texture ?
    public Case(){}

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean isBlocking) {
        this.blocking = isBlocking;
    }
}
