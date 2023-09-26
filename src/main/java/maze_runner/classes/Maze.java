package maze_runner.classes;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    private int width, height;
    private List<List<Cell>> cells;

    public Maze(int w, int h) {
        this.width = w;
        this.height = h;

        this.cells = new ArrayList<>();

        // id de chaque cellule
        int id = 0;

        // vertical
        for (int y = 0; y < height; y++) {
            List<Cell> col = new ArrayList<>();

            // horizontal
            for (int x = 0; x < width; x++) {
                // créé une nouvelle cellule avec tous les murs fermés
                Cell newCell = new Cell(id, true, true, true, true);
                // assigne les coordonnées
                newCell.x = x;
                newCell.y = y;
                // assigne le labyrinthe parent de cette cellule
                newCell.setParentMaze(this);
                // ajoute la cellule à la colonne
                col.add(newCell);
                id++;
            }
            // ajoute la colonne à la ligne
            cells.add(col);
        }
    }

    /*
     * Retourne chaque cellule dans une liste bi-dimensionelle
     */
    public List<List<Cell>> getCells() {
        return cells;
    }

    /**
     * Retourne une seule cellule
     * @param x - coordonnée horizontale
     * @param y - coordonnée verticale
     * @return la cellule
     */
    public Cell getCell(int x, int y) {
        return cells.get(y).get(x);
    }
    
    /*
     * Retourne la taille du labyrinthe, soit le nombre de cellules
     */
    public int size() {
        return width * height;
    }

    public String toString() {

        String res = "";
        
        // pour chaque cellule
        for(int y = 0; y < height; y++) {
            // pour le mur du haut
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                res += c.N ? "###" : "#.#";
            }

            res += "\n";

            // pour le mur de gauche et de droite
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                res += c.W ? "#" : ".";
                res += "."; // l'espace au milieu
                res += c.E ? "#" : ".";
            }

            res += "\n";

            // pour le mur du bas
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                res += c.S ? "###" : "#.#";
            }
            
            res += "\n";
        }

        return res;
    }
}
