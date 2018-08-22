package multiparser.py3extractor;

import multiparser.entity.FunctionEntity;
import multiparser.entity.LocalName;
import multiparser.entity.PackageEntity;
import multiparser.entity.VarEntity;
import multiparser.extractor.SingleCollect;
import multiparser.py3extractor.pyentity.*;

import java.util.ArrayList;

import static java.lang.System.exit;

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
     * process  method inside class: such as inst class, class method, class static method, abstract method...
     * @param methodDecorators: decorate,decorate,...,...
     * @param classId
     * @param functionName
     * @param paraStrs
     * @return
     */
    public int processMethod(String methodDecorators, int classId, String functionName, String paraStrs) {
        int functionId = singleCollect.getCurrentIndex();
        FunctionEntity functionEntity;
        if (methodDecorators.equals(ConstantString.NULL_STRING)) {
            functionEntity = new InstMethodEntity(functionId, functionName);
        }
        else if (methodDecorators.contains(ConstantString.CLASS_METHOD)) {
            functionEntity = new ClassMethodEntity(functionId, functionName);
        }
        else if (methodDecorators.contains(ConstantString.CLASS_STATIC_METHOD)) {
            functionEntity = new ClassStaticMethodEntity(functionId, functionName);
        }
        else { //instance method with other decorations
            functionEntity = new InstMethodEntity(functionId, functionName);
        }

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


    /**
     * process atom_expr: note, here atom_expr only include names but not string_literal , number_literal...
     * @param moduleId
     * @param classId
     * @param functionId
     * @param str
     */
    public void processAtomExpr(int moduleId, int classId, int functionId, String str, String usage) {
        //atom_expr is class variable: X
        if(classId != -1 && functionId == -1) {
            processClassVar(classId, str);
        }
        //atom_expr is a simple variable in global scope: x, __name__, __main__, x.y, x.y(),x/new()
        else if (classId == -1 && functionId == -1) {
            processGlobalOrLocalName(moduleId, str, usage);
        }
        //atom_expr is a simple variable in function scope: x, x.y, x.y(), x/new()
        else if (classId == -1 && functionId != -1) {
            processGlobalOrLocalName(functionId, str, usage);
        }
        //atom_expr is  variable in method scope: self.X, x, x.y, x.y(), x/new()
        else{ // classId != -1 && functionId != -1
            if(isInitMethod(functionId) && str.startsWith(ConstantString.SELF_DOT)) {
                processInstVar(classId, str);
            }
            else {  //regular a var inside a method
                processGlobalOrLocalName(functionId, str, usage);
            }

        }

        //atom_expr is functionCall: X(...) or new a class object
        //atom_expr is object's variable reference: x.y
        //atom_expr is object's method reference/call: x.y()
    }


    /**
     * process class variable
     * @param classId
     * @param str  a class variable
     */
    public void processClassVar(int classId, String str) {
        int varId = singleCollect.getCurrentIndex();
        //it should not be duplicated
        ClassVarEntity classVarEntity = new ClassVarEntity(varId, str);
        classVarEntity.setParentId(classId);
        singleCollect.getEntities().add(classVarEntity);

        singleCollect.getEntities().get(classId).addChildId(varId);
    }

    /**
     * process instance variable
     * @param classId
     * @param str a instance variable: self.x
     */
    public void processInstVar(int classId, String str) {
        //it should not be duplicated
        if(str.startsWith(ConstantString.SELF_DOT)) {
            String varName = str.substring(ConstantString.SELF_DOT.length(), str.length());
            int varId = singleCollect.getCurrentIndex();
            InstVarEntity instVarEntity = new InstVarEntity(varId, varName);
            instVarEntity.setParentId(classId);
            singleCollect.getEntities().add(instVarEntity);

            singleCollect.getEntities().get(classId).addChildId(varId);
        }
    }

    /**
     * process name in global scope(module) or local scope(function)
     * str is a simple variable in global scope: x, __name__, __main__, x.y, x.y(),x/new(), self.x
     * @param moduleOrFunctionId
     * @param str
     * @param usage
     */
    public void processGlobalOrLocalName(int moduleOrFunctionId, String str, String usage) {
        if(isStrAVar(str)) { // without (), without dot
            processLocOrGloVar(moduleOrFunctionId, str);
            processNameWithoutDot(moduleOrFunctionId,  str, usage);
        }
        else if(isStrACallee(str)) { //with ()
            processCallee(moduleOrFunctionId, str);
        }
        else if(isStrAObjectAttribute(str)) { //with dot, but without ()
            //because the name has dot: x.y, so x should be already appear in var in a separate way.
            processNameWithDot(moduleOrFunctionId,  str, usage);
        }
    }



    /**
     * judge the name with dot or not: X.Y but not X.Y()
     * @param str
     * @return
     */
    private boolean isStrAObjectAttribute(String str) {
        if(!str.contains(ConstantString.DOT)) {
            return false;
        }
        if(str.contains(ConstantString.LEFT_PARENTHESES)) {
            return false;
        }
        if(str.contains(ConstantString.RIGHT_PARENTHESES)) {
            return false;
        }
        return true;
    }


    /**
     * the name with dot.
     * it must be X.Y.  X should be already added into var, so just add X.Y into localName
     * @param parentId  moduleId or functionId
     * @param str
     */
    private void processNameWithDot(int parentId, String str, String usage) {
        LocalName localName = new LocalName(str, -1, "", "");
        localName.updateUsage(usage);
        //maybe duplicated.
        if (singleCollect.getEntities().get(parentId) instanceof ModuleEntity) {
            ((ModuleEntity) singleCollect.getEntities().get(parentId)).addLocalName(localName);
        }
        else if(singleCollect.getEntities().get(parentId) instanceof FunctionEntity) {
            ((FunctionEntity) singleCollect.getEntities().get(parentId)).addLocalName(localName);
        }
    }


    /** it is processed in the same way with processNameWithDot.
     * May be in the future, it is different,so we duplicate it.
     * the name without dot.
     * it must be Y.  Y should be already added into var.
     * @param parentId  moduleId or functionId
     * @param str
     */
    private void processNameWithoutDot(int parentId, String str, String usage) {
        LocalName localName = new LocalName(str, -1, "", "");
        localName.updateUsage(usage);
        //maybe duplicated.
        if (singleCollect.getEntities().get(parentId) instanceof ModuleEntity) {
            ((ModuleEntity) singleCollect.getEntities().get(parentId)).addLocalName(localName);
        }
        else if(singleCollect.getEntities().get(parentId) instanceof FunctionEntity) {
            ((FunctionEntity) singleCollect.getEntities().get(parentId)).addLocalName(localName);
        }
    }


    /** judge str is a simple var or not
     * var does not contain "." and "(" and ")".
     * @param str
     * @return
     */
    private boolean isStrAVar(String str) {
        if(str.contains(ConstantString.DOT)) {
            return false;
        }
        if(str.contains(ConstantString.LEFT_PARENTHESES) && str.contains(ConstantString.RIGHT_PARENTHESES)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param str   x.y()  or y()
     * @return
     */
    private boolean isStrACallee(String str) {
        if(str.contains(ConstantString.LEFT_PARENTHESES) && str.contains(ConstantString.RIGHT_PARENTHESES)) {
            return true;
        }
        return false;
    }

    /** process global var inside a module, or local var inside a function.
     * class var and instVar has been processed in single way, see processClassVar() and processInstVar()
     * new varEntity, and save
     * @param moduleOrFunctionId
     * @param str
     */
    private void processLocOrGloVar(int moduleOrFunctionId, String str) {
        int varId = singleCollect.getCurrentIndex();
        VarEntity varEntity = new VarEntity(varId, "", str);
        varEntity.setParentId(moduleOrFunctionId);
        singleCollect.getEntities().add(varEntity);

        singleCollect.getEntities().get(moduleOrFunctionId).addChildId(varId);
    }

    /**
     * just add to list (may be duplicated), process further int the future.
     * @param parentId moduleId or functionId
     * @param str
     */
    private void processCallee(int parentId, String str) {
        if(singleCollect.getEntities().get(parentId) instanceof ModuleEntity) {
            ((ModuleEntity) singleCollect.getEntities().get(parentId)).addFunctionCall(str);
        }
        else if (singleCollect.getEntities().get(parentId) instanceof FunctionEntity) {
            ((FunctionEntity) singleCollect.getEntities().get(parentId)).addCalledFunction(str);
        }
    }



}
