package rebelalliance.smartcash.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {
    public static List<List<String>> readCSV(URI path) {
        List<List<String>> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path.getPath()));
            String line;
            while((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                data.add(new ArrayList<>(Arrays.asList(values)));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
