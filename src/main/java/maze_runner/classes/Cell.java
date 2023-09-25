package maze_runner.classes;

public class Cell {

    private int id = -1;

    // Walls on the North, South, East and West sides
    public boolean N, S, E, W;
    
    private Maze parentMaze;
    public int x, y;

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

    public void setParentMaze(Maze parent) {
        this.parentMaze = parent;
    }

    public Cell getNeighbour(String wallString) {
        switch(wallString) {
            case "N":
                return parentMaze.getCell(x, y - 1);
            
            case "S":
                return parentMaze.getCell(x, y + 1);

            case "E":
                return parentMaze.getCell(x + 1, y);

            case "W":
                return parentMaze.getCell(x - 1, y);
            
            default:
                return null;
        }
    }

    public String coordinates() {
        return String.format("(x = %d ; y = %d)", x, y);
    }

    public void openWall(String wall) {
        // System.out.println("Cell #" + getID() + ": " + wall);
        switch (wall) {
            case "N":
                this.N = false;
                break;
        
            case "S":
                this.S = false;
                break;

            case "E":
                this.E = false;
                break;

            case "W":
                this.W = false;
                break;

            default:
                break;
        }
    }

    public int getID() {
        return id;
    }

    public String toString() {
        String cellStr = "";

        // North wall
        if(N) {
            cellStr += "###\n";
        }
        else {
            cellStr += "#.#\n";
        }

        // West wall
        if(W) {
            cellStr += "#";
        }
        else {
            cellStr += ".";
        }

        // Gap in the middle
        // If the id is not of the base value, put it in, else, put the gap
        cellStr += (id != -1) ? id : ".";

        // East wall
        if(E) {
            cellStr += "#\n";
        }
        else {
            cellStr += ".\n"; 
        }

        // South wall
        if (S) {
            cellStr += "###\n";
        }
        else {
            cellStr += "#.#\n";
        }

        return cellStr;
    }
}