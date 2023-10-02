package maze_runner.misc;

public class MazeGenerationException extends Exception {
    
    public MazeGenerationException() {
        super("Erreur innatendue lors de la génération du labyrinthe. Veuillez réessayer.\n");
    }
}
