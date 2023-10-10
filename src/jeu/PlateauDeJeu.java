package jeu;

import java.util.ArrayList;
import java.util.Iterator;

public class PlateauDeJeu {

    private final int largeur = 16;
    private final int hauteur = 9;
    private final int nombreDeMonstre = 5;
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
    } //supprimer genereMonstre et utililiser initialiserMonstre uniquement ?

    /**
     * Génération aléatoire d'une case trésor
     */
    public void genererCaseTresor(){
        int index = 0;
        while (cases.get(index).isBlocking()){ //tant que la case est bloquante (mur)
            index = (int) (Math.random() * cases.size()-1); //on génère un index aléatoire
        }
        cases.set(index, new CaseTresor()); //remplace une case chemin par une case trésor
    }

    /***
     * Génère le plateau de jeu avec les cases (trésor, mur, etc.)
     */
    public void genererPlateau() { //labyrinthe de test, juste une boite
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (i == 0 || i == hauteur - 1 || j == 0 || j == largeur - 1) {
                    this.cases.add(new CaseMur());
                } else {
                    this.cases.add(new CaseChemin());
                }
            }
        }
        genererMonstre();
        genererCaseTresor();
    }

    /***
     * Retourne la case aux coordonnées x et y
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return La case aux coordonnées x et y
     */
    public Case getCase(int x, int y) {
        return cases.get(x + y * largeur);
    }

    /***
     * Renvoi les cases du plateau de jeu
     * @return un arraylist de Case
     */
    public ArrayList<Case> getCases() {
        return this.cases;
    }

    /***
     * Determine la position x d'une case à partir de son index dans l'arraylist cases
     * @param index l'index de la case dans l'arraylist cases
     * @return la position x de la case
     */
    public int getXcase(int index) {
        return index%this.largeur;
    }

    /***
     * Determine la position y d'une case à partir de son index dans l'arraylist cases
     * @param index l'index de la case dans l'arraylist cases
     * @return la position y de la case
     */
    public int getYcase(int index) {
        return index / this.largeur;
    }

    /**
     * Iterateur sur la liste des monstres
     * @return un itérateur de la liste des monstres
     */
    public Iterator<EntiteeMonstre> monstreIterator() {
        return this.entiteeMonstres.iterator();
    }
}