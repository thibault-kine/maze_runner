package maze_runner.classes.generators;

import java.util.ArrayList;
import java.util.Arrays;

import maze_runner.classes.Cell;
import maze_runner.classes.Maze;
import maze_runner.interfaces.MazeGenerator;

public class SimplePerfectMazeGenerator implements MazeGenerator {

    public Maze Generate(int w, int h) {
        Maze maze = new Maze(w, h);

        // GENERATION //

        // get all the ids in a list so we can check and eliminate
        // the cells whose wall already have been removed
        ArrayList<Integer> idList = new ArrayList<>(maze.size());

        for (int i = 0; i < maze.size(); i++) {
            Cell randCell = maze.getCell(
                    random.nextInt(w),
                    random.nextInt(h));

            String randCoord = randomCoord();

            // Choses which wall the cell is allowed to open
            if (randCell.x == 0) {
                // if it's on the left, it can't open the left wall
                randCoord = randomCoordExcept("W");
            } 
            if (randCell.x == w) {
                // if it's on the right, it can't open the right wall
                randCoord = randomCoordExcept("E");
            } 
            if (randCell.y == 0) {
                // if it's on the bottom, it can't open the bottom wall
                randCoord = randomCoordExcept("S");
            } 
            if (randCell.y == h) {
                // if it's on the top, it can't open the top wall
                randCoord = randomCoordExcept("N");
            }

            Cell neighCell = null;
            String wallToOpen = "";
            switch (randCoord) {
                case "N": {
                    neighCell = maze.getCell(randCell.x, randCell.y - 1);
                    wallToOpen = "S";
                    break;
                }
                case "S": {
                    neighCell = maze.getCell(randCell.x, randCell.y + 1);
                    wallToOpen = "N";
                    break;
                }
                case "E": {
                    neighCell = maze.getCell(randCell.x + 1, randCell.y);
                    wallToOpen = "W";
                    break;
                }
                case "W": {
                    neighCell = maze.getCell(randCell.x - 1, randCell.y);
                    wallToOpen = "E";
                    break;
                }
                default:
                    break;
            }

            if (!idList.contains(randCell.getID())) {
                randCell.openWall(randCoord);
                idList.add(randCell.getID());
            }

            System.out.printf("Cell found after #%d: %d\n", randCell.getID(), neighCell.getID());
            neighCell.openWall(wallToOpen);
        }

        return maze;
    }

    private String randomCoord() {
        int index = random.nextInt(0, coordStrings.length - 1);
        return coordStrings[index];
    }

    private String randomCoordExcept(String exception) {
        ArrayList<String> validDirections = new ArrayList<>(Arrays.asList(coordStrings));
        validDirections.remove(exception);
        int index = random.nextInt(0, coordStrings.length - 1);
        return coordStrings[index];
    }
}
