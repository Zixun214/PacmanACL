package engine;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         un main.java.jeu qui peut evoluer (avant de se terminer) sur un plateau width x
 *         height
 */
public interface Game {

	/**
	 * methode qui contient l'evolution du main.java.jeu en fonction de la commande
	 * 
	 * @param userCmd
	 *            commande utilisateur
	 */
	public void evolve(Cmd userCmd);

	/**
	 * @return true si et seulement si le main.java.jeu est en pause
	 */
	public boolean isPaused();

	/**
	 * @return true si et seulement si le main.java.jeu est fini
	 */
	public boolean isFinished();

}
