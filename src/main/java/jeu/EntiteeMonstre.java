package jeu;

import model.PacmanGame;
public class EntiteeMonstre extends Entitee {

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
}