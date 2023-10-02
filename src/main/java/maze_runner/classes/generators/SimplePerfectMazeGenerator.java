package maze_runner.classes.generators;

import java.util.ArrayList;
import java.util.Arrays;

import maze_runner.classes.Cell;
import maze_runner.classes.Maze;
import maze_runner.interfaces.MazeGenerator;
import maze_runner.misc.MazeSizeException;

public class SimplePerfectMazeGenerator implements MazeGenerator {

    private Maze maze;

    @Override
    public Maze Generate(int w, int h) {
        try {
            maze = new Maze(w, h);
            // vérif de la taille minimale
            if (w < MIN_WIDTH || h < MIN_HEIGHT) {
                throw new MazeSizeException();
            }
        } catch (MazeSizeException e) {
            System.out.println(e);
            return null;
        }

        // GENERATION //

        // cherche les id de toutes les cellules pour qu'on puisse vérifier qu'elles
        // sont uniques
        ArrayList<Integer> idList = new ArrayList<Integer>(maze.size());

        // si je n'avais pas fait de for, j'aurais du faire 2 for-each imbriqués
        // car cells est une liste de liste
        while (maze.numberOfN(0) != maze.size()) {

            // la cellule principale !
            Cell randCell;
            String randWall = "";
            Cell neighbour;

            randCell = maze.getRandomCell(w - 1, h - 1);

            ArrayList<String> forbiddenWalls = new ArrayList<>(2);
            String[] forbiddenWallsString = new String[2];

            if (maze.numberOfN(randCell.getID()) <= 1) {
                // pour être sûr que chaque id est unique, dès qu'on trouve un id inconnu,
                // le rentrer...
                if (!idList.contains(randCell.getID())) {
                    idList.add(randCell.getID());
                } else {
                    // ... sinon, en chercher une autre tant que l'id
                    // n'est pas encore dans la liste
                    while (idList.contains(randCell.getID())) {
                        randCell = maze.getRandomCell(w, h);
                    }
                    // ensuite, l'ajouter à la liste
                    idList.add(randCell.getID());
                }
            }

            forbiddenWalls = checkForbiddenWalls(randCell, w, h);

            // s'il n'y a pas de murs interdits, chercher un mur au hasard
            if (forbiddenWalls.isEmpty()) {
                randWall = randomWall();
            } else {
                // sinon, donner les murs interdit à une méthode qui va retourner
                // la liste des murs SANS les murs interdits
                for (int j = 0; j < forbiddenWalls.size(); j++) {
                    forbiddenWallsString[j] = forbiddenWalls.get(j);
                }
                randWall = randomWallExcept(forbiddenWallsString);
            }

            neighbour = randCell.getNeighbour(randWall, w, h);

            // tant que la cellule voisine à le même id que la cellule actuelle
            // recommencer à chercher une nouvelle cellule
            while (
                randCell.getID() == neighbour.getID() ||
                randCell.isWallOpen(randWall)
            ) {
                // System.out.println("Old:");
                // System.out.printf(" \trandCell at %s\n", randCell.coordinates());
                // System.out.printf(" \tforbiddenWalls: %s\n", forbiddenWalls);
                // System.out.printf(" \trandWall: %s\n", randWall);
                // System.out.printf(" \tneighbour at %s\n", neighbour.coordinates());
                
                randCell = maze.getRandomCell(w, h);
                forbiddenWalls = checkForbiddenWalls(randCell, w, h);
                for (int j = 0; j < forbiddenWalls.size(); j++) {
                    forbiddenWallsString[j] = forbiddenWalls.get(j);
                }
                randWall = randomWallExcept(forbiddenWallsString);
                neighbour = randCell.getNeighbour(randWall, w, h);
                
                // System.out.println("New:");
                // System.out.printf(" \trandCell at %s\n", randCell.coordinates());
                // System.out.printf(" \tforbiddenWalls: %s\n", forbiddenWalls);
                // System.out.printf(" \trandWall: %s\n", randWall);
                // System.out.printf(" \tneighbour at %s\n", neighbour.coordinates());
            }

            // remplace l'id le plus grand par l'id le plus petit
            if (neighbour.getID() > randCell.getID()) {
                for (Cell c : maze.getCellsByID(neighbour.getID())) {
                    c.setID(randCell.getID());
                }
            }
            if (neighbour.getID() < randCell.getID()) {
                for (Cell c : maze.getCellsByID(randCell.getID())) {
                    c.setID(neighbour.getID());
                }
            }

            // System.out.printf(
            //         "Cell #%d \t%s Wall %s\n",
            //         randCell.getID(),
            //         randCell.coordinates(),
            //         randWall);

            // System.out.printf(
            //         "Neighbour #%d \t%s\n\n",
            //         neighbour.getID(),
            //         neighbour.coordinates());

            // la cellule ouvre son mur
            randCell.openWall(randWall);
            // le voisin ouvre le mur connexe
            switch (randWall) {
                case "N":
                    neighbour.openWall("S");
                    break;
                case "S":
                    neighbour.openWall("N");
                    break;
                case "E":
                    neighbour.openWall("W");
                    break;
                case "W":
                    neighbour.openWall("E");
                    break;
                default:
                    break;

            }
            // System.out.println(maze.toString());

        }

        return maze;
    }

    
    private ArrayList<String> checkForbiddenWalls(Cell cell, int w, int h) {
        ArrayList<String> forbiddenWalls = new ArrayList<>();
        // établit les murs que la cellule ne devrais pas ouvrir
        // selon sa position sur le labyrinthe
        // HORIZONTAL
        if (cell.position.x == 0)
            forbiddenWalls.add("W");
        if (cell.position.x == w - 1)
            forbiddenWalls.add("E");

        // VERTICAL
        if (cell.position.y == 0)
            forbiddenWalls.add("N");
        if (cell.position.y == h - 1)
            forbiddenWalls.add("S");

        return forbiddenWalls;
    }

    /**
     * Prends un mur au hasard selon la const "coordStrings" dans l'interface
     * MazeGenerator
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
     * Retourne une liste de murs dans laquelle on a retiré les murs interdits
     * 
     * @param exceptions - les murs que tu ne veux pas que ta cellule ouvre
     */
    private String randomWallExcept(String[] exceptions) {
        ArrayList<String> validDirections = new ArrayList<>(Arrays.asList(coordStrings));
        for (String e : exceptions) {
            validDirections.remove(e);
        }
        int index = random.nextInt(validDirections.size() - 1);
        // System.out.println(validDirections);
        return validDirections.get(index);
    }
}
