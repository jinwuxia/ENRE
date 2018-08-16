package multiparser.goextractor;

import java.util.ArrayList;

public class Signature {
    private String name;
    private ArrayList<String> inputTypes;
    private ArrayList<String> outputsTypes;


    public Signature(String name, ArrayList<String> inputTypes, ArrayList<String> outputsTypes) {
        this.name = name;
        this.inputTypes = inputTypes;
        this.outputsTypes = outputsTypes;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getInputTypes() {
        return inputTypes;
    }

    public ArrayList<String> getOutputsTypes() {
        return outputsTypes;
    }

    public boolean isEqual(Signature signature1) {
        if (!(name.equals(signature1.getName()))) {
            return false;
        }
        if (inputTypes.size() != signature1.getInputTypes().size()) {
            return false;
        }
        if (outputsTypes.size() != signature1.getOutputsTypes().size()) {
            return false;
        }
        for (int i = 0; i < inputTypes.size(); i++) {
            if(!(inputTypes.get(i).equals(signature1.getInputTypes().get(i)))) {
                return false;
            }
        }

        for (int i = 0; i < outputsTypes.size(); i++) {
            if(!(outputsTypes.get(i).equals(signature1.getOutputsTypes().get(i)))) {
                return false;
            }
        }
        return true;
    }

}
