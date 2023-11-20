package graph;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double calcEucDist(Coordinate coordinate) {
        int distX = Math.abs(this.x - coordinate.getX());
        int distY = Math.abs(this.x - coordinate.getY());
        return Math.sqrt(distX * distX + distY * distY);
    }
}
