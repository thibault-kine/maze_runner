package maze_runner;

import maze_runner.classes.Maze;
import maze_runner.classes.generators.*;

public class Main {
    public static void main(String[] args) {

        Maze maze = new SimplePerfectMazeGenerator().Generate(5, 5);

        System.out.println(maze.toString());
    }
}
