package jeu;

import java.util.ArrayList;
import java.util.Iterator;

public class PlateauDeJeu {

    private final int largeur = 20;
    private final int hauteur = 20;
    private final int nombreDeMonstre = 10;
    private ArrayList<Case> cases;
    private ArrayList<Entitee> entitees;

    private ArrayList<EntiteeMonstre> entiteeMonstres;


    public PlateauDeJeu() {
            this.cases = new ArrayList<>(largeur * hauteur); //Modélisation du plateau de jeu
            this.entitees = new ArrayList<>();
            this.entiteeMonstres = new ArrayList<>();
            genererPlateau();
    }

    /**
     * Initialise la position des monstres
     */
    public void initialiserMonstre(){
        for(int i = 0; i< nombreDeMonstre; i++){
            this.entiteeMonstres.add(new EntiteeMonstre());
        }
    }

    /**
     * Génère les monstres du jeu
     */
    public void genererMonstre(){
        initialiserMonstre();
    }

    /***
     * Génère le plateau de jeu avec les cases (trésor, mur, etc.)
     */
    public void genererPlateau() {
        genererMonstre();
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

    /**
     * Iterateur sur la liste des monstres
     * @return un itérateur de la liste des monstres
     */
    public Iterator<EntiteeMonstre> monstreIterator() {
        return this.entiteeMonstres.iterator();
    }

}
