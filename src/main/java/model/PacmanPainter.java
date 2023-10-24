package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

import javax.imageio.ImageIO;

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
	public static final int WIDTH = 960; //peut être modifier dynamiquement
	public static final int HEIGHT = 540;

	public static final int SCALEWIDTH = WIDTH / 16;
	public static final int SCALEHEIGHT = HEIGHT / 9;

	private TextManager scoreDisplay;

	private Frame frame;

	private int frameIndex;
	private int frameIndexMonster;

	private int animationSpeed = 100;

	/**
	 * appelle constructeur parent
	 *
	 * le jeutest a afficher
	 */
	public PacmanPainter() {
		// Initialise TextManager avec un affichage par défaut de score
		scoreDisplay = new TextManager("", new Font("Arial", Font.PLAIN, 16), Color.WHITE);
		this.frame = new Frame("Image Drawing Example");
		this.frameIndex = 0;
		this.frameIndexMonster = 0;
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
		for (int x = 0; x < width; x += SCALEWIDTH) {
			g2d.drawLine(x, 0, x, height);
		}
		// Dessinez les lignes horizontales de la grille
		for (int y = 0; y < height; y += SCALEHEIGHT) {
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
			g.drawImage(imgC, PacmanGame.plateauDeJeu.getXcase(i)*SCALEWIDTH, PacmanGame.plateauDeJeu.getYcase(i)*SCALEHEIGHT , this.frame);
		}


		//Dessiner le personnage avec textures et animations
		BufferedImage[] pacmanAnimation = new BufferedImage[17];
		BufferedImage[] monsterAnimation = new BufferedImage[13];
		try {
			String side = "";
			switch (PacmanGame.sidePacman){
				case (4) : side = "D"; break;
				case (2) : side = "A"; break;
				case (6) : side = "B"; break;
				case (8) : side = "C"; break;
				case (0) : side = "E";  break;
				default: break;
			}
			for(int i = 0; i < 17; i++) {
				if (PacmanGame.sidePacman == 0) {
					if (i < 10) pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_winA_000" + i + ".png"));
					else pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_winA_00" + i + ".png"));
				} else {
					if (i < 10) pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_walk" + side + "_000" + i + ".png"));
					else pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_walk" + side + "_00" + i + ".png"));
				}
			}
			for(int i = 0; i< 13; i++){
				if (i < 10) monsterAnimation[i] = ImageIO.read(new File("ressources/enemy_walkA_000" + i + ".png"));
				else monsterAnimation[i] = ImageIO.read(new File("ressources/enemy_walkA_00" + i + ".png"));
			}
		}catch (IOException e){
			e.printStackTrace();
		}

		this.frameIndex = (this.frameIndex + 1) % 17;
		Image img = pacmanAnimation[frameIndex];
		g.drawImage(img, PacmanGame.posPacmanX,PacmanGame.posPacmanY,SCALEWIDTH, SCALEHEIGHT, this.frame);


		//Dessiner les monstres
		this.frameIndexMonster = (this.frameIndexMonster + 1) % 13;
		for (Iterator<EntiteeMonstre> it = PacmanGame.plateauDeJeu.monstreIterator(); it.hasNext(); ) {
			EntiteeMonstre monstre = it.next();
			Image imgM = monsterAnimation[frameIndexMonster];
			g.drawImage(imgM, monstre.positionX, monstre.positionY, SCALEWIDTH, SCALEHEIGHT, this.frame);
		}
		/*
		for (Iterator<EntiteeMonstre> it = PacmanGame.plateauDeJeu.monstreIterator(); it.hasNext(); ) {
			EntiteeMonstre monstre = it.next();
			Image imgM = Toolkit.getDefaultToolkit().getImage("ressources/enemy_walkA_0000.png");
			g.drawImage(imgM, monstre.positionX,monstre.positionY,SCALEWIDTH, SCALEHEIGHT, this.frame);
		 */

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