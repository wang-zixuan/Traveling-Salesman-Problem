package algo.ls;

import graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LocalSearch {
    private static int minimumCost = Integer.MAX_VALUE;
    private static List<Integer> result = new ArrayList<>();

    public static void findShortestCycle(Graph g, int cutoffTime, int seed) {
        int dimension = g.getDimension();

        List<Integer> currentTour = generateRandomTour(dimension, seed);
        int currentCost = calculateTourCost(g, currentTour);

        long startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        while (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - startTime < cutoffTime) {
            List<Integer> neighborTour = generateNeighborTour(currentTour);
            int neighborCost = calculateTourCost(g, neighborTour);
            if (neighborCost < currentCost) {
                currentTour = neighborTour;
                currentCost = neighborCost;
                updateGraphResult(g, currentTour, currentCost);
            }
        }
    }

    private static List<Integer> generateRandomTour(int dimension, int seed) {
        List<Integer> tour = new ArrayList<>();
        for (int i = 1; i <= dimension; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour, new Random(seed));
        return tour;
    }

    private static List<Integer> generateNeighborTour(List<Integer> tour) {
        int tourSize = tour.size();
        List<Integer> neighborTour = new ArrayList<>(tour);
        int randomIndex1 = (int) (Math.random() * tourSize);
        int randomIndex2 = (int) (Math.random() * tourSize);
        Collections.swap(neighborTour, randomIndex1, randomIndex2);
        return neighborTour;
    }

    private static int calculateTourCost(Graph g, List<Integer> tour) {
        int cost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            int city1 = tour.get(i) - 1;
            int city2 = tour.get(i + 1) - 1;
            cost += g.getAdjacencyMatrix()[city1][city2];
        }
        int firstCity = tour.get(0) - 1;
        int lastCity = tour.get(tour.size() - 1) - 1;
        cost += g.getAdjacencyMatrix()[lastCity][firstCity];
        return cost;
    }

    private static void updateGraphResult(Graph g, List<Integer> tour, int cost) {
        g.setResult(tour);
        g.setMinimumCost(cost);
    }
}
