package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graph.Coordinate;
import graph.Graph;

public class FileUtil {
    private static final String NAME = "NAME";
    private static final String NODE_COORD_SECTION = "NODE_COORD_SECTION";
    private static final String DIMENSION = "DIMENSION";

    private static final String BRUTE_FORCE = "BF";
    private static final String APPROX = "Approx";
    private static final String LOCAL_SEARCH = "LS";

    public static Graph readCityData(String filename) {
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(filename));

            boolean isNodeCoordSection = false;
            int dimension = 0;
            String cityName = "";

            List<Coordinate> coordinates = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!isNodeCoordSection) {
                    if (line.startsWith(NAME)) {
                        cityName = line.split("\\s+")[1];
                    }
                    else if (line.startsWith(DIMENSION)) {
                        dimension = Integer.parseInt(line.split("\\s+")[1]);
                    } else if (line.startsWith(NODE_COORD_SECTION)) {
                        isNodeCoordSection = true;
                    }
                } else {
                    if (!line.equals("EOF")) {
                        String[] coordData = line.split("\\s+");
                        int x = (int) Double.parseDouble(coordData[1]);
                        int y = (int) Double.parseDouble(coordData[2]);
                        coordinates.add(new Coordinate(x, y));
                    } else {
                        break;
                    }
                }
            }

            if (cityName.length() == 0 || coordinates.size() == 0 || dimension == 0) {
                throw new RuntimeException("No data found!");
            }

            return new Graph(cityName, dimension, coordinates);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void storeResults(String algName, Graph g, int cutoffTime, int seed) {
        String dirName = "./results/" + algName + "/";
        String fileName = g.getCityName().toLowerCase() + "_" + algName + "_";

        switch (algName) {
            case BRUTE_FORCE:
                fileName += cutoffTime + ".sol";
                break;
            case APPROX:
                fileName += seed + ".sol";
                break;
            case LOCAL_SEARCH:
                fileName += cutoffTime + "_" + seed + ".sol";
                break;
            default:
                break;
        }

        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dirName + fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileWriter fileWriter = new FileWriter(dirName + fileName);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(String.valueOf((double) g.getMinimumCost()));
            bw.newLine();
            bw.write(g.getResultToString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
