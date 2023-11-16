package model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import engine.Cmd;
import engine.Game;
import jeu.EntiteeMonstre;
import jeu.FireBomb;
import jeu.PlateauDeJeu;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 *
 */
public class PacmanGame implements Game {
	/**
	 * position initiale de pacman
	 */
	public static final int cercleDiametre = 60;

	public static int sidePacman = 2; //2 = base, 4 = gauche, 6 = droite, 8 = haut

	public static int isHit = 0;

	protected static final int iniPosX = 90 - cercleDiametre / 2;
	protected static final int iniPosY = 90 - cercleDiametre / 2;

	public static int posPacmanX;
	public static int posPacmanY;

	public static final int pas = 10;
	/**
	 * le plateau de jeu
	 */
	public static PlateauDeJeu plateauDeJeu;


	public static int lastButtonPressed = 0;

	public static int score = 0;


	/**
	 * constructeur avec fichier source pour le help
	 */
	public PacmanGame(String source) {
		this.plateauDeJeu = new PlateauDeJeu();
		initialisation();
		BufferedReader helpReader;
		try {
			helpReader = new BufferedReader(new FileReader(source));
			String ligne;
			while ((ligne = helpReader.readLine()) != null) {
				System.out.println(ligne);
			}
			helpReader.close();
		} catch (IOException e) {
			System.out.println("Help not available!");
			//e.printStackTrace();
		}
	}

	public void initialisation() {
		this.posPacmanX = iniPosX;
		this.posPacmanY = iniPosY;

	}

	/**
	 * faire evoluer le jeu suite a une commande
	 *
	 * @param commande
	 */
	@Override
	public void evolve(Cmd commande) {
		if (commande == Cmd.IDLE || commande == null) {
			PacmanGame.sidePacman = 0;
			return;
		}
		//System.out.println("Execute " + commande);
		switch (commande) {
			//2 conditions pour déterminer le mur
			//1er pour savoir le centre de case (Si pas de condition 2, il y des bugs)
			//2ème pour assurer que la peronnage marche au centre de case(soit x, soit y)
			case UP:
				if (PacmanGame.plateauDeJeu.getCase(PacmanGame.posPacmanX / 60, (PacmanGame.posPacmanY - pas) / 60).getColor() != Color.RED && PacmanGame.posPacmanX % 60 == 0) {
					if (PacmanGame.posPacmanY > PacmanPainter.SCALEHEIGHT) PacmanGame.posPacmanY -= pas;
				}
				PacmanGame.sidePacman = 8;
				PacmanGame.lastButtonPressed = 8;
				break;
			case DOWN:
				if (PacmanGame.plateauDeJeu.getCase(PacmanGame.posPacmanX / 60, (PacmanGame.posPacmanY + 60) / 60).getColor() != Color.RED && PacmanGame.posPacmanX % 60 == 0) {
					if (PacmanGame.posPacmanY + PacmanPainter.SCALEHEIGHT < PacmanPainter.HEIGHT - PacmanPainter.SCALEHEIGHT)
						PacmanGame.posPacmanY += pas;
				}
				PacmanGame.sidePacman = 2;
				PacmanGame.lastButtonPressed = 2;
				break;
			case LEFT:
				if (PacmanGame.plateauDeJeu.getCase((PacmanGame.posPacmanX - pas) / 60, (PacmanGame.posPacmanY) / 60).getColor() != Color.RED && PacmanGame.posPacmanY % 60 == 0) {
					if (PacmanGame.posPacmanX > PacmanPainter.SCALEWIDTH) PacmanGame.posPacmanX -= pas;
				}
				PacmanGame.sidePacman = 4;
				PacmanGame.lastButtonPressed = 4;
				break;
			case RIGHT:
				if (PacmanGame.plateauDeJeu.getCase((PacmanGame.posPacmanX + 60) / 60, (PacmanGame.posPacmanY) / 60).getColor() != Color.RED && PacmanGame.posPacmanY % 60 == 0) {
					if (PacmanGame.posPacmanX + PacmanPainter.SCALEWIDTH < PacmanPainter.WIDTH - PacmanPainter.SCALEWIDTH)
						PacmanGame.posPacmanX += pas;
				}
				PacmanGame.sidePacman = 6;
				PacmanGame.lastButtonPressed = 6;
				break;
			case SPACE:
				fire();
				collisionBombeMonstre();
				break;
		}
		collisionJoueurMonstre();
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		// le jeu n'est jamais fini
		return false;
	}

	/**
	 * Detecte la collision entre un monstre et un joueur
	 */
	public void collisionJoueurMonstre() {
		int cercleDiametreReel = cercleDiametre / 2;
		for (Iterator<EntiteeMonstre> it = PacmanGame.plateauDeJeu.monstreIterator(); it.hasNext(); ) {
			EntiteeMonstre monstre = it.next();
			if ((monstre.positionX + cercleDiametreReel >= posPacmanX) && (monstre.positionX - cercleDiametreReel <= posPacmanX)
					&& ((monstre.positionY + cercleDiametreReel >= posPacmanY) && (monstre.positionY - cercleDiametreReel <= posPacmanY))) {
				PacmanGame.isHit = 1;
			}
		}
	}

	/**
	 * Detecte collision bombe et monstre
	 * Fait disparaître le monstre si collision
	 */
	public void collisionBombeMonstre(){
		int cercleDiametreReel = cercleDiametre / 2;
		int Xbombe = PacmanGame.plateauDeJeu.solofireBomb.positionX; int Ybombe = PacmanGame.plateauDeJeu.solofireBomb.positionY;
		for (Iterator<EntiteeMonstre> it = PacmanGame.plateauDeJeu.monstreIterator(); it.hasNext(); ) {
			EntiteeMonstre monstre = it.next();
			if ((monstre.positionX + cercleDiametreReel >= Xbombe) && (monstre.positionX - cercleDiametreReel <= Xbombe)
					&& ((monstre.positionY + cercleDiametreReel >= Ybombe) && (monstre.positionY - cercleDiametreReel <= Ybombe))) {
					//TODO remove definitely mobs without using translation
					monstre.positionX = -90;monstre.positionY=-90;
					PacmanGame.score += 10;
			}
		}
	}

	public void fire(){
		switch (lastButtonPressed){
			case 0:
				return;
			case 2:
				PacmanGame.plateauDeJeu.solofireBomb = new FireBomb(PacmanGame.posPacmanX, PacmanGame.posPacmanY + 60);
				break;
			case 4:
				PacmanGame.plateauDeJeu.solofireBomb = new FireBomb(PacmanGame.posPacmanX -60 , PacmanGame.posPacmanY);
				break;
			case 8:
				PacmanGame.plateauDeJeu.solofireBomb = new FireBomb(PacmanGame.posPacmanX, PacmanGame.posPacmanY - 60);
				break;
			case 6:
				PacmanGame.plateauDeJeu.solofireBomb = new FireBomb(PacmanGame.posPacmanX + 60, PacmanGame.posPacmanY);
				break;
		}
	}
}