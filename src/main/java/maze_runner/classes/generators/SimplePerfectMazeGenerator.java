package maze_runner.classes.generators;

import java.util.ArrayList;
import java.util.Arrays;

import maze_runner.classes.Cell;
import maze_runner.classes.Maze;
import maze_runner.interfaces.MazeGenerator;

public class SimplePerfectMazeGenerator implements MazeGenerator {

    private Maze maze;

    public Maze Generate(int w, int h) {
        maze = new Maze(w, h);

        // GENERATION //

        // get all the ids in a list so we can check and eliminate
        // the cells whose wall already have been removed
        ArrayList<Integer> idList = new ArrayList<Integer>(maze.size());

        // basically a foreach, but I'd rather cycle through maze.size()
        // instead of two foreach loops ( because of List<List<Cell>> )
        for (int i = 0; i < maze.size(); i++) {

            // Todo: la boucle s'arrête au début de la 16è itération! 

            // the main cell that we are working with !
            Cell randCell = getRandomCell(w - 1, h - 1);
            String randWall = "";
            
            ArrayList<String> forbiddenWalls = new ArrayList<>(2);
            String[] forbiddenWallsString = new String[2];
            
            System.out.println("test 1"); // apparaît
            
            // Todo: indice: le pb viendrait du bloc de code en dessous
            // to be sure that every cell is unique and present in the idList
            // if the id doesn't exist in the list yet, add it
            if (!idList.contains(randCell.getID())) {
                idList.add(randCell.getID());
            } else {
                // else, while the current cell's id is present, randomly find
                // another one until its id is not present
                while (idList.contains(randCell.getID())) {
                    randCell = getRandomCell(w - 1, h - 1);
                }
                // then, add it to the list
                idList.add(randCell.getID());
            }
            
            System.out.println("test 2"); // n'apparaît pas
            
            // Set walls that the cell shouldn't open
            // according to its position on the maze
            // HORIZONTAL
            if(randCell.x == 0) forbiddenWalls.add("W");
            else if(randCell.x == w - 1) forbiddenWalls.add("E");

            // VERTICAL
            if(randCell.y == 0) forbiddenWalls.add("N");
            else if(randCell.y == h - 1) forbiddenWalls.add("S");

            // if there are no forbidden walls, it can be any wall
            if(forbiddenWalls.isEmpty()) {
                randWall = randomWall();
            }
            else {
                // else, feed the forbidden walls into a method that will
                // remove them from the list and assign it to randomWall
                for(int j = 0; j < forbiddenWalls.size(); j++) {
                    forbiddenWallsString[j] = forbiddenWalls.get(j);
                }
                randWall = randomWallExcept(forbiddenWallsString);
            }
            
            // System.out.println(forbiddenWalls);

            // the current cell opens the wall
            randCell.openWall(randWall);
            System.out.printf("Cell #%d \t%s Wall %s\n", randCell.getID(), randCell.coordinates(), randWall);
            // System.out.println(randWall);
            // System.out.println(randCell.coordinates());
            Cell neighbour = randCell.getNeighbour(randWall);
            if(neighbour != null) {
                // the chosen neighbour opens their wall
                neighbour.openWall(randWall);
            }
            
            System.out.printf(
                "Neighbour #%d \t%s\n\n", 
                neighbour.getID(),
                neighbour.coordinates()
            );
        }

        return maze;
    }

    /**
     * Gets a random cell according to the width and height of the maze (both excluded, so it's from 0 to n-1)
     * @param w - width of the maze
     * @param h - height of the maze
     */
    private Cell getRandomCell(int w, int h) {
        return maze.getCell(random.nextInt(w), random.nextInt(h));
    }

    /**
     * Gets a random wall according to the constant 'coordStrings' in the interface
     */
    private String randomWall() {
        int index = random.nextInt(coordStrings.length - 1);
        return coordStrings[index];
    }

    private String randomWallExcept(String exception) {
        ArrayList<String> validDirections = new ArrayList<>(Arrays.asList(coordStrings));
        validDirections.remove(exception);
        int index = random.nextInt(validDirections.size() - 1);
        // System.out.println(validDirections);
        return validDirections.get(index);
    }

    /**
     * Returns a modified array of the "coordStrings" minus the exceptions
     * @param exceptions - the walls you don't want suggested to your cell
     */
    private String randomWallExcept(String[] exceptions) {
        ArrayList<String> validDirections = new ArrayList<>(Arrays.asList(coordStrings));
        for(String e : exceptions) {
            validDirections.remove(e);
        }
        int index = random.nextInt(validDirections.size() - 1);
        // System.out.println(validDirections);
        return validDirections.get(index);
    }
}
