package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import engine.GamePainter;
import engine.TextManager;
import jeu.Case;
import jeu.CaseMur;
import jeu.EntiteeMonstre;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 *
 */
public class PacmanPainter implements GamePainter {

	/**
	 * la taille des cases
	 */
	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;

	private TextManager scoreDisplay;

	private Frame frame;

	/**
	 * appelle constructeur parent
	 *
	 * le jeutest a afficher
	 */
	public PacmanPainter() {
		// Initialise TextManager avec un affichage par défaut de score
		scoreDisplay = new TextManager("", new Font("Arial", Font.PLAIN, 16), Color.WHITE);
		this.frame = new Frame("Image Drawing Example");
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {

		//Afficher les grilles
		int width = im.getWidth();
		int height = im.getHeight();
		// Créez un Graphics2D à partir de l'image
		Graphics2D g2d = (Graphics2D) im.getGraphics();
		// Création d'un Graphics
		Graphics g = im.getGraphics();
		// Définissez la couleur de la grille (par exemple, noir)
		g2d.setColor(Color.BLACK);
		// Définissez l'épaisseur de la ligne de la grille
		g2d.setStroke(new BasicStroke(1)); // Épaisseur de 1 pixel
		// Dessinez les lignes verticales de la grille
		for (int x = 0; x < width; x += 60) {
			g2d.drawLine(x, 0, x, height);
		}
		// Dessinez les lignes horizontales de la grille
		for (int y = 0; y < height; y += 60) {
			g2d.drawLine(0, y, width, y);
		}

		//Dessiner les cases
		//wallBreakable_small.png

		ArrayList<Case> cases = PacmanGame.plateauDeJeu.getCases();
		for(int i = 0; i < cases.size(); i++){
			Image imgC = null;
			Case casePlateau = cases.get(i);
			if(casePlateau.getColor() == Color.RED) {
				if( ((CaseMur)casePlateau).estMurDeCote() )
					imgC = Toolkit.getDefaultToolkit().getImage("ressources/wallStone_small.png");
				else
					imgC = Toolkit.getDefaultToolkit().getImage("ressources/wallStone_fence.png");
			}
			if(casePlateau.getColor() == Color.YELLOW){
				imgC = Toolkit.getDefaultToolkit().getImage("ressources/barrelBomb0013.png");
			}
			if(casePlateau.getColor() == Color.GREEN){
				imgC = Toolkit.getDefaultToolkit().getImage("ressources/groundEarth_checkered.png");
			}
			//g2d = (Graphics2D) im.getGraphics();
			//g2d.setColor(casePlateau.getColor());
			g.drawImage(imgC, PacmanGame.plateauDeJeu.getXcase(i)*60, PacmanGame.plateauDeJeu.getYcase(i)*60 , this.frame);
			//g2d.fillRect(PacmanGame.plateauDeJeu.getXcase(i)*60, PacmanGame.plateauDeJeu.getYcase(i)*60, 60, 60); //TODO: 60 est la taille d'une case, il faudrait la mettre en constante ?
		}


		//Dessiner le personnage avec texture

		Image img = null;
		if(PacmanGame.sidePacman == 4) img = Toolkit.getDefaultToolkit().getImage("ressources/hero_walkD_0000.png");
		if(PacmanGame.sidePacman == 6) img = Toolkit.getDefaultToolkit().getImage("ressources/hero_walkB_0000.png");
		if(PacmanGame.sidePacman == 8) img = Toolkit.getDefaultToolkit().getImage("ressources/hero_walkC_0000.png");
		if(PacmanGame.sidePacman == 2) img =Toolkit.getDefaultToolkit().getImage("ressources/hero_walkA_0000.png");
		g.drawImage(img, PacmanGame.posPacmanX,PacmanGame.posPacmanY, this.frame);


		//Dessiner les monstres
		for (Iterator<EntiteeMonstre> it = PacmanGame.plateauDeJeu.monstreIterator(); it.hasNext(); ) {
			EntiteeMonstre monstre = it.next();
			Image imgM = Toolkit.getDefaultToolkit().getImage("ressources/enemy_walkA_0000.png");
			g.drawImage(imgM, monstre.positionX,monstre.positionY, this.frame);
		}

		//Afficher du texte
		// Màj et dessine le score
		scoreDisplay.setText("Score: " + "test");
		scoreDisplay.drawText(g2d, 10, 20);



	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}