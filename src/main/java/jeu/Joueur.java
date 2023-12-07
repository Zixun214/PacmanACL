package jeu;


public class Joueur extends Entitee{

    private boolean swim;
    private long tempsRestant;
    private boolean isHit = false;
    private int life = 6;
    private int cooldownHit = 0;

    public Joueur(int x, int y) {
        super(x, y);
        this.swim = false;
        this.tempsRestant = 3000;
    }

    public boolean canSwim(){
        return swim;
    }

    public void diminuerTempsRestant(long debut){
        if (tempsRestant > 0) {
            tempsRestant -= System.currentTimeMillis() - debut;
        } else {
            swim = true;
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getCooldownHit() {
        return cooldownHit;
    }

    public void setCooldownHit(int cooldownHit) {
        this.cooldownHit = cooldownHit;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
