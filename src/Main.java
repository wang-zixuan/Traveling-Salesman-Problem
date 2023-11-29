import java.util.HashMap;
import java.util.Map;

import algo.approx.Approximation;
import algo.bf.BruteForce;
import algo.ls.LocalSearch;
import graph.Graph;
import utils.FileUtil;

public class Main {
    private static final String INST = "inst";
    private static final String ALG = "alg";
    private static final String TIME = "time";
    private static final String SEED = "seed";

    private static final String BRUTE_FORCE = "BF";
    private static final String APPROX = "Approx";
    private static final String LOCAL_SEARCH = "LS";

    public static void main(String[] args) {
        // Command line arguments
        if (args.length != 6 && args.length != 8) {
            throw new IllegalArgumentException("Please enter 3 or 4 input arguments!");
        }

        Map<String, String> arguments = new HashMap<>();

        for (int i = 0; i < args.length; i += 2) {
            arguments.put(args[i].substring(1), args[i + 1]);
        }

        String filename = arguments.get(INST);
        String alg = arguments.get(ALG);
        int cutoffTime = Integer.parseInt(arguments.get(TIME));
        int seed = Integer.parseInt(arguments.getOrDefault(SEED, "0"));

        Graph g = FileUtil.readCityData(filename);

        if (g == null) {
            throw new NullPointerException("Can't read data!");
        }

        switch (alg) {
            case BRUTE_FORCE:
                BruteForce.findShortestCycle(g, cutoffTime);
                break;
            case APPROX:
                Approximation.findShortestCycle(g, seed);
                break;
            case LOCAL_SEARCH:
                LocalSearch.findShortestCycle(g, cutoffTime, seed);
                break;
            default:
                throw new IllegalArgumentException("Invalid -alg argument!");
        }

        FileUtil.storeResults(alg, g, cutoffTime, seed);
    }
}
