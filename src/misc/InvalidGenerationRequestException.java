package misc;

public class InvalidGenerationRequestException extends Exception {
    
    public InvalidGenerationRequestException() {
        super("Requête de génération invalide.\nUtilisation : java -jar MazeGenerator [largeur] [hauteur] [perfect/imperfect]\r\n" + //
                "[simple/graph/optimized]");
    }
}
