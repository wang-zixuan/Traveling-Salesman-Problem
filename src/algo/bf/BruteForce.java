package algo.bf;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The BruteForce class provides methods to find the shortest cycle in a
 * graph using brute force.
 */
public class BruteForce {
    private static int minimumCost = Integer.MAX_VALUE;
    private static List<Integer> result = new ArrayList<>();

    /**
     * Finds the shortest cycle in the given graph.
     *
     * @param g The input graph.
     * @param cutoffTime The cutoff time for brute force algorithm.
     */
    public static void findShortestCycle(Graph g, int cutoffTime) {
        int dimension = g.getDimension();

        // start from the first vertex (1-indexed)
        result.add(1);

        boolean[] visited = new boolean[dimension];
        visited[0] = true;

        long startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        tspHelper(g, dimension, 0, visited, 0, 1, startTime, cutoffTime);
    }

    /**
     * Helper function for recursion.
     *
     * @param g The input graph.
     * @param dimension Dimension of current instance.
     * @param cur Current vertex.
     * @param visited A boolean array to decide whether the vertex has been visited.
     * @param curCost Current cost during recursion.
     * @param count Number of vertices visited.
     * @param startTime Start time of the helper function.
     * @param cutoffTime Total running time of the helper function.
     */

    private static void tspHelper(Graph g, int dimension, int cur,
                                  boolean[] visited, int curCost, int count,
                                  long startTime, int cutoffTime) {
        // if the program exceeds the cutoff time, then exit
        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - startTime >= cutoffTime) {
            return;
        }

        if (count == dimension) {
            // if we get smaller cost, update the result
            if (curCost < minimumCost) {
                minimumCost = curCost;
                List<Integer> localPath = new ArrayList<>(result);
                g.setResult(localPath);
                g.setMinimumCost(minimumCost);
            }
            return;
        }

        for (int i = 0; i < dimension; i++) {
            if (!visited[i]) {
                // if current cost or current cost plus edge cost is bigger than minimum cost, then ignore
                if (curCost > minimumCost || curCost + g.getAdjacencyMatrix()[cur][i] > minimumCost) continue;
                visited[i] = true;
                result.add(i + 1);
                tspHelper(g, dimension, i, visited, curCost + g.getAdjacencyMatrix()[cur][i],
                        count + 1, startTime, cutoffTime);
                visited[i] = false;
                result.remove(result.size() - 1);
            }
        }
    }
}
