import java.util.HashMap;
import java.util.List;
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

    private static Map<String, String> arguments = new HashMap<>();

    public static void main(String[] args) {
        // Command line arguments
        if (args.length != 8) {
            throw new IllegalArgumentException("Please enter 4 input arguments!");
        }

        for (int i = 0; i < args.length; i += 2) {
            arguments.put(args[i].substring(1), args[i + 1]);
        }

        String filename = arguments.get(INST);
        String alg = arguments.get(ALG);
        int cutoffTime = Integer.parseInt(arguments.get(TIME));
        int seed = Integer.parseInt(arguments.get(SEED));

        Graph g = FileUtil.ReadCityData(filename);

        switch (alg) {
            case BRUTE_FORCE:
                BruteForce.findShortestCycle(g, cutoffTime, seed);
                break;
            case APPROX:
                Approximation.findShortestCycle(g, cutoffTime, seed);
                break;
            case LOCAL_SEARCH:
                LocalSearch.findShortestCycle(g, cutoffTime, seed);
                break;
            default:
                throw new IllegalArgumentException("Invalid -alg argument!");
        }

        // store results
    }
}
