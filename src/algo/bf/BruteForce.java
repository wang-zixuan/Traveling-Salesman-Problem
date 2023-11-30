package algo.bf;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BruteForce {
    private static int minimumCost = Integer.MAX_VALUE;
    private static List<Integer> result = new ArrayList<>();

    public static void findShortestCycle(Graph g, int cutoffTime) {
        int dimension = g.getDimension();

        // start from the first vertex (1-indexed)
        result.add(1);

        boolean[] visited = new boolean[dimension];
        visited[0] = true;

        long startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        tspHelper(g, dimension, 0, visited, 0, 1, startTime, cutoffTime);
    }

    private static void tspHelper(Graph g, int dimension, int cur,
                                  boolean[] visited, int curCost, int count,
                                  long startTime, int cutoffTime) {
        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - startTime >= cutoffTime) {
            return;
        }

        if (count == dimension) {
            curCost += g.getAdjacencyMatrix()[cur][0];
            List<Integer> localPath = new ArrayList<>(result);
            // add start vertex to form a hamiltonian cycle
            localPath.add(1);
            if (curCost < minimumCost) {
                minimumCost = curCost;
                g.setResult(localPath);
                g.setMinimumCost(minimumCost);
            }
            return;
        }

        for (int i = 0; i < dimension; i++) {
            if (!visited[i]) {
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
