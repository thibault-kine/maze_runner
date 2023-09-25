import classes.generators.SimplePerfectMazeGenerator;

public class Main {
    public static void main(String[] args) {
        
        SimplePerfectMazeGenerator mazeGenerator = new SimplePerfectMazeGenerator();
        System.out.println(mazeGenerator.Generate(5, 5).ToString());
    }
}
