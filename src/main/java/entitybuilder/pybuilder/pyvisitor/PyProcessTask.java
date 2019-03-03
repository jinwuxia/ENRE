package entitybuilder.pybuilder.pyvisitor;

import expression.Expression;
import expression.ExpressionCollect;
import expression.ExpressionContainer;
import uerr.*;
import uerr.SingleCollect;
import entitybuilder.pybuilder.PyConstantString;
import entitybuilder.pybuilder.pyentity.*;
import util.Configure;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PyProcessTask {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();

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
        singleCollect.getEntityById(parentId).addChildId(classId);

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
            singleCollect.getEntityById(classId).addChildId(methodId);
        }
    }


    /**
     * check if __init__ is explicitly defined inside class or not
     * @param classId
     * @return
     */
    private boolean classHasExplicitInitMethod(int classId) {
        for(int childId : singleCollect.getEntityById(classId).getChildrenIds()) {
            if(singleCollect.getEntityById(childId) instanceof InstMethodEntity) {
                if(singleCollect.getEntityById(childId).getName().equals(PyConstantString.INIT_METHOD_NAME)) {
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
        singleCollect.getEntityById(moduleId).addChildId(functionId);

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
        singleCollect.getEntityById(classId).addChildId(functionId);

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
            ( (PyFunctionEntity) singleCollect.getEntityById(functionId)).addParameter(paraId);
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
        if(singleCollect.getEntityById(functionId).getName().equals(PyConstantString.INIT_METHOD_NAME)) {
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
        ((AbsVAREntity) singleCollect.getEntityById(leftId)).setValue(value);
        //System.out.println("left: " + singleCollect.getEntityById(leftId).getName() + "; right: " + value);
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
     * @param location
     * return varID, or -1(if localName, not var)
     */
    public int processAtomExpr(boolean isLeftAssign, int moduleId, int classId, int functionId, String str, String location) {
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
                processLocOrGloName(parentId, str, location);
            }
        }
        //it a local Name or global Name:  self.X, x, x.y, x.y(), x/new()
        else {
            processLocOrGloName(parentId, str, location);
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

        singleCollect.getEntityById(classId).addChildId(varId);
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
        singleCollect.getEntityById(classId).addChildId(varId);

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
        if(!(singleCollect.getEntityById(moduleId) instanceof ModuleEntity)) {
            return -1;
        }
        for (int childId : singleCollect.getEntityById(moduleId).getChildrenIds()) {
            if(singleCollect.getEntityById(childId) instanceof AbsVAREntity) {
                if(singleCollect.getEntityById(childId).getName().equals(str)) {
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
        if(!(singleCollect.getEntityById(functionId) instanceof PyFunctionEntity)) {
            return -1;
        }
        for (int childId : singleCollect.getEntityById(functionId).getChildrenIds()) {
            if(singleCollect.getEntityById(childId) instanceof AbsVAREntity) {
                if(singleCollect.getEntityById(childId).getName().equals(str)) {
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
        if(!(singleCollect.getEntityById(functionId) instanceof PyFunctionEntity)) {
            return -1;
        }
        for (int parameterId : ((PyFunctionEntity) singleCollect.getEntityById(functionId)).getParameters()) {
            if(singleCollect.getEntityById(parameterId).getName().equals(str)) {
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

        singleCollect.getEntityById(moduleOrFunctionId).addChildId(varId);
        return varId;
    }


    /**
     * process name in global scope(module) or local scope(function)
     * str is a simple variable in global scope: x, __name__, __main__, x.y, x.y(),x/new(), self.x
     * @param parentId  the module or funtionid
     * @param str
     * @param usage
     */
    private void processLocOrGloName(int parentId, String str, String usage) {
        //get the expression container for current module/function
        AbsEntity entity =  singleCollect.getEntityById(parentId);
        int expContainerId = entity.getExpContainerId();
        if (expContainerId == -1) {
            expContainerId = expressionCollect.getCurrentIndex();
            expressionCollect.addContainer(new ExpressionContainer(parentId, expContainerId));
            singleCollect.getEntityById(parentId).setExpContainerId(expContainerId);
        }

        int expIndex = expressionCollect.getContainerById(expContainerId).getExprByName(str);
        //System.out.println("raw expression: "+ str);
        if(-1 == expIndex) {
            String aliasStr = simplifyStr(expContainerId, str);
            //System.out.println("after: " + aliasStr);
            expressionCollect.getContainerById(expContainerId).addExpression(new Expression(str,aliasStr, usage, 1));
        }
        else {
            int oldFreq = expressionCollect.getContainerById(expContainerId).getExpressionList().get(expIndex).getFreq();
            expressionCollect.getContainerById(expContainerId).getExpressionList().get(expIndex).setFreq(oldFreq + 1);
        }




    }

    /**
     * make parameter not including ( and ".".
     * so we can split by . in the expression extension
     * @param str
     * @return
     */
    private String simplifyStr(int expContainerId, String str) {
        List<Expression> expressions = expressionCollect.getContainerById(expContainerId).getExpressionList();

        int index = expressions.size() -1;
        while(isComplex(str) && index >= 0) {
            String substr = expressions.get(index).getRawStr();

            int startIndex = str.indexOf(substr);
            if(startIndex == 0) {
                startIndex = str.lastIndexOf(substr);
            }
            while (startIndex != -1 && startIndex != 0
                    && (substr.contains(".") || substr.contains("("))
                    && !str.equals(substr)) {
                //System.out.println("substr " + substr);
                int endIndex = startIndex + substr.length();
                str = str.substring(0, startIndex) + Integer.toString(index) + str.substring(endIndex);

                startIndex = str.indexOf(substr);
                if(startIndex == 0) {
                    startIndex = str.lastIndexOf(substr);
                }
            }
            index--;
            //System.out.println("substr " + substr+", str " + str + ", index " + Integer.toString(index));
        }

        return str;
    }

    /**
     * contain () and dot
     * @param str
     * @return
     */
    private boolean isComplex(String str) {
        int indexOfLeftBrace = str.indexOf("(");
        int indexOfDot = str.indexOf(".");
        int indexOfRightBrace = str.indexOf(")");
        if (indexOfLeftBrace != -1 && indexOfRightBrace != -1 && indexOfDot != -1 ) {
            if (dotBetween(str))
            return true;
        }
        if(countAppearNumber(str, "(") > 1 && countAppearNumber(str, ")") > 1) {
            return true;
        }
        return false;

    }
    private boolean dotBetween(String str) {
        int indexOflBrace = str.indexOf("(");
        int indexOfrBrace = str.indexOf(")");
        str  = str.substring(indexOflBrace, indexOfrBrace);
        if (str.contains(".")) {
            return true;
        }
        return false;

    }

    private int countAppearNumber(String str, String subStr) {
        int count = 0;
        int start = 0;
        while ((start = str.indexOf(subStr, start)) != -1) {
            start = start + subStr.length();
            count++;
        }
        return count;
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
            if(singleCollect.getEntityById(functionOrModuleId) instanceof PyFunctionEntity) {
                ((PyFunctionEntity) singleCollect.getEntityById(functionOrModuleId)).addImportStmts(importStmts);
            }
            else if(singleCollect.getEntityById(functionOrModuleId) instanceof ModuleEntity) {
                ((ModuleEntity) singleCollect.getEntityById(functionOrModuleId)).addImportStmts(importStmts);
            }
        }
    }


}
