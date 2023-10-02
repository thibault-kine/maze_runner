package maze_runner.classes;

public class Tuple {
    
    public final Integer x;
    public final Integer y;

    public Tuple(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
