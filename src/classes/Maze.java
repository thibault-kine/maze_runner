package classes;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    private int width, height;
    private List<List<Cell>> cells;

    public Maze(int w, int h) {
        this.width = w;
        this.height = h;

        this.cells = new ArrayList<>();

        // horizontal
        for (int x = 0; x < width; x++) {
            List<Cell> row = new ArrayList<>();

            // vertical
            for (int y = 0; y < height; y++) {
                // creates a new cell with all walls closed
                row.add(new Cell(true, true, true, true));
            }

            // add the column to the row
            cells.add(row);
        }
    }

    public String ToString() {

        String res = "";
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(x).get(y);
                res += c.N ? "╔═╗" : "╔ ╗";
            }
            res += "\n";
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(x).get(y);
                res += c.W ? "║" : " ";
                res += " ";
                res += c.E ? "║" : " ";
            }
            res += "\n";
            for(int x = 0; x < width; x++) {
                Cell c = cells.get(x).get(y);
                res += c.S ? "╚═╝" : "╚ ╝";
            }
            res += "\n";
        }

        // // cells
        // for (int y = 0; y < height; y++) {
        //     sb.append("|");
        //     for (int x = 0; x < width; x++) {
        //         Cell cell = cells.get(x).get(y);
        //         sb.append(cell.N ? "   " : "---");
        //         sb.append("|");
        //     }
        //     sb.append("\n");
        //     sb.append("+");
        //     for (int x = 0; x < width; x++) {
        //         Cell cell = cells.get(x).get(y);
        //         sb.append(cell.W ? "   " : "---");
        //         sb.append("+");
        //     }
        //     sb.append("\n");
        //     for (int x = 0; x < width; x++) {
        //         sb.append("+---");
        //     }
        //     sb.append("+\n");
        // }


        return res;
    }
}
