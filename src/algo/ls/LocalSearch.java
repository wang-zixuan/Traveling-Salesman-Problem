package algo.ls;

import graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * The LocalSearch class provides methods to find the shortest cycle in a
 * graph using local search algorithms.
 */
public class LocalSearch {
    /**
     * Finds the shortest cycle in the given graph.
     *
     * @param g The input graph.
     * @param cutoffTime cutoff time for the algorithm
     * @param seed The seed for random selection of the starting vertex.
     */
    public static void findShortestCycle(Graph g, int cutoffTime, int seed) {
        int dimension = g.getDimension();

        // Initialize with a random tour (initial solution)
        List<Integer> currentTour = generateRandomTour(dimension, seed);
        int currentCost = calculateTourCost(g, currentTour);

        long startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        while (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - startTime < cutoffTime) {
            // Generate neighboring solution by swapping two random cities
            List<Integer> neighborTour = generateNeighborTour(currentTour);
            int neighborCost = calculateTourCost(g, neighborTour);
            if (neighborCost < currentCost) {
                currentTour = neighborTour;
                currentCost = neighborCost;
                updateGraphResult(g, currentTour, currentCost);
            }
        }
    }

    /**
     * Generate random tour of the graph
     *
     * @param dimension Dimension of the graph.
     * @param seed random seed.
     * @return A tour of the graph.
     */
    private static List<Integer> generateRandomTour(int dimension, int seed) {
        List<Integer> tour = new ArrayList<>();
        for (int i = 1; i <= dimension; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour, new Random(seed));
        return tour;
    }

    /**
     * Generate neighbour tour.
     *
     * @param tour current tour.
     * @return the neighbour tour.
     */
    private static List<Integer> generateNeighborTour(List<Integer> tour) {
        int n = tour.size();
        List<Integer> neighborTour = new ArrayList<>(tour);
        int i = (int) (Math.random() * n);
        int j = (int) (Math.random() * n);
        while (i == j) {
            j = (int) (Math.random() * n);
        }
        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
        }
        // Reverse the order of nodes between i and j
        while (i < j) {
            Collections.swap(neighborTour, i, j);
            i++;
            j--;
        }
        return neighborTour;
    }

    /**
     * Calculate the final cost.
     *
     * @param g Graph.
     * @param tour Current tour.
     * @return Total cost of the tour.
     */
    private static int calculateTourCost(Graph g, List<Integer> tour) {
        int cost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            int city1 = tour.get(i) - 1;
            int city2 = tour.get(i + 1) - 1;
            cost += g.getAdjacencyMatrix()[city1][city2];
        }
        // Add the cost of returning to the starting city
        int firstCity = tour.get(0) - 1;
        int lastCity = tour.get(tour.size() - 1) - 1;
        cost += g.getAdjacencyMatrix()[lastCity][firstCity];
        return cost;
    }

    /**
     * Set result to the graph.
     *
     * @param g Graph.
     * @param tour Input tour.
     * @param cost Minimum cost.
     */
    private static void updateGraphResult(Graph g, List<Integer> tour, int cost) {
        g.setResult(tour);
        g.setMinimumCost(cost);
    }
}
