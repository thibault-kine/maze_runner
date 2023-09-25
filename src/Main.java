import classes.generators.SimplePerfectMazeGenerator;

public class Main {
    public static void main(String[] args) {

        System.out.println(new SimplePerfectMazeGenerator().Generate(7, 5).ToString());
    }
}
