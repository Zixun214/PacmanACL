package start;


import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;
import model.PacmanPainter;

/**
 * lancement du moteur avec le main.java.jeu
 */
public class Main {
	public static boolean StartGame=false;

	public static void main(String[] args) throws InterruptedException {

//********** commenter cette partie s'il y a problème avec lwjgl
		startMenu();
		if(!StartGame)
			return;
//***************
		runGame();
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
