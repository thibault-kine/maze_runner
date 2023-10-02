package maze_runner.classes;

public class Cell {

    // -1 = id non assigné
    private int id = -1;

    // Murs des côtés Nord, Sud, Est et Ouest
    public boolean N, S, E, W;
    
    // Labyrinthe parent
    private Maze parentMaze;

    // coordonnées relatives au labyrinthe
    public Tuple position;

    // dans le cadre d'un algorithme de prim
    public boolean visited = false;

    // sans id (déprécié)
    public Cell(boolean n, boolean s, boolean e, boolean w) {
        this.N = n;
        this.S = s;
        this.E = e;
        this.W = w;
    }

    // avec id (à utiliser)
    public Cell(int id, boolean n, boolean s, boolean e, boolean w) {
        this.id = Math.abs(id); // pour être sur que l'id est positif
        
        this.N = n;
        this.S = s;
        this.E = e;
        this.W = w;
    }

    public void setParentMaze(Maze parent) {
        this.parentMaze = parent;
    }

    /**
     * Retourne la cellule voisine en fonction du mur choisi
     * (ex: de l'autre côté du mur Est, on va chercher la cellule à droite)
     * @param wallString - le mur
     */
    public Cell getNeighbour(String wallString, int w, int h) {
        switch(wallString) {
            case "N":
                if(position.y - 1 >= 0) return parentMaze.getCell(position.x, position.y - 1);
            
            case "S":
                if(position.y + 1 < h) return parentMaze.getCell(position.x, position.y + 1);

            case "E":
                if(position.x + 1 < w) return parentMaze.getCell(position.x + 1, position.y);

            case "W":
                if(position.x - 1 >= 0) return parentMaze.getCell(position.x - 1, position.y);
            
            default:
                return null;
        }
    }

    /**
     * Retournes les coordonnées telles que "(x ; y)"
     * Sachant que (0 ; 0) = en haut à gauche et (w-1 ; h-1) = en bas à droite
     * @return string
     */
    public String coordinates() {
        return String.format("(x = %d ; y = %d)", position.x, position.y);
    }

    /**
     * Ouvre le mur choisi
     */
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

    public boolean isWallOpen(String wall) {
        switch(wall) {
            case "N":
                return !N;
            case "S":
                return !S;
            case "E":
                return !E;
            case "W":
                return !W;
            default:
                return false;
        }
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String toString() {
        return String.format(
            "Cell #%d:\n \t%s\n \tN: %s, S: %s, E: %s, W: %s\n",
            id,
            coordinates(),
            N, S, E, W
        );
    }
}