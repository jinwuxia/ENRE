package multiparser.py3extractor.pyentity;

import multiparser.entity.FileEntity;

public class ModuleEntity extends FileEntity{
    private String moduleSimpleName; // without path, a simple name

    public ModuleEntity(int moduleId, String name) {
        this.id = moduleId;
        this.name = name;
    }

    public void setModuleSimpleName(String moduleSimpleName) {
        this.moduleSimpleName = moduleSimpleName;
    }

    public String getModuleSimpleName() {
        return moduleSimpleName;
    }
}
