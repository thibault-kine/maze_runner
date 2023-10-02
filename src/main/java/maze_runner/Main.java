package maze_runner;

import maze_runner.classes.Maze;
import maze_runner.classes.generators.*;
import maze_runner.misc.InvalidGenerationRequestException;

public class Main {
    public static void main(String[] args) {
        Maze maze = null;

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        String perfection = args[2];
        String genType = args[3];

        try {
            if (genType.equals("simple")) {
                if (perfection.equals("perfect")) {
                    maze = new SimplePerfectMazeGenerator().Generate(width, height);
                }
            } else {
                throw new InvalidGenerationRequestException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        // maze = new SimpleImperfectMazeGenerator().Generate(5, 5);
        // maze = new SimplePerfectMazeGenerator().Generate(50, 50);

        if(maze != null)
            System.out.println(maze.toString());
        else
            System.out.println("Maze generation error");
        }
}
