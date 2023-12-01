package graph;

public class Coordinate {
    private int index;
    private int x;
    private int y;

    public Coordinate(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public int getIndex() {
        return this.index;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int calcEucDist(Coordinate coordinate) {
        int distX = Math.abs(this.x - coordinate.getX());
        int distY = Math.abs(this.y - coordinate.getY());
        long squaredDist = (long) distX * distX + (long) distY * distY;
        return (int) Math.sqrt(squaredDist);
    }
}
