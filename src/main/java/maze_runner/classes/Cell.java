package maze_runner.classes;

public class Cell {

    private int id = -1;

    // Walls on the North, South, East and West sides
    public boolean N, S, E, W;

    // without identifier
    public Cell(boolean n, boolean s, boolean e, boolean w) {
        this.N = n;
        this.S = s;
        this.E = e;
        this.W = w;
    }

    // with identifier
    public Cell(int id, boolean n, boolean s, boolean e, boolean w) {
        this.id = Math.abs(id); // to make sure that id is positive
        
        this.N = n;
        this.S = s;
        this.E = e;
        this.W = w;
    }

    public int getID() {
        return id;
    }

    public String toString() {
        String cellStr = "";

        // North wall
        if(N) {
            cellStr += "###";
        }
        else {
            cellStr += "# #";
        }

        // West wall
        if(W) {
            cellStr += "#";
        }
        else {
            cellStr += " ";
        }

        // Gap in the middle
        // If the id is not of the base value, put it in, else, put the gap
        cellStr += (id != -1) ? id : " ";

        // East wall
        if(E) {
            cellStr += "#";
        }
        else {
            cellStr += " "; 
        }

        // South wall
        if (S) {
            cellStr += "###";
        }
        else {
            cellStr += "# #";
        }

        return cellStr;
    }
}