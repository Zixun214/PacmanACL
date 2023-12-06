package start;


import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;
import model.PacmanPainter;

import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.*;

/**
 * lancement du moteur avec le main.java.jeu
 */
public class Main {
	public static boolean StartGame=false;
	public static boolean winTurn=true;
	public static void main(String[] args) throws InterruptedException {
		if (args.length > 0 && "swing".equals(args[0])) {
			runGame();
		} else {
			startMenu();
			if(!StartGame) {
				System.out.println("Game close.");
				return;
			}

			//***** new process pour le jeu
			//because need to function on mac.
			try {
				String currentDir = Paths.get("").toAbsolutePath().toString();
				String jarPath = currentDir.endsWith("target") ? "JeuTemplate-1.0-SNAPSHOT.jar" : "target/JeuTemplate-1.0-SNAPSHOT.jar";
				ProcessBuilder pb = new ProcessBuilder("java", "-jar", jarPath, "swing");
				pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
				pb.redirectError(ProcessBuilder.Redirect.INHERIT);
				pb.start();
			} catch (IOException e) {
				e.printStackTrace();
			}//*****
		}
		while (true){
			int option;
			if(winTurn){
				option = JOptionPane.showOptionDialog(
						null,
						"You win！Here is your score : "+PacmanGame.score +"\n Want to continue your challenge？Time will go faster !!!",
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
						"You die! Here is your points: "+PacmanGame.score,
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
