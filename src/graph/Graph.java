package graph;

import java.util.List;

public class Graph {
    private int dimension;
    private List<Coordinate> coordinates;
    private double[][] adjacencyMatrix;
    private List<Integer> result;

    public Graph(int dimension, List<Coordinate> coordinates) {
        this.dimension = dimension;
        this.coordinates = coordinates;
        computeAdjacencyMatrix();
    }

    public int getDimension() {
        return this.dimension;
    }

    public List<Coordinate> getCoordinates() {
        return this.coordinates;
    }

    public double[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public void setAdjacencyMatrix(double[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }

    private void computeAdjacencyMatrix() {
        this.adjacencyMatrix = new double[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            Coordinate c = this.coordinates.get(i);
            for (int j = 0; j < i; j++) {
                this.adjacencyMatrix[i][j] = this.adjacencyMatrix[j][i] = c.calcEucDist(this.coordinates.get(j));
            }
        }
    }
}
