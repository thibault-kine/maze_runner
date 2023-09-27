package maze_runner.interfaces;

import java.util.Random;

import maze_runner.classes.Maze;

public interface MazeGenerator {

    final int MIN_WIDTH = 5;
    final int MIN_HEIGHT = 5;

    final String[] coordStrings = { "N", "E", "S", "W" };

    final Random random = new Random();

    Maze Generate(int w, int h);
}
