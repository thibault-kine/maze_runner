package maze_runner.classes.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        Cell ranCell = maze.getCell(
            random.nextInt(1, w - 1),
            random.nextInt(1, h - 1)
        );

        String ranCoord = randomCoord();
        if (!idList.contains(ranCell.getID())) {
            ranCell.openWall(ranCoord);
            idList.add(ranCell.getID());
        }

        Cell neighCell = null;
        String wallToOpen = "";
        switch (ranCoord) {
            case "N":
                neighCell = maze.getCell(ranCell.y, ranCell.x - 1);
                wallToOpen = "S";
                break;

            case "S":
                neighCell = maze.getCell(ranCell.y, ranCell.x + 1);
                wallToOpen = "N";
                break;

            case "E":
                neighCell = maze.getCell(ranCell.y + 1, ranCell.x);
                wallToOpen = "W";
                break;

            case "W":
                neighCell = maze.getCell(ranCell.y - 1, ranCell.x);
                wallToOpen = "E";
                break;

            default:
                break;
        }

        System.out.printf("Cell found after #%d: %d\n", ranCell.getID(), neighCell.getID());
        neighCell.openWall(wallToOpen);

        return maze;
    }

    private String randomCoord() {
        int index = random.nextInt(0, coordStrings.length);
        return coordStrings[index];
    }
}
