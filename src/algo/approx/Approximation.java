package algo.approx;

import graph.Graph;

import java.util.*;

/**
 * The Approximation class provides methods to find the shortest cycle in a
 * graph using approximation algorithms.
 */
public class Approximation {

    /**
     * Finds the shortest cycle in the given graph starting from a specified seed.
     *
     * @param g    The input graph.
     * @param seed The seed for random selection of the starting vertex.
     */
    public static void findShortestCycle(Graph g, int seed) {
        // Initialize variables
        List<Edge> mstEdges = new ArrayList<>();
        List<Integer> result = new ArrayList<>();

        // Generate MST using Prim's algorithm and store selected edges
        generateMST(g, mstEdges);

        // Perform Preorder tree walk on the MST to generate a cycle
        preorderTraversal(mstEdges, result, seed);

        // Set the result and minimum cost in the Graph object
        g.setResult(result);
        g.setMinimumCost(calculatePathCost(g, result));
    }

    /**
     * Generates the Minimum Spanning Tree (MST) of the given graph using Prim's
     * algorithm.
     *
     * @param g        The input graph.
     * @param mstEdges List to store the edges of the MST.
     */
    private static void generateMST(Graph g, List<Edge> mstEdges) {
        int dimension = g.getDimension();
        boolean[] visited = new boolean[dimension];
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        int startVertex = 0; // Start from the first vertex

        // Mark the starting vertex as visited
        visited[startVertex] = true;

        // Add edges from the starting vertex to the minHeap
        for (int i = 0; i < dimension; i++) {
            if (i != startVertex) {
                minHeap.add(new Edge(startVertex, i, g.getAdjacencyMatrix()[startVertex][i]));
            }
        }

        // Build MST using Prim's algorithm
        while (!minHeap.isEmpty()) {
            Edge edge = minHeap.poll();
            int nextVertex = edge.end;

            if (!visited[nextVertex]) {
                visited[nextVertex] = true;
                mstEdges.add(edge);

                // Add edges from the newly visited vertex to the minHeap
                for (int i = 0; i < dimension; i++) {
                    if (!visited[i]) {
                        minHeap.add(new Edge(nextVertex, i, g.getAdjacencyMatrix()[nextVertex][i]));
                    }
                }
            }
        }
    }

    /**
     * Performs Preorder tree walk on the given MST to generate a cycle starting
     * from the specified seed.
     *
     * @param mst    List of edges representing the Minimum Spanning Tree.
     * @param result List to store the vertices of the generated cycle.
     * @param seed   The seed for random selection of the starting vertex.
     */

    private static void preorderTraversal(List<Edge> mst, List<Integer> result, int seed) {
        // Convert MST edges to an adjacency matrix
        int dimension = mst.size() + 1; // The dimension is the number of edges plus one for the starting vertex
        int[][] adjacencyMatrix = new int[dimension][dimension];

        // Fill in the adjacency matrix based on MST edges
        for (Edge edge : mst) {
            adjacencyMatrix[edge.start][edge.end] = edge.weight;
            adjacencyMatrix[edge.end][edge.start] = edge.weight;
        }

        // Initialize variables
        boolean[] visited = new boolean[dimension];
        Stack<Integer> stack = new Stack<>();

        // Start from the randomly selected seed
        Random rand = new Random(seed);
        int startVertex = rand.nextInt(dimension);
        visited[startVertex] = true;
        stack.push(startVertex);

        // Perform Preorder tree walk
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            result.add(cur + 1); // Adjust to 1-indexed

            // Visit adjacent vertices
            for (int i = dimension - 1; i >= 0; i--) {
                if (adjacencyMatrix[cur][i] > 0 && !visited[i]) {
                    visited[i] = true;
                    stack.push(i);
                }
            }
        }
    }

    /**
     * Calculates the cost of the given path in the graph.
     *
     * @param g    The input graph.
     * @param path List of vertices representing the path.
     * @return The cost of the path.
     */

    private static int calculatePathCost(Graph g, List<Integer> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            cost += g.getAdjacencyMatrix()[path.get(i) - 1][path.get(i + 1) - 1];
        }
        return cost;
    }

    /**
     * Represents an edge in a graph with a start vertex, end vertex, and weight.
     */
    private static class Edge {
        int start;
        int end;
        int weight;

        /**
         * Constructs an Edge with the given start vertex, end vertex, and weight.
         *
         * @param start  The start vertex of the edge.
         * @param end    The end vertex of the edge.
         * @param weight The weight of the edge.
         */
        Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }
}
