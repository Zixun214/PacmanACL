package jeu;

import java.awt.*;

public class CaseNatation extends Case{

    private long lastTime = 0;

    public CaseNatation(){
        super();
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public void event(Joueur player) {
        if (!player.canSwim()) {
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastTime;
            if (deltaTime < 300) {
                player.diminuerTempsRestant(deltaTime);
            }
            lastTime = currentTime;
        }
    }
}
