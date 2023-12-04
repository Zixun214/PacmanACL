package start;


import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;
import model.PacmanPainter;

import javax.swing.*;

/**
 * lancement du moteur avec le main.java.jeu
 */
public class Main {
	public static boolean StartGame=false;
	public static boolean winTurn=true;
	public static void main(String[] args) throws InterruptedException {

//********** commenter cette partie s'il y a problème avec lwjgl
		startMenu();
		if(!StartGame)
			return;
//***************
		runGame();
		while (true){
			int option;
			if(winTurn){
				option = JOptionPane.showOptionDialog(
						null,
						"You win！want to continue your challenge？\n Time will go faster !!!",
						"Win",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						new Object[]{"Continue", "Exit"},
						"Continue");
				if (option == JOptionPane.YES_OPTION) {
					runGame();
				} else {
					System.exit(0);
				}
			}else{
				JOptionPane.showMessageDialog(
						null,
						"You die! Here are your points: "+PacmanGame.score,
						"End",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
	}

	public static void startMenu(){
		new StartMenu().run();
	}

	public static void runGame() throws InterruptedException {
		// creation du main.java.jeu particulier et de son afficheur
		PacmanGame game = new PacmanGame("ressources/GameHelper.txt");
		game.startTimer();
		PacmanPainter painter = new PacmanPainter();
		PacmanController controller = new PacmanController();

		// classe qui lance le moteur de main.java.jeu generique
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();//boucle
	}
}
