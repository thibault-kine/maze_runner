package maze_runner.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        setStart();
        setExit();
    }

    private void setStart() {
        List<Cell> cellsX = cells.get(0);
        List<Cell> cellsY = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            cellsY.add(cells.get(i).get(0));
        }
        
        Random rand = new Random();
        int randInt = rand.nextInt(width);
        if(rand.nextBoolean()) {
            cellsX.get(randInt).openWall("N");
        }
        else {
            cellsY.get(randInt).openWall("W");
        }
    }

    private void setExit() {
        List<Cell> cellsX = cells.get(height - 1);
        List<Cell> cellsY = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            cellsY.add(cellsX.get(width - 1));
        }

        Random rand = new Random();
        int randInt = rand.nextInt(width);
        if(rand.nextBoolean()) {
            cellsX.get(randInt).openWall("S");
        }
        else {
            cellsY.get(randInt).openWall("E");
        }
    }

    /**
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
    
    /**
     * Retourne la taille du labyrinthe, soit le nombre de cellules
     */
    public int size() {
        return width * height;
    }

    public boolean isFilledWithN(int n) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Cell c = getCell(x, y);
                if(c.getID() != n) return false;
            }
        }

        return true;
    }

    public int numberOfN(int n) {
        int counter = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(getCell(x, y).getID() == n) counter++;
            }
        }

        return counter;
    }

    public Cell getCellByID(int id) {
        Cell c = null;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                c = getCell(x, y);
                if(c.getID() == id) return c;
            }
        }
        
        return null;
    }

    public ArrayList<Cell> getCellsByID(int id) {
        ArrayList<Cell> cells = new ArrayList<>();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Cell c = getCell(x, y);
                if(c.getID() == id) cells.add(c);;
            }
        }

        return cells;
    }

    public String toString() {

        String[] identifiers = {
            // "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
        String res = "";
        
        // pour chaque cellule
        for(int y = 0; y < height; y++) {
            // pour le mur du haut
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                // s'il y a le mur du nord, l'afficher, sinon mettre un vide
                res += c.N ? "###" : "#.#";
            }

            res += "\n";

            // pour le mur de gauche et de droite
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                // s'il y a le mur de l'ouest, l'afficher, sinon mettre un vide
                res += c.W ? "#" : ".";
                res += "."; // l'espace au milieu
                // res += " " + identifiers[c.getID()] + " ";
                // s'il y a le mur de l'est, l'afficher, sinon mettre un vide
                res += c.E ? "#" : ".";
            }

            res += "\n";

            // pour le mur du bas
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                // s'il y a le mur du sud, l'afficher, sinon mettre un vide
                res += c.S ? "###" : "#.#";
            }
            
            res += "\n";
        }

        return res;
    }
}
