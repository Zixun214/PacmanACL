package jeu;

import java.util.ArrayList;

public class PlateauDeJeu {

    private final int largeur = 20;
    private final int hauteur = 20;
    private ArrayList<Case> cases;
    private ArrayList<Entitee> entitees;

    public PlateauDeJeu() {
            this.cases = new ArrayList<>(largeur * hauteur); //Modélisation du plateau de jeu
            this.entitees = new ArrayList<>();
    }

    /***
     * Génère le plateau de jeu avec les cases (trésor, mur, etc.)
     */
    public void genererPlateau() {
        //TODO: Générer le plateau de jeu
    }

    /***
     * Retourne la case aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return La case aux coordonnées x et y
     */
    public Case getCase(int x, int y) {
        return cases.get(y + x * largeur);
    }


}
