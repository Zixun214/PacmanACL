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
import jeu.FireBomb;

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

	private TextManager chronoDisplay;

	private Frame frame;

	private int frameIndex;
	private int frameIndexMonster;

	private int timerCollision = 0;
	private int timerCollisionMAXVALUE = 10; //environ 1 seconde d'animation

	private int fireTimer = 0;
	private int fireTimerMAXVALUE = 20; //envirion 2 seconde d'animation


	/**
	 * appelle constructeur parent
	 *
	 * le jeutest a afficher
	 */
	public PacmanPainter() {
		// Initialise TextManager avec un affichage par défaut de score
		scoreDisplay = new TextManager("", new Font("Arial", Font.PLAIN, 16), Color.WHITE);
		chronoDisplay = new TextManager("", new Font("Arial", Font.PLAIN, 16), Color.WHITE);
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
		BufferedImage[] fireAnimation = new BufferedImage[17];
		try {
			String side = "";
			switch (PacmanGame.lastButtonPressed){
				case (0) :
                case (2) : side = "A"; break;
				case (4) : side = "D"; break;
                case (6) : side = "B"; break;
				case (8) : side = "C"; break;
				default: break;
			}
			for(int i = 0; i < 17; i++) {
				if (PacmanGame.isHit == 0) {
					if (PacmanGame.sidePacman == 0) {
						pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_walk" + side + "_0000.png"));
						//if (i < 10) pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_winA_000" + i + ".png"));
						//else pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_winA_00" + i + ".png"));
					} else {
						if (i < 10) pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_walk" + side + "_000" + i + ".png"));
						else pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_walk" + side + "_00" + i + ".png"));
					}
				} else {
					if (i < 10) pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_hitA_000" + i + ".png"));
					else pacmanAnimation[i] = ImageIO.read(new File("ressources/hero_hitA_00" + i + ".png"));
					if (i == 16) timerCollision++;
					if (timerCollision == timerCollisionMAXVALUE) {
						PacmanGame.isHit = 0;timerCollision = 0;
					}
				}
			}

			for(int i = 0; i< 13; i++){
				if (i < 10) monsterAnimation[i] = ImageIO.read(new File("ressources/enemy02_walkA_000" + i + ".png"));
				else monsterAnimation[i] = ImageIO.read(new File("ressources/enemy02_walkA_00" + i + ".png"));
			}

			for(int i = 1; i < 17; i++){
				fireAnimation[i] = ImageIO.read(new File("ressources/Fire-bomb" + i + ".png"));
				if (i == 16) fireTimer++;
				if (fireTimer == fireTimerMAXVALUE) {
					PacmanGame.plateauDeJeu.solofireBomb.positionX = -90;
					PacmanGame.plateauDeJeu.solofireBomb.positionY = -90;
					fireTimer = 0;
				}
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
			monstre.move();
			monstre.changeDirection();
		}

		//Dessiner les bombes
		Image imgF = fireAnimation[frameIndex];
		FireBomb fire = PacmanGame.plateauDeJeu.solofireBomb;
		g.drawImage(imgF, fire.positionX, fire.positionY, SCALEWIDTH, SCALEHEIGHT, this.frame);



		//Afficher du texte
		// Màj et dessine le score
		scoreDisplay.setText("Score: " +  PacmanGame.score);
		scoreDisplay.drawText(g2d, 10, 20);

		//Afficher chrono
		chronoDisplay.setText("Timer : " + (PacmanGame.DURATION - PacmanGame.secondsPassed));
		chronoDisplay.drawText(g2d, 420, 20);


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