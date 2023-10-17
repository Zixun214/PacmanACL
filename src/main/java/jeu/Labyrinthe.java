package jeu;

import java.util.ArrayList;
import java.util.Random;

public class Labyrinthe {

    private Vertex[][] labyrinthe;
    private int height;
    private int width;

    public Labyrinthe(int height, int width){
        this.height = height;
        this.width = width;
        this.labyrinthe = new Vertex[height][width];
        init();
    }

    private void create(Random rand){
        Vertex current, next;
        ArrayList<Vertex> visited = new ArrayList<Vertex>();
        ArrayList<Vertex> finished = new ArrayList<Vertex>();
        visited.add(this.labyrinthe[0][0]);

        while (!visited.isEmpty()){
            current = visited.get(-1);
            current.setVisited(true);
            next = choisirVoisin(current, rand);
            if (next != null){
                ajouterChemin(current, next);
                visited.add(next);
            } else {
                finished.add(current);
                visited.remove(current);
            }
        }
    }

    public void creerLabyrinthe(){
        Random rand = new Random();
        create(rand);
    }

    public void creerLabyrinthe(int seed){
        Random rand = new Random(seed);
        create(rand);
    }

    private void init(){
        for(int i = 0; i < this.height; i++){
            for(int j = 0; j < this.width; j++){
                this.labyrinthe[i][j] = new Vertex(String.valueOf(i) + String.valueOf(j));
                if (i > 0){
                    ajouterVoisins(this.labyrinthe[i][j], this.labyrinthe[i-1][j]);
                }
                if (j > 0){
                    ajouterVoisins(this.labyrinthe[i][j], this.labyrinthe[i][j-1]);
                }
            }
        }
    }

    private void ajouterVoisins(Vertex cible, Vertex source){
        cible.addNeighbor(source);
        source.addNeighbor(cible);
    }

    private void ajouterChemin(Vertex cible, Vertex source){
        cible.addPath(source);
        source.addPath(cible);
    }

    private Vertex choisirVoisin(Vertex v, Random rand){
        ArrayList<Vertex> neighbors = v.getNeighbors();
        while (!neighbors.isEmpty()){
            Vertex n = neighbors.get(rand.nextInt(neighbors.size()));
            if (!n.isVisited()){
                return n;
            } else {
                neighbors.remove(n);
            }
        }
        return null;
    }


}
