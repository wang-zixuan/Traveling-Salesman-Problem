package graph;

import java.util.List;

public class Graph {
    private String cityName;
    private int dimension;
    private List<Coordinate> coordinates;
    private int[][] adjacencyMatrix;
    private List<Integer> result;
    private int minimumCost;

    public Graph(String cityName, int dimension, List<Coordinate> coordinates) {
        this.setCityName(cityName);
        this.setDimension(dimension);
        this.setCoordinates(coordinates);
        this.setAdjacencyMatrix();
    }

    public int getDimension() {
        return this.dimension;
    }

    public List<Coordinate> getCoordinates() {
        return this.coordinates;
    }

    public int[][] getAdjacencyMatrix() {
        return this.adjacencyMatrix;
    }

    public List<Integer> getResult() {
        return this.result;
    }

    public int getMinimumCost() {
        return this.minimumCost;
    }

    public String getCityName() {
        return this.cityName;
    }

    public String getResultToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.result.size(); i++) {
            sb.append(result.get(i));
            if (i < this.result.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public void setResult(List<Integer> result) {
        System.out.println(result.toString());
        this.result = result;
    }

    public void setMinimumCost(int minimumCost) {
        this.minimumCost = minimumCost;
    }

    private void setAdjacencyMatrix() {
        this.adjacencyMatrix = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            Coordinate c = this.coordinates.get(i);
            for (int j = 0; j < i; j++) {
                this.adjacencyMatrix[i][j] = this.adjacencyMatrix[j][i] = c.calcEucDist(this.coordinates.get(j));
            }
        }
    }
}
