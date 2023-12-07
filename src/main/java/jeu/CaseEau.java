package jeu;

import java.awt.*;

public class CaseEau extends Case{

    public CaseEau(){
        super();
        this.setBlocking(true);
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public void event(Joueur player) {
        if (player.canSwim() && this.isBlocking()){
            this.setBlocking(false);
        }
        System.out.println("canSwim : "+player.canSwim());
    }
}
