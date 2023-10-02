package maze_runner.misc;

public class InvalidGenerationTypeException extends Exception {

    public InvalidGenerationTypeException() {
        super("Erreur: Veuillez fournir un type de labyrinthe et une méthode de génération valides.\n");
    }
}