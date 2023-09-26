package maze_runner.classes.generators;

import java.util.ArrayList;
import java.util.Arrays;

import maze_runner.classes.Cell;
import maze_runner.classes.Maze;
import maze_runner.interfaces.MazeGenerator;

public class SimplePerfectMazeGenerator implements MazeGenerator {

    private Maze maze;

    public Maze Generate(int w, int h) {
        try {
            maze = new Maze(w, h);
            // vérif de la taille minimale
            if(w < MIN_WIDTH || h < MIN_HEIGHT) {
                throw new Exception(String.format("Erreur: veuillez entrer une largeur et hauteur valides supérieurs ou égaux à %d\n", MIN_HEIGHT));
            }
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }

        // GENERATION //

        // cherche les id de toutes les cellules pour qu'on puisse vérifier qu'elles
        // sont uniques
        ArrayList<Integer> idList = new ArrayList<Integer>(maze.size());

        // si je n'avais pas fait de for, j'aurais du faire 2 for-each imbriqués
        // car cells est une liste de liste
        for (int i = 0; i < maze.size(); i++) {

            // la cellule principale !
            Cell randCell = getRandomCell(w - 1, h - 1);
            String randWall = "";
            
            ArrayList<String> forbiddenWalls = new ArrayList<>(2);
            String[] forbiddenWallsString = new String[2];
            
            // pour être sûr que chaque id est unique, dès qu'on trouve un id inconnu,
            // le rentrer...
            if (!idList.contains(randCell.getID())) {
                idList.add(randCell.getID());
            } else {
                // ... sinon, en chercher une autre tant que l'id 
                // n'est pas encore dans la liste
                while (idList.contains(randCell.getID())) {
                    randCell = getRandomCell(w, h);
                }
                // ensuite, l'ajouter à la liste
                idList.add(randCell.getID());
            }
            
            // établit les murs que la cellule ne devrais pas ouvrir
            // selon sa position sur le labyrinthe
            // HORIZONTAL
            if(randCell.x == 0) forbiddenWalls.add("W");
            else if(randCell.x == w - 1) forbiddenWalls.add("E");

            // VERTICAL
            if(randCell.y == 0) forbiddenWalls.add("N");
            else if(randCell.y == h - 1) forbiddenWalls.add("S");

            // s'il n'y a pas de murs interdits, chercher un mur au hasard
            if(forbiddenWalls.isEmpty()) {
                randWall = randomWall();
            }
            else {
                // sinon, donner les murs interdit à une méthode qui va retourner
                // la liste des murs SANS les murs interdits
                for(int j = 0; j < forbiddenWalls.size(); j++) {
                    forbiddenWallsString[j] = forbiddenWalls.get(j);
                }
                randWall = randomWallExcept(forbiddenWallsString);
            }
            
            // System.out.println(forbiddenWalls);

            // la cellule ouvre son mur
            randCell.openWall(randWall);
            System.out.printf(
                "Cell #%d \t%s Wall %s\n", 
                randCell.getID(), 
                randCell.coordinates(), 
                randWall
            );
            // System.out.println(randWall);
            // System.out.println(randCell.coordinates());
            Cell neighbour = randCell.getNeighbour(randWall);
            if(neighbour != null) {
                // le voisin ouvre le mur connexe
                switch(randWall) {
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
     * Prends une cellule au hasard entre (0, 0) et (n-1, n-1)
     * @param w - largeur du labyrinthe
     * @param h - hauteur du labyrinthe
     */
    private Cell getRandomCell(int w, int h) {
        return maze.getCell(random.nextInt(w), random.nextInt(h));
    }

    /**
     * Prends un mur au hasard selon la const "coordStrings" dans l'interface MazeGenerator
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
     * @param exceptions - les murs que tu ne veux pas que ta cellule ouvre
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
