package maze_runner.classes.generators;

import java.util.*;

import maze_runner.interfaces.*;
import maze_runner.classes.Maze;
import maze_runner.classes.Tuple;
import maze_runner.classes.Cell;

public class SimpleImperfectMazeGenerator implements MazeGenerator {

    private Maze maze = null;

    @Override
    public Maze Generate(int width, int height) {

        // #region CREATION DU MAZE
        try {
            maze = new Maze(width, height);
            if (width < MIN_WIDTH || height < MIN_HEIGHT) {
                throw new Exception(String.format(
                        "Erreur: veuillez entrer une largeur et hauteur valides supérieurs ou égaux à %d\n",
                        MIN_HEIGHT));
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        //#endregion

        // #region GENERATION
        // * ALGORITHME DE PRIM * //

        // cellules actives
        ArrayList<Cell> activeCells = new ArrayList<>(maze.size());
        // position des cellules explorées
        ArrayList<Tuple> cellPosStack = new ArrayList<>(maze.size() - 1);
        // id des cellules ajoutées à la stack
        int id = 0;

        // Cellule principale 
        Cell cell = maze.getRandomCell(width, height);
        // tant que le tableau des cellules actives n'est pas plein 
        do {
            // On choisit arbitrairement une cellule, on stocke la position 
            // en cours et on la marque comme visitée (vrai)
            cellPosStack.add(cell.position);
            cell.visited = true;

            // Puis on regarde quelles sont les cellules voisines 
            // possibles et non visitées
            ArrayList<Cell> neighbors = new ArrayList<>();
            ArrayList<String> validWalls = getValidWalls(checkForbiddenWalls(cell, width, height));
            for(String wall : validWalls) {
                Cell n = cell.getNeighbour(wall, width, height);
                if(n != null && !n.visited) {
                    neighbors.add(n);
                }
            }
            
            System.out.println(validWalls.size());
            // tant n'y a plus de murs valides
            while(validWalls.size() == 0) {
                // on revient en arrière
                cell = maze.getCell(cellPosStack.get(--id));
            }
            id++;
            System.out.println(cell);
            System.out.println("validWalls: " + validWalls);
            System.out.println("neighbors: " + neighbors);

            // S'il y a au moins une possibilité, on en choisit une au hasard, 
            // on ouvre le mur et on recommence avec la nouvelle cellule
            Random random = new Random();
            Cell neighbor = neighbors.get(random.nextInt(neighbors.size()));

            if(neighbor.position.x < cell.position.x) {
                cell.openWall("W");
                neighbor.openWall("E");
            }
            else if(neighbor.position.x > cell.position.x) {
                cell.openWall("E");
                neighbor.openWall("W");
            }
            
            if(neighbor.position.y < cell.position.y) {
                cell.openWall("N");
                neighbor.openWall("S");
            }
            else if(neighbor.position.y > cell.position.y) {
                cell.openWall("S");
                neighbor.openWall("N");
            }


            // System.out.println(activeCells);
            System.out.println(maze);

            cell = neighbor;

            // réinitialiser toutes les autres variables
            validWalls.clear();
            neighbors.clear();

        } while(activeCells.size() < maze.size());

        // #endregion

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

    private ArrayList<String> getValidWalls(ArrayList<String> forbiddenWalls) {
        ArrayList<String> validWalls = new ArrayList<>(Arrays.asList(coordStrings));
        for(String fw : forbiddenWalls) {
            if(validWalls.contains(fw)) validWalls.remove(fw);
        }
        return validWalls;
    }

    /**
     * Retourne une liste de murs dans laquelle on a retiré les murs interdits
     * 
     * @param exceptions - les murs que tu ne veux pas que ta cellule ouvre
     */
    private String randomWallExcept(ArrayList<String> exceptions) {
        ArrayList<String> validDirections = new ArrayList<>(Arrays.asList(coordStrings));
        for (String e : exceptions) {
            validDirections.remove(e);
        }
        int index = random.nextInt(validDirections.size() - 1);
        // System.out.println(validDirections);
        return validDirections.get(index);
    }
}