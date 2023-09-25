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

        int id = 0;

        // vertical
        for (int y = 0; y < height; y++) {
            List<Cell> col = new ArrayList<>();

            // horizontal
            for (int x = 0; x < width; x++) {
                // creates a new cell with all walls closed
                Cell newCell = new Cell(id, true, true, true, true);
                newCell.x = x;
                newCell.y = y;
                newCell.setParentMaze(this);
                col.add(newCell);
                id++;
            }
            // add the column to the row
            cells.add(col);
        }
    }

    /*
     * Returns every cell in a bi-dimensional list
     */
    public List<List<Cell>> getCells() {
        return cells;
    }

    /**
     * Returns a single cell
     * @param x - the horizontal coordinate
     * @param y - the vertical coordinate
     * @return the cell
     */
    public Cell getCell(int x, int y) {
        return cells.get(y).get(x);
    }
    
    public int size() {
        return width * height;
    }

    public String toString() {

        String res = "";
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                res += c.N ? "###" : "#.#";
            }
            res += "\n";
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                res += c.W ? "#" : ".";
                res += ".";
                res += c.E ? "#" : ".";
            }
            res += "\n";
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(y).get(x);
                res += c.S ? "###" : "#.#";
            }
            res += "\n";
        }

        return res;
    }
}
