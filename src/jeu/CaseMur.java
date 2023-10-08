package jeu;

import java.awt.*;

public class CaseMur extends Case {
    public CaseMur() {
            this.blocking = true;
        }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}
