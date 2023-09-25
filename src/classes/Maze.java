package classes;

import java.util.ArrayList;
import java.util.List;

import classes.Cell;

public class Maze {
    
    private int width, height;
    private List<List<Cell>> cells;

    public Maze(int w, int h) {
        this.width = w;
        this.height = h;

        this.cells = new ArrayList<>();

        // id that will define each cell
        int _id = 0;
        // horizontal
        for(int x = 0; x < width; x++) {
            List<Cell> row = new ArrayList<>();

            // vertical
            for(int y = 0; y < height; y++) {
                // creates a new cell with all walls closed
                row.add(new Cell(_id, false, false, false, false));
                _id++; // increments the id
            }

            // add the column to the row
            cells.add(row);
        }
    }

    public String ToString() {

        String result = "";
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                result += cells.get(x).get(y).ToString();
            }
            result += "\n";
        }

        return result;
    }
}
