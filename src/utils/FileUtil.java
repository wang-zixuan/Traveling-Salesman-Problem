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

/**
 * FileUtil class for file-related operations.
 */
public class FileUtil {
    private static final String NODE_COORD_SECTION = "NODE_COORD_SECTION";
    private static final String DIMENSION = "DIMENSION";

    private static final String BRUTE_FORCE = "BF";
    private static final String APPROX = "Approx";
    private static final String LOCAL_SEARCH = "LS";

    // read data from a filename
    public static Graph readCityData(String filename) {
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(filename));

            boolean isNodeCoordSection = false;
            int dimension = 0;

            // get city name by filename
            String cityName = filename.split("/")[2].split("\\.")[0];

            List<Coordinate> coordinates = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // non-coord section
                if (!isNodeCoordSection) {
                    if (line.startsWith(DIMENSION)) {
                        dimension = Integer.parseInt(line.split("\\s+")[1]);
                    } else if (line.startsWith(NODE_COORD_SECTION)) {
                        isNodeCoordSection = true;
                    }
                } else {
                    if (!line.equals("EOF")) {
                        // parse coordinate data
                        String[] coordData = line.split("\\s+");
                        int index = Integer.parseInt(coordData[0]);
                        int x = (int) Double.parseDouble(coordData[1]);
                        int y = (int) Double.parseDouble(coordData[2]);
                        coordinates.add(new Coordinate(index, x, y));
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

    // store results into result folder
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
        // if the directory doesn't exist, then create folder
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // if the file already exists, then delete it
        File file = new File(dirName + fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            // write data into the file
            FileWriter fileWriter = new FileWriter(dirName + fileName);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(String.valueOf(g.getMinimumCost()));
            bw.newLine();
            bw.write(g.getResultToString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
