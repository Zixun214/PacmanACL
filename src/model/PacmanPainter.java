package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import engine.GamePainter;
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

		//Dessiner le personnage
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.blue);
		crayon.fillOval(PacmanGame.posPacmanX,PacmanGame.posPacmanY,10,10);

		//Dessiner les monstres
		for (Iterator<EntiteeMonstre> it = PacmanGame.plateauDeJeu.monstreIterator(); it.hasNext(); ) {
			EntiteeMonstre monstre = it.next();
			Graphics2D g2d = (Graphics2D) im.getGraphics();
			g2d.setColor(Color.red);
			g2d.fill(new Ellipse2D.Double(monstre.positionX, monstre.positionY, 10, 10));
		}

		//Dessiner le trésor
		Graphics2D g2d = (Graphics2D) im.getGraphics();
		g2d.setColor(Color.yellow);
		g2d.fill(new Ellipse2D.Double(PacmanGame.plateauDeJeu.getCaseTresorPositionX(), PacmanGame.plateauDeJeu.getCaseTresorPositionY(), 10, 10));

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
