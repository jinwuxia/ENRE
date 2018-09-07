package multiparser.goextractor;

import multiparser.extractor.RelationInterface;
import multiparser.util.Tuple;

import java.util.ArrayList;

public class GoRelationInf extends RelationInterface {
    @Override
    public String basicStatis() {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionCalls(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionParas(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionRets(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionSets(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionUses(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImplementDeps(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getInheritDeps(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImportDeps(String level) {
        return null;
    }
}
