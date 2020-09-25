package writer;

import com.opencsv.CSVWriter;
import util.Configure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public void writeCsv(List<String[]> dataList, String fileName) {
        File file = new File(fileName);

        try {
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter with ',' as separator
            CSVWriter writer = new CSVWriter(outputfile, ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END
            		);
            writer.writeAll(dataList);

            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void writeCsv_semicolon(List<String[]> dataList, String fileName) {
        File file = new File(fileName);

        try {
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter with ',' as separator
            CSVWriter writer = new CSVWriter(outputfile, ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END
            		);
            writer.writeAll(dataList);

            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
