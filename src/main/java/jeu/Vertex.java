package jeu;

import java.util.ArrayList;

public class Vertex {

    private String id;
    private ArrayList<Vertex> neighbors; //voisins
    private ArrayList<Vertex> path; //chemin vers les vertexs
    private boolean visited;

    public Vertex(String coord){
        this.id = coord;
        this.neighbors = new ArrayList<Vertex>(4);
        this.path = new ArrayList<Vertex>();
        this.visited = false;
    }

    public void addNeighbor(Vertex v){
        this.neighbors.add(v);
    }

    public void addPath(Vertex v){
        this.path.add(v);
    }

    public boolean isVisited(){
        return this.visited;
    }

    public void removeNeighbor(Vertex v){
        this.neighbors.remove(v);
    }

    public ArrayList<Vertex> getNeighbors(){
        return this.neighbors;
    }

    public ArrayList<Vertex> getPath(){
        return this.path;
    }

    public boolean hasPath(Vertex target){
        for (Vertex chemin : path){
            if (chemin.equals(target)){
                return true;
            }
        }
        return false;
    }

    public String getId(){
        return this.id;
    }

    public void setVisited(boolean b){
        this.visited = b;
    }

    public boolean equals(Vertex v) {
        return this.id.equals(v.getId());
    }
}
