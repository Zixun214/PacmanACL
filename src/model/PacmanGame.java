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
	protected static final int iniPosX=50;
	protected static final int iniPosY=50;

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
				PacmanGame.posPacmanY-=10;//pour affichage
				break;
			case DOWN:
				PacmanGame.posPacmanY+=10;
				break;
			case LEFT:
				PacmanGame.posPacmanX-=10;
				break;
			case RIGHT:
				PacmanGame.posPacmanX+=10;
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
