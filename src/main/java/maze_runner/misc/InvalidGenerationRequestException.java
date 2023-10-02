package maze_runner.misc;

public class InvalidGenerationRequestException extends Exception {
    
    public InvalidGenerationRequestException() {
        super("Erreur: Requête de génération invalide.\nUtilisation : java -jar MazeGenerator [largeur] [hauteur] [perfect/imperfect]\r\n" + //
                "[simple/graph/optimized]\n");
    }
}
