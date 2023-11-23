import jeu.Case;
import jeu.PlateauDeJeu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlateauDeJeuTest {

    PlateauDeJeu plateauDeJeu;

    @BeforeEach
    void setUp() {
        plateauDeJeu = new PlateauDeJeu(1000); //1000 pour une seed constante
        /*
        Labyrinthe généré:
        XXXXXXXXXXXXXXXX
        X X       X   XX
        X XXX XXX XXX XX
        X     X X     XX
        XXXXXXX XXXXX XX
        X         X   XX
        X XXXXXXX X XXXX
        X       X     XX
        XXXXXXXXXXXXXXXX
         */
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isMurDessous() {
        ArrayList<Case> cases = plateauDeJeu.getCases();
        int x,y;

        //Tu peux modifier la fonction pour tester un cas particulier (le labyrinthe est "affiché" dans le setUp)

        for (int i = 0; i < cases.size(); i++) {
            x = plateauDeJeu.getXcase(i);
            y = plateauDeJeu.getYcase(i);

            if (y == plateauDeJeu.getHauteur()-1){ //dernière ligne
                assertFalse(plateauDeJeu.isMurDessous(x,y));
            } else {
                if (plateauDeJeu.getCase(x,y+1).isBlocking()){
                    assertTrue(plateauDeJeu.isMurDessous(x,y));
                } else {
                    assertFalse(plateauDeJeu.isMurDessous(x,y));
                }
            }
        }
    }

    @Test
    void getXcase() {
        ArrayList<Case> cases = plateauDeJeu.getCases();

        int cptX = 0;

        for (int i = 0; i < cases.size(); i++) {
            assertSame(plateauDeJeu.getXcase(i), cptX);

            if (cptX == 15){
                cptX = 0;
            } else {
                cptX++;
            }
        }
    }

    @Test
    void getYcase() {
        ArrayList<Case> cases = plateauDeJeu.getCases();

        int cpt = 0;
        int cptY = 0;

        for (int i = 0; i < cases.size(); i++) {
            assertSame(plateauDeJeu.getYcase(i), cptY);

            if (cpt == 15){
                cpt = 0;
                cptY++;
            } else {
                cpt++;
            }
        }
    }
}