package model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import engine.Cmd;
import engine.Game;
import engine.GameEngineGraphical;
import jeu.*;
import start.Main;

import javax.swing.*;

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
	public static boolean gameInPause = false;
	public static int score = 0;

	public Timer timer;

	public static int DURATION = 240;

	public static int secondsPassed = 0;

	private Joueur player;


	/**
	 * constructeur avec fichier source pour le help
	 */
	public PacmanGame(String source) {
		this.timer = new Timer();
		this.player = new Joueur(PacmanGame.iniPosX, PacmanGame.iniPosY); //initialise le joueur
		this.plateauDeJeu = new PlateauDeJeu();
		this.plateauDeJeu.setPlayer(player);
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
		if(commande == Cmd.PAUSE) gameInPause = !gameInPause;
		if (commande == null || gameInPause) {
			PacmanGame.sidePacman = 0;
			return;
		}
		if(player.getCooldownHit() > 0) player.setCooldownHit(player.getCooldownHit() - 1);
		//System.out.println("Execute " + commande);
		switch (commande) {
			//2 conditions pour déterminer le mur
			//1er pour savoir le centre de case (Si pas de condition 2, il y des bugs)
			//2ème pour assurer que la peronnage marche au centre de case(soit x, soit y)
			case UP:
				if (!PacmanGame.plateauDeJeu.getCase(PacmanGame.posPacmanX / 60, (PacmanGame.posPacmanY - pas) / 60).isBlocking() && PacmanGame.posPacmanX % 60 == 0) {
					if (PacmanGame.posPacmanY > PacmanPainter.SCALEHEIGHT) PacmanGame.posPacmanY -= pas;
				}
				PacmanGame.sidePacman = 8;
				PacmanGame.lastButtonPressed = 8;
				break;
			case DOWN:
				if (!PacmanGame.plateauDeJeu.getCase(PacmanGame.posPacmanX / 60, (PacmanGame.posPacmanY + 60) / 60).isBlocking() && PacmanGame.posPacmanX % 60 == 0) {
					if (PacmanGame.posPacmanY + PacmanPainter.SCALEHEIGHT < PacmanPainter.HEIGHT - PacmanPainter.SCALEHEIGHT)
						PacmanGame.posPacmanY += pas;
				}
				PacmanGame.sidePacman = 2;
				PacmanGame.lastButtonPressed = 2;
				break;
			case LEFT:
				if (!PacmanGame.plateauDeJeu.getCase((PacmanGame.posPacmanX - pas) / 60, (PacmanGame.posPacmanY) / 60).isBlocking() && PacmanGame.posPacmanY % 60 == 0) {
					if (PacmanGame.posPacmanX > PacmanPainter.SCALEWIDTH) PacmanGame.posPacmanX -= pas;
				}
				PacmanGame.sidePacman = 4;
				PacmanGame.lastButtonPressed = 4;
				break;
			case RIGHT:
				if (!PacmanGame.plateauDeJeu.getCase((PacmanGame.posPacmanX + 60) / 60, (PacmanGame.posPacmanY) / 60).isBlocking() && PacmanGame.posPacmanY % 60 == 0) {
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
		if (isFinished()){
			if(player.getLife() <= 0){
				System.out.println("You died!");
				Main.winTurn=false;
			} else {
				System.out.println("Congrats! You won!");
				PacmanGame.score+=30;
			}

		}
		plateauDeJeu.gameEvent();
	}

	/**
	 * verifier si le jeu est en pause
	 */
	@Override
	public boolean isPaused() {
		return gameInPause;
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		if(player.getLife() <= 0) return true;
		int xCurr = (PacmanGame.posPacmanX - PacmanGame.posPacmanX%10) / 60;
		int yCurr = (PacmanGame.posPacmanY - PacmanGame.posPacmanY%10) / 60;
		Case current = plateauDeJeu .getCase(xCurr, yCurr);
		return current.isTresor();
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
				player.setHit(true);
				takeDamage();
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

	/**
	 * Retire la vie du personnage suite à une collision
	 */
	public void takeDamage() {
		if(player.getCooldownHit() > 0) return;
		else {
			player.setCooldownHit(10);
			player.setLife(player.getLife() - 1);
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

	public static void teleportation(int x, int y){
		PacmanGame.posPacmanX = x;
		PacmanGame.posPacmanY = y;
	}

	public void startTimer() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				if (secondsPassed < DURATION) {
					//System.out.println("Seconds passed: " + secondsPassed);
					if(!gameInPause)
						secondsPassed++;
				} else {
					//System.out.println("Timer expired. Task completed.");
					timer.cancel(); // Stop the timer when the duration is reached
					System.out.println("Out of time !");
					System.exit(0);
				}
			}
		};

		// Schedule the task to run every 1000 milliseconds (1 second)
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

}