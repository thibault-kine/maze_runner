package maze_runner;

import maze_runner.classes.generators.*;

public class Main {
    public static void main(String[] args) {

        System.out.println(new SimplePerfectMazeGenerator().Generate(5, 5).toString());
    }
}
