package graph;

/**
 * Coordinate class to store the location of each vertex on a 2D plane.
 */
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

    /**
     * Calculate Euclidean distances between two coordinates.
     *
     * @param coordinate The input coordinate.
     * @return Euclidean distance between two coordinates.
     */
    public int calcEucDist(Coordinate coordinate) {
        int distX = Math.abs(this.x - coordinate.getX());
        int distY = Math.abs(this.y - coordinate.getY());
        long squaredDist = (long) distX * distX + (long) distY * distY;
        return (int) Math.sqrt(squaredDist);
    }
}
