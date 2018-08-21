package multiparser.py3extractor.pyentity;

import multiparser.entity.Entity;

import java.util.ArrayList;

public class ClassEntity extends Entity{

    public ClassEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private ArrayList<String> baseClassNameList = new ArrayList<String>();
    private ArrayList<Integer> baseClassIdList = new ArrayList<Integer>();

    public void addBaseClassName(String baseName) {
        baseClassNameList.add(baseName);
    }

    public void addBaseClassId(int baseId) {
        baseClassIdList.add(baseId);
    }
}
