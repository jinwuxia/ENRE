package multiparser.py3extractor;

import multiparser.entity.FunctionEntity;
import multiparser.entity.PackageEntity;
import multiparser.entity.VarEntity;
import multiparser.extractor.SingleCollect;
import multiparser.py3extractor.pyentity.*;

import java.util.ArrayList;

public class PyProcessTask {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    /**
     * process the directory as a package,
     * and save into pakckageEntity
     *
     * its parent is a package or none. after finishing all packages, we should set the parentId for each package
     * save into singlecollection.entities.
     * @param fileName = "../../__init__.py"
     * @return packageId
     */
    public int processPackage(String fileName) {
        String [] tmp = fileName.split("/");
        String dirStr = tmp[0];
        for (int i = 1; i < tmp.length -1; i++) {
            dirStr += "/";
            dirStr += tmp[i];
        }
        String packageName = tmp[tmp.length - 2];

        // new packageEntity
        int packageId = singleCollect.getCurrentIndex();
        PackageEntity packageEntity = new PackageEntity(packageId, dirStr, packageName);
        singleCollect.addEntity(packageEntity);

        //set parent and child

        return packageId;
    }


    /**
     * process the file as a module,
     * and save into moduleEntity.
     * its parent is a package or none. after finishing all files, we should set the parentId for each module
     * save into singlecollection.entities.
     * @param fileName
     * @return moduleId
     */
    public int processModule(String fileName) {
        String[] tmpArr = fileName.split("/");
        String moduleSimpleName = tmpArr[tmpArr.length - 1];

        int moduleId = singleCollect.getCurrentIndex();
        ModuleEntity moduleEntity = new ModuleEntity(moduleId, fileName);
        moduleEntity.setModuleSimpleName(moduleSimpleName);
        singleCollect.addEntity(moduleEntity);

        //set parent and child

        return moduleId;
    }


    /**
     * save classEntity
     * @param parentId:  moduleId or the nested blockId
     * @param className
     * @param baseStrs
     * @return
     */
    public int processClass(int parentId, String className, String baseStrs) {
        int classId = singleCollect.getCurrentIndex();
        ClassEntity classEntity = new ClassEntity(classId, className);
        classEntity.setParentId(parentId);

        //add original baseName
        if(!baseStrs.equals(ConstantString.NULL_STRING)) {
            String [] baseArr = baseStrs.split(ConstantString.COMMA);
            for (String baseName : baseArr) {
                classEntity.addBaseClassName(baseName);
            }
        }
        singleCollect.addEntity(classEntity);
        singleCollect.getEntities().get(parentId).addChildId(classId);

        return classId;
    }

    /**
     * process top-level functionEntity
     * @param moduleId
     * @param functionName
     * @param paraStrs
     * @return functionId
     */
    public int processFunction(int moduleId, String functionName, String paraStrs) {
        int functionId = singleCollect.getCurrentIndex();
        FunctionEntity functionEntity = new FunctionEntity(functionId, functionName);
        functionEntity.setParentId(moduleId);
        singleCollect.addEntity(functionEntity);
        singleCollect.getEntities().get(moduleId).addChildId(functionId);

        processParas(functionId, paraStrs);

        return functionId;
    }


    /**
     * process class method or class static method
     * @param methodDecorators: decorate,decorate,...,...
     * @param classId
     * @param functionName
     * @param paraStrs
     * @return
     */
    public int processClassMethod(String methodDecorators, int classId, String functionName, String paraStrs) {
        int functionId = singleCollect.getCurrentIndex();
        if(methodDecorators.indexOf(ConstantString.CLASS_STATIC_METHOD) != -1) {
            ClassStaticMethodEntity functionEntity = new ClassStaticMethodEntity(functionId, functionName);
            functionEntity.setParentId(classId);
            singleCollect.addEntity(functionEntity);
        }
        else if(methodDecorators.indexOf(ConstantString.CLASS_METHOD) != -1) {
            ClassMethodEntity functionEntity = new ClassMethodEntity(functionId, functionName);
            functionEntity.setParentId(classId);
            singleCollect.addEntity(functionEntity);
        }
        singleCollect.getEntities().get(classId).addChildId(functionId);

        processParas(functionId, paraStrs);
        return functionId;
    }

    /**
     * process class instance's method
     * @param classId
     * @param functionName
     * @param paraStrs
     * @return
     */
    public int processInstMethod(int classId, String functionName, String paraStrs) {
        int functionId = singleCollect.getCurrentIndex();
        InstMethodEntity functionEntity = new InstMethodEntity(functionId, functionName);
        functionEntity.setParentId(classId);
        singleCollect.addEntity(functionEntity);
        singleCollect.getEntities().get(classId).addChildId(functionId);

        processParas(functionId, paraStrs);

        return functionId;
    }


    /**
     * process  function's parameters,
     * @param paraStrs = (arg1,arg2)
     * @return
     */
    private void processParas(int functionId, String paraStrs) {
        //save parameter entities (parameter only has a name without type information)
        ArrayList<VarEntity> paraVars = extractParas(paraStrs);
        for(VarEntity paraVarEntity : paraVars) {
            int paraId = singleCollect.getCurrentIndex();
            paraVarEntity.setId(paraId);
            singleCollect.addEntity(paraVarEntity); // its parent id is not the functionID.

            // set parameters
            ( (FunctionEntity) singleCollect.getEntities().get(functionId)).addParameter(paraId);
        }
    }

    /**
     *
     * @param paraStrs
     * @return
     */
    private ArrayList<VarEntity> extractParas(String paraStrs) {
        ArrayList<VarEntity> varEntities = new ArrayList<VarEntity>();
        if(paraStrs.startsWith(ConstantString.LEFT_PARENTHESES)
                && paraStrs.endsWith(ConstantString.RIGHT_PARENTHESES)) {
            paraStrs = paraStrs.substring(1, paraStrs.length() - 1);
        }
        if(paraStrs.equals(ConstantString.NULL_STRING)) {
            return varEntities;
        }
        String [] paraArr = paraStrs.split(ConstantString.COMMA);
        for (String para : paraArr) {
            VarEntity varEntity = new VarEntity();
            varEntity.setName(para);
            varEntities.add(varEntity);
        }
        return varEntities;
    }


    /**
     * judge the function with function id is init method or not
     * @param functionId
     * @return
     */
    public boolean isInitMethod(int functionId) {
        if (functionId == -1) {
            return false;
        }
        if(singleCollect.getEntities().get(functionId).getName().equals(ConstantString.INIT_METHOD_NAME)) {
            return true;
        }
        return false;
    }

}
