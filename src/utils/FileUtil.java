package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graph.Coordinate;
import graph.Graph;

public class FileUtil {
    private static final String NODE_COORD_SECTION = "NODE_COORD_SECTION";
    private static final String DIMENSION = "DIMENSION";

    public static Graph ReadCityData(String filename) {
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(filename));

            boolean isNodeCoordSection = false;
            int dimension = 0;

            List<Coordinate> coordinates = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!isNodeCoordSection) {
                    if (line.startsWith(DIMENSION)) {
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
                    }
                }
            }

            return new Graph(dimension, coordinates);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
