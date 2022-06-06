package classes;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static double between(Position x, Position y) {
        return Math.sqrt((x.x - y.x)*(x.x - y.x)  + (x.y - y.y)*(x.y - y.y));
    }
}
