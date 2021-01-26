package formator.fxml;

import formator.MapObject;
import util.Configure;

import java.util.ArrayList;
import java.util.Map;

public class XBuildObject {

    public XDepObject buildObjectProcess(MapObject mapObject) {
        Configure configure = Configure.getConfigureInstance();
        ArrayList<String> nodes = mapObject.getNodes();
        Map<Integer, Map<Integer, Map<String, Integer>>> finalRes = mapObject.getFinalRes();

        XNodes xNodes = new XNodes();
        xNodes.setNodes(nodes);

        ArrayList<XCell> xCellList = buildCellList(finalRes);

        XCells xCells = new XCells();
        xCells.setCells(xCellList);

        XDepObject xDepObject = new XDepObject();
        xDepObject.setName(configure.getAttributeName());
        xDepObject.setSchemaVersion(configure.getSchemaVersion());
        xDepObject.setVariables(xNodes);
        xDepObject.setCells(xCells);

        return xDepObject;
    }



    private ArrayList<XCell> buildCellList(Map<Integer, Map<Integer, Map<String, Integer>>> finalRes) {
        ArrayList<XCell> cellList = new ArrayList<XCell>();
        for (Map.Entry<Integer, Map<Integer, Map<String, Integer>>> entry1 : finalRes.entrySet()) {
            int src = entry1.getKey();

            Map<Integer, Map<String, Integer>> values1 = entry1.getValue();
            for (Map.Entry<Integer, Map<String, Integer>> entry2 : values1.entrySet()) {
                int dst = entry2.getKey();

                Map<String, Integer> values2 = entry2.getValue();
                ArrayList<XDepend> xDepends = buildDependList(values2);
                XCell xCell = new XCell();
                xCell.setSrc(src);
                xCell.setDest(dst);
                xCell.setDepends(xDepends);
                cellList.add(xCell);
            }
        } //end for
        return cellList;
    }



    private ArrayList<XDepend> buildDependList(Map<String, Integer> values2) {
        ArrayList<XDepend> dependList = new ArrayList<XDepend>();

        for (Map.Entry<String, Integer> entry3 : values2.entrySet()) {
            String depType = entry3.getKey();
            float weight = (float) entry3.getValue();
            XDepend xDepend = new XDepend();
            xDepend.setWeight(weight);
            xDepend.setName(depType);
            dependList.add(xDepend);
        } //end for
        return dependList;
    }

}
