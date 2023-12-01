package algo.approx;

import graph.Coordinate;
import graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Approximation {

    public static void findShortestCycle(Graph g, int cutoffTime, int seed) {
        // Set a seed for reproducibility
        Random rand = new Random(seed);

        // Find the Minimum Spanning Tree using Prim's algorithm
        List<Integer> mst = primMST(g);

        // Modify the MST to create a Hamiltonian cycle
        List<Integer> hamiltonianCycle = new ArrayList<>(mst);
        for (int i = 0; i < hamiltonianCycle.size(); i += 2) {
            int node = hamiltonianCycle.get(i);
            if (!hamiltonianCycle.contains(node + 1)) {
                int next = (node + 1) % g.getDimension();
                hamiltonianCycle.add(i + 1, next);
            }
        }

        // Update the result and minimumCost in the graph
        g.setResult(hamiltonianCycle);
        g.setMinimumCost(calculateTourCost(g, hamiltonianCycle));
    }

    // Prim's algorithm to find Minimum Spanning Tree
    private static List<Integer> primMST(Graph g) {
        int n = g.getDimension();
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        int[] key = new int[n];

        for (int i = 0; i < n; i++) {
            key[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<Coordinate> pq = new PriorityQueue<>();
        pq.add(new Coordinate(0, 0)); // Start from vertex 0

        key[0] = 0;

        while (!pq.isEmpty()) {
            int u = pq.poll().getY(); // Extract the minimum key vertex
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                int weight = g.getAdjacencyMatrix()[u][v];
                if (!visited[v] && weight < key[v]) {
                    parent[v] = u;
                    key[v] = weight;
                    pq.add(new Coordinate(weight, v));
                }
            }
        }

        List<Integer> mst = new LinkedList<>();
        for (int i = 1; i < n; i++) {
            mst.add(parent[i]);
            mst.add(i);
        }

        return mst;
    }

    // Calculate the cost of the tour
    private static int calculateTourCost(Graph g, List<Integer> tour) {
        int cost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            int u = tour.get(i);
            int v = tour.get(i + 1);
            cost += g.getAdjacencyMatrix()[u][v];
        }
        // Add the cost to return to the starting city
        cost += g.getAdjacencyMatrix()[tour.get(tour.size() - 1)][tour.get(0)];
        return cost;
    }
}
