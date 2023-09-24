package jeu;

public abstract class Case {
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
