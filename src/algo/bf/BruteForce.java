package algo.bf;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BruteForce {
    private static int minimumCost = Integer.MAX_VALUE;
    private static List<Integer> result = new ArrayList<>();

    public static void findShortestCycle(Graph g, int cutoffTime, int seed) {
        int dimension = g.getDimension();
        Random rdm = new Random(seed);
        int start = rdm.nextInt(dimension);
        result.add(start);

        boolean[] visited = new boolean[dimension];
        for (int i = 0; i < dimension; i++) {
            visited[i] = (i == start);
        }

        long startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        tspHelper(g, dimension, start, start, visited, 0, 1, startTime, cutoffTime);
    }

    private static void tspHelper(Graph g, int dimension, int start, int cur, boolean[] visited,
                                  int curCost, int count,
                                  long startTime, int cutoffTime) {
        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - startTime >= cutoffTime) {
            return;
        }

        if (count == dimension) {
            curCost += g.getAdjacencyMatrix()[cur][start];
            List<Integer> localPath = new ArrayList<>(result);
            localPath.add(start);
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
                result.add(i);
                tspHelper(g, dimension, start, i, visited, curCost + g.getAdjacencyMatrix()[cur][i],
                        count + 1, startTime, cutoffTime);
                visited[i] = false;
                result.remove(result.size() - 1);
            }
        }
    }
}
