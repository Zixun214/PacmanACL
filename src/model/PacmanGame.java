package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import engine.Cmd;
import engine.Game;
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
	public static final int cercleDiametre = 10;
	protected static final int iniPosX= 90 - cercleDiametre / 2;
	protected static final int iniPosY= 90 - cercleDiametre / 2;

	public static int posPacmanX;
	public static int posPacmanY;

	/**
	 * le plateau de jeu
	 */
	public static PlateauDeJeu plateauDeJeu;

	/**
	 * constructeur avec fichier source pour le help
	 * 
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

	public void initialisation(){
		this.posPacmanX=iniPosX;
		this.posPacmanY=iniPosY;
	}

	/**
	 * faire evoluer le jeu suite a une commande
	 * 
	 * @param commande
	 */
	@Override
	public void evolve(Cmd commande) {
		if(commande == Cmd.IDLE)
			return;
		//System.out.println("Execute " + commande);
		switch (commande){
			case UP :
				if(PacmanGame.posPacmanY - 60 > 60) PacmanGame.posPacmanY-=60;//pour affichage
				break;
			case DOWN:
				if(PacmanGame.posPacmanY + 60 < 480) PacmanGame.posPacmanY+=60;
				break;
			case LEFT:
				if(PacmanGame.posPacmanX - 60 > 60) PacmanGame.posPacmanX-=60;
				break;
			case RIGHT:
				if(PacmanGame.posPacmanX + 60 < 900) PacmanGame.posPacmanX+=60;
				break;
		}
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		// le jeu n'est jamais fini
		return false;
	}

}
