package model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import engine.GamePainter;
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

	/**
	 * appelle constructeur parent
	 * 
	 * le jeutest a afficher
	 */
	public PacmanPainter() {
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
		ArrayList<Case> cases = PacmanGame.plateauDeJeu.getCases();
		for(int i = 0; i < cases.size(); i++){
			Case casePlateau = cases.get(i);
			g2d = (Graphics2D) im.getGraphics();
			g2d.setColor(casePlateau.getColor());
			g2d.fillRect(PacmanGame.plateauDeJeu.getXcase(i)*60, PacmanGame.plateauDeJeu.getYcase(i)*60, 60, 60); //TODO: 60 est la taille d'une case, il faudrait la mettre en constante ?
		}

		//Dessiner le personnage
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.blue);
		crayon.fillOval(PacmanGame.posPacmanX,PacmanGame.posPacmanY,10,10);

		//Dessiner les monstres
		for (Iterator<EntiteeMonstre> it = PacmanGame.plateauDeJeu.monstreIterator(); it.hasNext(); ) {
			EntiteeMonstre monstre = it.next();
			g2d = (Graphics2D) im.getGraphics();
			g2d.setColor(Color.red);
			g2d.fill(new Ellipse2D.Double(monstre.positionX, monstre.positionY, 10, 10));
		}

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
