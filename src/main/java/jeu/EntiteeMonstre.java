package jeu;

import model.PacmanGame;
public class EntiteeMonstre extends Entitee {

    public int direction = random.nextInt(3);

    public EntiteeMonstre(PlateauDeJeu pdj) {
        super(0,0);
        initialiserMonstre(pdj);
    }

    public void initialiserMonstre(PlateauDeJeu pdj){
        while (pdj.getCase(positionX/60, positionY/60).isBlocking()) { //tant que la case est bloquante (mur)
            //Position (X,Y) = au centre d'une case !
            this.positionX = 30 * (random.nextInt(33 - 5) + 2); //entre 2 et 28
            this.positionY = 30 * (random.nextInt(18 - 4) + 2); //entre 2 et 14
            if (this.positionY % 60 == 0) this.positionY += 30;
            if (this.positionX % 60 == 0) this.positionX += 30;
            this.positionX = this.positionX - PacmanGame.cercleDiametre / 2;
            this.positionY = this.positionY - PacmanGame.cercleDiametre / 2;
        }
    }

    public void changeDirection(){
        //Si on touche un mur quelquonque
        //On touche un mur à droite
        int translationX = this.positionX / 60;
        int translationY = this.positionY /60;
        if(direction == 3){
            if (((this.positionX) % 6 == 0)) {
                if (PacmanGame.plateauDeJeu.isMurAGauche(translationX-1, translationY)) {
                    this.direction = 2;
                }
            }
        }
        if(direction == 2){
            if (((this.positionX) % 6 == 0)) {
                if (PacmanGame.plateauDeJeu.isMurADroite(translationX, translationY)) {
                    this.direction = 3;
                }
            }
        }
        if(this.direction == 0) {
            if (((this.positionY + 30) % 60 == 0)) {
                if (PacmanGame.plateauDeJeu.isMurDessus(translationX, translationY + 1)) {
                    this.direction = random.nextInt(1)+1;
                }
            }
        }
        if(this.direction == 1) {
            if (((this.positionY) % 6 == 0)) {
                if (PacmanGame.plateauDeJeu.isMurDessous(translationX, translationY)) {
                    this.direction = 0;
                }
            }
        }

    }

    public void move(){
        switch (this.direction){
            case 0 : //haut
                this.positionY -= 1;
                break;
            case 1 : //bas
                this.positionY += 1;
                break;
            case 2 : //droite
                this.positionX += 1;
                break;
            case 3 : //gauche
                this.positionX -= 1;
                break;
            default: break;
        }
    }
}