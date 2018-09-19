package hidepwriter;

import com.opencsv.CSVWriter;
import util.Configure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public void toCSV(List<String[]> nodeList, List<String[]> edgeList) {
        Configure configure = Configure.getConfigureInstance();
        writeCsv(nodeList, configure.getOutputCsvNodeFile());
        writeCsv(edgeList, configure.getOutputCsvEdgeFile());
    }

    private void writeCsv(List<String[]> dataList, String fileName) {
        File file = new File(fileName);

        try {
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter with '|' as separator
            CSVWriter writer = new CSVWriter(outputfile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            // create a List which contains String array
            //List<String[]> data = new ArrayList<String[]>();
            //data.add(new String[] { "Name", "Class", "Marks" });
            //data.add(new String[] { "Aman", "10", "620" });
            //data.add(new String[] { "Suraj", "10", "630" });
            writer.writeAll(dataList);

            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
