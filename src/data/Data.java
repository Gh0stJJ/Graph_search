package data;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Data {
    ArrayList<ArrayList<String>> data = new ArrayList<>();

    public Data() {

    }
    /*
    * Read data from csv
    * @param path
     */
    public ArrayList<ArrayList<String>> getData(String path) {
        //Read data from csv
        try {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(path))
                    .withCSVParser(parser)
                    .build();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                ArrayList<String> row = new ArrayList<>();
                Collections.addAll(row, nextLine);
                data.add(row);
            }
        }catch (Exception e){
            System.out.println("Error al leer el archivo");
        }
        return data;
    }
}
