package maze_runner.misc;

import maze_runner.interfaces.MazeGenerator;

public class MazeSizeException extends Exception {
    
    public MazeSizeException() {
        super(String.format(
            "Erreur: veuillez entrer une largeur et hauteur valides supérieurs ou égaux à %d et %d\n",
            MazeGenerator.MIN_WIDTH, MazeGenerator.MIN_HEIGHT));
    }
}
