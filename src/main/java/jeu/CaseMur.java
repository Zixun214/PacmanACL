package jeu;

import java.awt.*;

public class CaseMur extends Case {

    private boolean murDeCote;

    public CaseMur() {
        this.blocking = true;
        this.murDeCote = false;
    }

    public CaseMur(boolean murDeCote) {
        this.blocking = true;
        this.murDeCote = murDeCote;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
    public boolean estMurDeCote(){
        return murDeCote;
    }

    public void setMurDeCote(boolean set){
        this.murDeCote =set;
    }
}