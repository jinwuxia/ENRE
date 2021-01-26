package formator.fxml;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class XNodes {

    private ArrayList<String> nodes;

    public ArrayList<String> getNodes() {
        return nodes;
    }

    @XmlElement(name = "variable")
    public void setNodes(ArrayList<String> nodes) {
        this.nodes = nodes;
    }
}
