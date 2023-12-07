package jeu;

import model.PacmanGame;

import java.lang.management.MonitorInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PlateauDeJeu {

    private final int largeur = 16;
    private final int hauteur = 9;
    private final int nombreDeMonstre = 5;
    private ArrayList<Case> cases;

    private int nombreDeCaseChronoPlus = 2;

    public FireBomb solofireBomb;

    private ArrayList<EntiteeMonstre> entiteeMonstres;
    private Joueur player;

    public PlateauDeJeu() {
        this(new Random().nextInt());
    }

    public PlateauDeJeu(int randomSeed) {
        this.cases = new ArrayList<>(largeur * hauteur); //Modélisation du plateau de jeu
        this.entiteeMonstres = new ArrayList<>();
        this.player = new Joueur(0,0);
        this.solofireBomb = new FireBomb(-90,-90); //TODO set solofireBomb inexistant at the beginning
        System.out.println("Seed: " + randomSeed);
        genererPlateau(randomSeed);
    }

    /**
     * Initialise la position des monstres
     */
    public void initialiserMonstre(){
        for(int i = 0; i< nombreDeMonstre; i++){
            this.entiteeMonstres.add(new EntiteeMonstre(this));
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
        int index = choisirCase();
        cases.set(index, new CaseTresor()); //remplace une case chemin par une case trésor
    }

    /**
     * Génération aléatoire d'une case chrono
     */
    public void genererCaseChrono(int nombredecases){
        for(int i = 0; i< nombredecases; i++) {
            int index = choisirCase();
            cases.set(index, new CaseChrono());
        }
    }

    public void genererCaseTP(){
        int caseTP = choisirCase();
        int caseCible = choisirCase();

        while (caseTP == caseCible){
            caseCible = choisirCase();
        }
        cases.set(caseTP, new CaseTP(getXcase(caseCible),getYcase(caseCible))); //remplace une case chemin par une case trésor
    }

    public void genererCaseEau(int nb){
        while (nb > 0){
            int index = choisirCase();
            if (index != 18 && index != 32) {
                cases.set(index, new CaseEau()); //remplace une case chemin par une case Eau
                nb--;
            }
        }
    }

    public void genererCaseNatation(){
        if (cases.get(18).isBlocking()){
            cases.set(33, new CaseNatation());
        } else {
            cases.set(18, new CaseNatation());
        }
    }

    public int choisirCase(){
        int index = 0;
        while (cases.get(index).isSpecial() || index == 17){ //tant que la case est bloquante (mur) ou que c'est la case de départ
            index = (int) (Math.random() * cases.size()-1); //on génère un index aléatoire
        }
        return index;
    }

    /***
     * Génère le plateau de jeu avec les cases (trésor, mur, etc.)
     */
    public void genererPlateau(int randomSeed) { //labyrinthe de test, juste une boite
        Labyrinthe lab = new Labyrinthe(4, 7); //créer un labyrinthe de la taille du plateau de jeu sans les murs extérieurs
        lab.creerLabyrinthe(randomSeed);
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (i == 0 || i == hauteur - 1 || j == 0 || j == largeur - 1) { //mur extérieur
                    if( (j == 0 && i >= 0 && i < hauteur -1) || (j == largeur -1 && i >= 0 && i < hauteur -1) ) this.cases.add(new CaseMur(true));
                    else this.cases.add(new CaseMur());
                } else {
                    try {
                        this.cases.add(lab.getCase(i-1,j-1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        this.cases.add(new CaseMur());
                    }
                }
            }
        }

        //Modification apparence des murs
        for(int i = 0; i < hauteur; i++){
            for(int j = 0; j < largeur; j++ ){
                if(this.getCase(j,i) instanceof CaseMur) {
                    if (isMurDessous(j, i)) ((CaseMur)this.getCase(j,i)).setMurDeCote(true);
                    else ((CaseMur)this.getCase(j,i)).setMurDeCote(false);
                }
            }
        }

        genererMonstre();
        genererCaseNatation();
        genererCaseEau(5);
        genererCaseTP();
        genererCaseChrono(nombreDeCaseChronoPlus);
        genererCaseTresor();
    }

    public void gameEvent(){
        int xCurr = (PacmanGame.posPacmanX - PacmanGame.posPacmanX%10) / 60;
        int yCurr = (PacmanGame.posPacmanY - PacmanGame.posPacmanY%10) / 60;
        // va chercher la case sur laquelle se trouve le joueur
        Case current = this.getCase(xCurr, yCurr);
        current.event(player); //execute l'event de la case
    }

    public boolean isMurDessous(int x, int y){
        try {
            return this.getCase(x,y+1).isBlocking();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    public boolean isMurDessus(int x, int y){
        try {
            return this.getCase(x,y-1).isBlocking();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isMurAGauche(int x, int y){
        try {
            return this.getCase(x-1,y).isBlocking();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isMurADroite(int x, int y){
        try {
            return this.getCase(x+1,y).isBlocking();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
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


    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public Joueur getPlayer() {
        return player;
    }

    public void setPlayer(Joueur player) {
        this.player = player;
    }
}