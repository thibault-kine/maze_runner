package classes;

public class Cell {

    // Walls on the North, South, East and West sides
    private boolean N, S, E, W;

    public Cell(boolean n, boolean s, boolean e, boolean w) {
        this.N = n;
        this.S = s;
        this.E = e;
        this.W = w;
    }
}