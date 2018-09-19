package hidepwriter;

import formator.fjson.JDepObject;
import formator.fxml.XDepObject;
import util.Configure;

import java.util.ArrayList;

public class WriterIntf {

    Configure configure = Configure.getConfigureInstance();

    public void run(JDepObject jDepObject, XDepObject xDepObject, ArrayList<String[]> nodes, ArrayList<String[]> edges) {

        //output data by writers
        JsonWriter jsonWriter = new JsonWriter();
        jsonWriter.toJson(jDepObject);
        System.out.println("Export "+configure.getOutputJsonFile()+" successfully...");

        XmlWriter xmlWriter = new XmlWriter();
        xmlWriter.toXml(xDepObject);
        System.out.println("Export "+configure.getOutputXmlFile()+" successfully...");

        CsvWriter csvWriter = new CsvWriter();
        csvWriter.toCSV(nodes, edges);
        System.out.println("Export "+configure.getOutputCsvNodeFile()+" successfully...");
        System.out.println("Export "+configure.getOutputCsvEdgeFile()+" successfully...");

        DotWriter dotWriter = new DotWriter();
        dotWriter.writeDot(DotUtil.FILTER_NO_DEP);
        dotWriter.writeDot(DotUtil.FILTER_FILE_FOLDER_DEP);
        dotWriter.writeDot(DotUtil.FILTER_CLASS_DEP);
        dotWriter.writeDot(DotUtil.FILTER_FUNC_CLASS_DEP);
        dotWriter.writeDot(DotUtil.FILTER_DEFAULT_DEP);

    }

}
