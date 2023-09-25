package interfaces;

import classes.Maze;

public interface MazeGenerator {

    final int MIN_WIDTH = 5;
    final int MIN_HEIGHT = 5;

    Maze Generate(int w, int h);
}
