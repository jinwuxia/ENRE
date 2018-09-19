package formator;


import formator.fjson.JBuildObject;
import formator.fjson.JDepObject;
import formator.fxml.XBuildObject;
import formator.fxml.XDepObject;
import formator.spreadsheet.Csvgrapher;

import java.util.ArrayList;
import java.util.Map;

public class Formator {
    private MapObject mapObject;
    private Csvgrapher csvgrapher;

    public Formator(String[] depTypes, Map<Integer, String[]> entities,
                    Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> deps) {

        mapObject = new MapObject(depTypes);

        csvgrapher = new Csvgrapher();
        csvgrapher.buildProcess(entities, deps);
    }

    public XDepObject getfXmlDataModel() {
        XBuildObject xBuildObject = new XBuildObject();
        XDepObject xDepObject = xBuildObject.buildObjectProcess(mapObject);
        return xDepObject;
    }

    public JDepObject getfJsonDataModel() {
        JBuildObject jBuildObject = new JBuildObject();
        JDepObject jDepObject = jBuildObject.buildObjectProcess(mapObject);
        return jDepObject;

        //JsonWriter jasonFormat = new JsonWriter();
        //jasonFormat.toJson(jDepObject);

        //XmlWriter xmlFormat = new XmlWriter();
        //xmlFormat.toXml(xDepObject);
    }

    public ArrayList<String[]> getNodeModel() {
        return csvgrapher.getNodes();
    }

    public ArrayList<String[]> getEdgeModel() {
        return csvgrapher.getEdges();
    }


}
