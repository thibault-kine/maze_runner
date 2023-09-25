package classes.generators;

import classes.Maze;
import interfaces.MazeGenerator;

public class SimplePerfectMazeGenerator implements MazeGenerator {

    public Maze Generate(int w, int h) {
        return new Maze(w, h);
    }
}
