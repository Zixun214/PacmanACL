package jeu;

import model.PacmanGame;

import java.awt.*;

public class CaseEau extends Case{

    public CaseEau(){
        super();
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public void event(Joueur player) {
        int midY = PacmanGame.posPacmanY - PacmanGame.posPacmanY%60 + 30;
        int midX = PacmanGame.posPacmanX - PacmanGame.posPacmanX%60 + 30;
        if (!player.canSwim()){
            if (PacmanGame.posPacmanX%60 != 0){
                if (PacmanGame.posPacmanX <= midX){
                    PacmanGame.teleportation(midX-30,PacmanGame.posPacmanY);
                } else {
                    PacmanGame.teleportation(midX+30,PacmanGame.posPacmanY);
                }
            } else {
                if (PacmanGame.posPacmanY <= midY){
                    PacmanGame.teleportation(PacmanGame.posPacmanX,midY-30);
                } else {
                    PacmanGame.teleportation(PacmanGame.posPacmanX,midY+30);
                }
            }
        }
    }
}
