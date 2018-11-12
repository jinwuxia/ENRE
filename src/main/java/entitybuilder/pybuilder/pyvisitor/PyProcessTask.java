package entitybuilder.pybuilder.pyvisitor;

import uerr.*;
import uerr.SingleCollect;
import entitybuilder.pybuilder.PyConstantString;
import entitybuilder.pybuilder.pyentity.*;
import util.Configure;
import util.OsUtil;
import util.StringUtil;

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
        String dirStr = StringUtil.deleteLastStrByPathDelimiter(fileName);
        String packageName = StringUtil.getLastStrByPathDelimiter(dirStr);

        // new packageEntity
        int packageId = singleCollect.getCurrentIndex();
        AbsFLDEntity packageEntity = new AbsFLDEntity(packageId, dirStr, packageName);
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
        String onlyFileName = StringUtil.getLastStrByPathDelimiter(fileName);
        String moduleSimpleName = onlyFileName.split(PyConstantString.DOT_PY)[0];

        int moduleId = singleCollect.getCurrentIndex();
        ModuleEntity moduleEntity = new ModuleEntity(moduleId, fileName);
        moduleEntity.setModuleSimpleName(moduleSimpleName);
        singleCollect.addEntity(moduleEntity);

        //set parent and child
        //System.out.println("modulename:" + fileName);
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
        if(!baseStrs.equals(Configure.NULL_STRING)) {
            String [] baseArr = baseStrs.split(Configure.COMMA);
            for (String baseName : baseArr) {
                classEntity.addBaseClassName(baseName);
            }
        }
        singleCollect.addEntity(classEntity);
        singleCollect.getEntities().get(parentId).addChildId(classId);

        return classId;
    }

    /**
     * if __init__ is not explicitly defined inside class,
     * we should added this init method to class's child
     * @param classId
     */
    public void supplementInitMethod(int classId) {
        if(!classHasExplicitInitMethod(classId)) {
            int methodId = singleCollect.getCurrentIndex();
            InstMethodEntity methodEntity = new InstMethodEntity(methodId, PyConstantString.INIT_METHOD_NAME);
            methodEntity.setParentId(classId);
            singleCollect.addEntity(methodEntity);
            singleCollect.getEntities().get(classId).addChildId(methodId);
        }
    }


    /**
     * check if __init__ is explicitly defined inside class or not
     * @param classId
     * @return
     */
    private boolean classHasExplicitInitMethod(int classId) {
        for(int childId : singleCollect.getEntities().get(classId).getChildrenIds()) {
            if(singleCollect.getEntities().get(childId) instanceof InstMethodEntity) {
                if(singleCollect.getEntities().get(childId).getName().equals(PyConstantString.INIT_METHOD_NAME)) {
                    return true;
                }
            }
        }
        return false;
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
        PyFunctionEntity functionEntity = new PyFunctionEntity(functionId, functionName);
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
        PyFunctionEntity functionEntity;
        if (methodDecorators.equals(Configure.NULL_STRING)) {
            functionEntity = new InstMethodEntity(functionId, functionName);
        }
        else if (methodDecorators.contains(PyConstantString.CLASS_METHOD)) {
            functionEntity = new ClassMethodEntity(functionId, functionName);
        }
        else if (methodDecorators.contains(PyConstantString.CLASS_STATIC_METHOD)) {
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
        ArrayList<AbsVAREntity> paraVars = extractParas(paraStrs);
        for(AbsVAREntity paraVarEntity : paraVars) {
            int paraId = singleCollect.getCurrentIndex();
            paraVarEntity.setId(paraId);
            singleCollect.addEntity(paraVarEntity); // its parent id is not the functionID.

            // set parameters
            ( (PyFunctionEntity) singleCollect.getEntities().get(functionId)).addParameter(paraId);
        }
    }

    /**
     *
     * @param paraStrs
     * @return
     */
    private ArrayList<AbsVAREntity> extractParas(String paraStrs) {
        ArrayList<AbsVAREntity> varEntities = new ArrayList<AbsVAREntity>();
        if(paraStrs.startsWith(Configure.LEFT_PARENTHESES)
                && paraStrs.endsWith(Configure.RIGHT_PARENTHESES)) {
            paraStrs = paraStrs.substring(1, paraStrs.length() - 1);
        }
        if(paraStrs.equals(Configure.NULL_STRING)) {
            return varEntities;
        }
        String [] paraArr = paraStrs.split(Configure.COMMA);
        for (String para : paraArr) {
            AbsVAREntity varEntity = new AbsVAREntity();
            varEntity.setName(para);
            varEntity.setSimpleName();
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
        if(singleCollect.getEntities().get(functionId).getName().equals(PyConstantString.INIT_METHOD_NAME)) {
            return true;
        }
        return false;
    }


    /**
     * save right value to the existed left var
     * @param value
     * @param leftId
     */
    public void processRightAssignValue(String value, int leftId) {
        ((AbsVAREntity) singleCollect.getEntities().get(leftId)).setValue(value);
        //System.out.println("left: " + singleCollect.getEntities().get(leftId).getName() + "; right: " + value);
    }


    /**
     * process atom_expr: note, here atom_expr only include names but not string_literal , number_literal...
     * only name appears in left assign, it may be a var
     *
     * @param isLeftAssign
     * @param moduleId
     * @param classId
     * @param functionId
     * @param str
     * @param usage
     * return varID, or -1(if localName, not var)
     */
    public int processAtomExpr(boolean isLeftAssign, int moduleId, int classId, int functionId, String str, String usage) {
        int resId = -1;
        int parentId = moduleId;
        if(functionId != -1) {
            parentId = functionId;
        }
        //only appear in left assign, it may be a new varibaleEntity
        if(isLeftAssign) {
            //System.out.println(classId + "," + functionId + ","+ str);
            if(classId != -1 && functionId == -1) {
                //atom_expr is class variable: X
                resId = processClassVar(classId, str);
            }
            else if(isInitMethod(functionId) && str.startsWith(PyConstantString.SELF_DOT) && !isStrACallee(str)) {
                //atom_expr is a instance variable: self.x, exclude self.y().
                resId = processInstVar(classId, str);
            }
            else {
                if(isStrAVar(str)) { //atom_expr is a local or global variable: x
                    resId = processLocOrGloVar(parentId, str);
                }
                //it a local Name or global Name, save into Name
                processLocOrGloName(parentId, str, usage);
            }
        }
        //it a local Name or global Name:  self.X, x, x.y, x.y(), x/new()
        else {
            processLocOrGloName(parentId, str, usage);
        }
        return resId;
    }

    /**
     * process class variable
     * @param classId
     * @param str  a class variable
     */
    private int processClassVar(int classId, String str) {
        int varId = singleCollect.getCurrentIndex();
        //it should not be duplicated
        ClassVarEntity classVarEntity = new ClassVarEntity(varId, str);
        classVarEntity.setParentId(classId);
        singleCollect.getEntities().add(classVarEntity);

        singleCollect.getEntities().get(classId).addChildId(varId);
        return varId;
    }

    /**
     * process instance variable
     * @param classId
     * @param str a instance variable: self.x
     */
    private int processInstVar(int classId, String str) {
        //it should not be duplicated
        String varName = str.substring(PyConstantString.SELF_DOT.length(), str.length());
        int varId = singleCollect.getCurrentIndex();
        InstVarEntity instVarEntity = new InstVarEntity(varId, varName);
        instVarEntity.setParentId(classId);
        singleCollect.getEntities().add(instVarEntity);
        singleCollect.getEntities().get(classId).addChildId(varId);

        return varId;
    }

    /**
     * judge the str (x) is already a global var or not?
     * @param str
     * @param moduleId
     * @return
     */
    private int findRepeatedGlobalVar(String str, int moduleId) {
        if(moduleId == -1) {
            return -1;
        }
        if(!(singleCollect.getEntities().get(moduleId) instanceof ModuleEntity)) {
            return -1;
        }
        for (int childId : singleCollect.getEntities().get(moduleId).getChildrenIds()) {
            if(singleCollect.getEntities().get(childId) instanceof AbsVAREntity) {
                if(singleCollect.getEntities().get(childId).getName().equals(str)) {
                    return childId;
                }
            }
        }
        return -1;
    }

    /**
     * judge the str (x) is already a local var in function/method or not?
     * @param str
     * @param functionId
     * @return
     */
    private int findRepeatedLocalVar(String str, int functionId) {
        if(functionId == -1) {
            return -1;
        }
        if(!(singleCollect.getEntities().get(functionId) instanceof PyFunctionEntity)) {
            return -1;
        }
        for (int childId : singleCollect.getEntities().get(functionId).getChildrenIds()) {
            if(singleCollect.getEntities().get(childId) instanceof AbsVAREntity) {
                if(singleCollect.getEntities().get(childId).getName().equals(str)) {
                    return childId;
                }
            }
        }
        return -1;
    }


    /**
     * judge the str (x) is already a parameter var in function/method or not?
     * @param str
     * @param functionId
     * @return
     */
    private int findParameterVar(String str, int functionId) {
        if(functionId == -1) {
            return -1;
        }
        if(!(singleCollect.getEntities().get(functionId) instanceof PyFunctionEntity)) {
            return -1;
        }
        for (int parameterId : ((PyFunctionEntity) singleCollect.getEntities().get(functionId)).getParameters()) {
            if(singleCollect.getEntities().get(parameterId).getName().equals(str)) {
                return parameterId;
            }
        }
        return -1;
    }

    /** process global var inside a module, or local var inside a function.
     * class var and instVar has been processed in single way, see processClassVar() and processInstVar()
     * new varEntity, and save
     * @param moduleOrFunctionId
     * @param str
     */
    private int processLocOrGloVar(int moduleOrFunctionId, String str) {
        int varId = -1;
        varId = findRepeatedGlobalVar(str, moduleOrFunctionId);
        if(varId != -1) {
            return varId;
        }
        varId = findRepeatedLocalVar(str, moduleOrFunctionId);
        if(varId != -1) {
            return varId;
        }
        varId = findParameterVar(str, moduleOrFunctionId);
        if(varId != -1){
            return varId;
        }

        varId = singleCollect.getCurrentIndex();
        AbsVAREntity varEntity = new AbsVAREntity(varId, "", str);
        varEntity.setParentId(moduleOrFunctionId);
        singleCollect.getEntities().add(varEntity);

        singleCollect.getEntities().get(moduleOrFunctionId).addChildId(varId);
        return varId;
    }


    /**
     * process name in global scope(module) or local scope(function)
     * str is a simple variable in global scope: x, __name__, __main__, x.y, x.y(),x/new(), self.x
     * @param moduleOrFunctionId
     * @param str
     * @param usage
     */
    private int processLocOrGloName(int moduleOrFunctionId, String str, String usage) {
        int resId = -1;
        if(isStrAVar(str)) { // without (), without dot
            resId = processNameWithoutDot(moduleOrFunctionId,  str, usage);
        }
        else if(isStrACallee(str)) { //such as x.y(), y(), self.y()
            resId = processCallee(moduleOrFunctionId, str);
        }
        else if(isStrAObjectAttribute(str)) { //with dot, but without (). such as x.y
            //because the name has dot: x.y,
            // so x should be already appear in var in a separate way.
            // or, x is a imported name.
            resId = processNameWithDot(moduleOrFunctionId, str, usage);
        }
        return resId;
    }



    /**
     * judge the name with dot or not: X.Y but not X.Y()
     * @param str
     * @return
     */
    private boolean isStrAObjectAttribute(String str) {
        if(!str.contains(Configure.DOT)) {
            return false;
        }
        if(str.contains(Configure.LEFT_PARENTHESES)) {
            return false;
        }
        if(str.contains(Configure.RIGHT_PARENTHESES)) {
            return false;
        }
        return true;
    }


    /**
     * the name with dot.
     * it must be X.Y.  X should be already added into var or imported name,
     * so  add X.y into localName
     * @param parentId  moduleId or functionId
     * @param str
     */
    private int processNameWithDot(int parentId, String str, String usage) {
        int nameIndex = -1;

        if(singleCollect.getEntities().get(parentId) instanceof ModuleEntity) {
            nameIndex = processNameInModule(parentId, str, usage);
        }
        else if (singleCollect.getEntities().get(parentId) instanceof PyFunctionEntity) {
            nameIndex = processNameInFunction(parentId, str, usage);
        }
        return nameIndex;
    }


    /** it is processed in the same way with processNameWithDot.
     * May be in the future, it is different,so we duplicate it.
     * the name without dot.
     * it must be Y.  Y should be already added into var.
     * @param parentId  moduleId or functionId
     * @param str
     */
    private int processNameWithoutDot(int parentId, String str, String usage) {
        int nameIndex = -1;
        //maybe duplicated, check if exist.
        if(str.equals(PyConstantString.SELF)) {  //if local name = self, do not process, then return.
            return nameIndex;
        }

        if(singleCollect.getEntities().get(parentId) instanceof ModuleEntity) {
            nameIndex = processNameInModule(parentId, str, usage);
        }
        else if (singleCollect.getEntities().get(parentId) instanceof PyFunctionEntity) {
            nameIndex = processNameInFunction(parentId, str, usage);
        }
        return nameIndex;

    }


    /** process name "x" or "x.y " or "x.y.z" in the module
     *
     * @param moduleId
     * @param name
     * @param usage
     */
    private int processNameInModule(int moduleId, String name, String usage) {
        int localNameIndex = getLocalNameId(moduleId, name);
        if(localNameIndex != -1) { //exist
            ((ModuleEntity) singleCollect.getEntities().get(moduleId)).getLocalNames().get(localNameIndex).updateUsage(usage);
            ((ModuleEntity) singleCollect.getEntities().get(moduleId)).getLocalNames().get(localNameIndex).updateWeighedUsage(usage);
        }
        else { //not exist
            localNameIndex = ((ModuleEntity) singleCollect.getEntities().get(moduleId)).getLocalNames().size();
            LocalName localName = new LocalName(name, -1, "", "");
            localName.updateWeighedUsage(usage);
            localName.updateUsage(usage);
            ((ModuleEntity) singleCollect.getEntities().get(moduleId)).addLocalName(localName);

        }
        return localNameIndex;
    }


    /**
     * process name "x" in the function
     * @param functionId
     * @param name
     * @param usage
     */
    private int processNameInFunction(int functionId, String name, String usage) {
        int localNameIndex = getLocalNameId(functionId, name);
        if(localNameIndex != -1) { //exist
            ((PyFunctionEntity) singleCollect.getEntities().get(functionId)).getLocalNames().get(localNameIndex).updateUsage(usage);
            ((PyFunctionEntity) singleCollect.getEntities().get(functionId)).getLocalNames().get(localNameIndex).updateWeighedUsage(usage);
        }
        else { //not exist
            localNameIndex = ((PyFunctionEntity) singleCollect.getEntities().get(functionId)).getLocalNames().size();
            LocalName localName = new LocalName(name, -1, "", "");
            localName.updateUsage(usage);
            localName.updateWeighedUsage(usage);
            ((PyFunctionEntity) singleCollect.getEntities().get(functionId)).addLocalName(localName);
        }
        return localNameIndex;
    }

    /**
     * get localname index in funciton or module
     * @param parentId
     * @param name
     * @return
     */
    private int getLocalNameId(int parentId, String name) {
        AbsEntity entity = singleCollect.getEntities().get(parentId);
        ArrayList<LocalName> localNames = null;

        if(entity instanceof ModuleEntity) {
            localNames = ((ModuleEntity) entity).getLocalNames();
        }
        else if(entity instanceof PyFunctionEntity) {
            localNames = ((PyFunctionEntity) entity).getLocalNames();
        }
        if(localNames == null) {
            return -1;
        }

        for (int index = 0; index < localNames.size(); index ++) {
            LocalName localName = localNames.get(index);
            if(localName.getName().equals(name)) {
                return index;
            }
        }
        return -1;
    }

    /** judge str is a simple var or not
     * var does not contain "." and "(" and ")".
     * @param str
     * @return
     */
    private boolean isStrAVar(String str) {
        if(str.contains(Configure.DOT)) {
            return false;
        }
        if(str.contains(Configure.LEFT_PARENTHESES) && str.contains(Configure.RIGHT_PARENTHESES)) {
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
        if(str.contains(Configure.LEFT_PARENTHESES) && str.contains(Configure.RIGHT_PARENTHESES)) {
            return true;
        }
        return false;
    }



    /**
     *
     * @param parentId moduleId or functionId
     * @param str  callee fun form with parameter
     */
    private int processCallee(int parentId, String str) {
        int nameIndex = -1;
        if(singleCollect.getEntities().get(parentId) instanceof ModuleEntity) {
            nameIndex = ((ModuleEntity) singleCollect.getEntities().get(parentId)).getCalledFunctions().size();
            ((ModuleEntity) singleCollect.getEntities().get(parentId)).addFunctionCall(str);
            //((ModuleEntity) singleCollect.getEntities().get(parentId)).updateCalledWeightedFunction(str);
        }
        else if (singleCollect.getEntities().get(parentId) instanceof PyFunctionEntity) {
            nameIndex = ((PyFunctionEntity) singleCollect.getEntities().get(parentId)).getCalledFunctions().size();
            ((PyFunctionEntity) singleCollect.getEntities().get(parentId)).addCalledFunction(str);
            //((AbsFUNEntity) singleCollect.getEntities().get(parentId)).updateCalledWeightedFunction(str);
        }
        return nameIndex;
    }



    /**
     *
     * @param from
     * @param importStr
     * @param functionOrModuleId
     */
    public void processFromImport(String from, String importStr, int functionOrModuleId) {
        if(importStr.equals(Configure.NULL_STRING)) {
            return;
        }
        ArrayList<ImportStmt> importStmts = new ArrayList<ImportStmt>();
        String [] arr = importStr.split(Configure.COMMA);
        for (String str : arr) {
            String [] arr1 = str.split(Configure.SEMICOLON);
            String impor = arr1[0];
            String as = "";
            if(arr1.length > 1) {
                as = arr1[1];
            }
            ImportStmt importStmt = new ImportStmt(from, impor, as);
            importStmts.add(importStmt);
        }
        saveImportsInFuncOrModule(importStmts, functionOrModuleId);
    }



    /**process import string
     * impport str or imprt str1 as str2
     * x1;y1, x2, x3, x4;y4
     * @param importStr
     * @param functionOrMoudleId
     */
    public void processImportName(String importStr, int functionOrMoudleId) {
        if(importStr.equals(Configure.NULL_STRING)) {
            return;
        }

        ArrayList<ImportStmt> importStmts = new ArrayList<ImportStmt>();
        String from = "";
        String [] arr = importStr.split(Configure.COMMA);
        for (String str : arr) {
            String [] arr1 = str.split(Configure.SEMICOLON);
            String impor = arr1[0];
            String as = "";
            if(arr1.length > 1) {
                as = arr1[1];
            }
            ImportStmt importStmt = new ImportStmt(from, impor, as);
            importStmts.add(importStmt);
        }
        saveImportsInFuncOrModule(importStmts, functionOrMoudleId);
    }


    /**
     * save import list into  module uerr or function uerr.
     * @param importStmts
     * @param functionOrModuleId
     */
    private void saveImportsInFuncOrModule(ArrayList<ImportStmt> importStmts, int functionOrModuleId) {
        if(functionOrModuleId != -1) {
            if(singleCollect.getEntities().get(functionOrModuleId) instanceof PyFunctionEntity) {
                ((PyFunctionEntity) singleCollect.getEntities().get(functionOrModuleId)).addImportStmts(importStmts);
            }
            else if(singleCollect.getEntities().get(functionOrModuleId) instanceof ModuleEntity) {
                ((ModuleEntity) singleCollect.getEntities().get(functionOrModuleId)).addImportStmts(importStmts);
            }
        }
    }


}
