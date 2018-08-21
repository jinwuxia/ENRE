package multiparser.py3extractor.pyentity;

import multiparser.entity.FunctionEntity;

public class ClassMethodEntity extends FunctionEntity{

    public ClassMethodEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
