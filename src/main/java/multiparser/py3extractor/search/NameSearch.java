package multiparser.py3extractor.search;

import multiparser.entity.Entity;
import multiparser.extractor.SingleCollect;
import multiparser.py3extractor.ConstantString;
import multiparser.py3extractor.pyentity.*;
import multiparser.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * build scopeMap for each entity
 *  in each scope (module, class, function, method), record the visible name and its binding entity
 *
 *  Module: children, imported name.
 *  Function: children, parameter, located module's visible name, imported name.
 *  Class: children, "self", BaseClass full name,
 *         BaseClass's chilren (from left to right, depth first, no-diplicated),
 *         located module's visible name.
 *  Method: children, parameter, self, BaseClass full name, located module's visible name, imported name.
 */
public class NameSearch {

    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    //(scopeId, (name, nameEntityId))
    private HashMap<Integer, HashMap<String, Integer>> nameMap = new HashMap<Integer, HashMap<String, Integer>>();

    public HashMap<Integer, HashMap<String, Integer>> getNameMap() {
        return nameMap;
    }

    /**
     * in scope with scopeId, get the entity Id by name.
     * if not found, return -1.
     * @param name
     * @param scopeId
     * @return
     */
    public int getIdByNameInScope(String name, int scopeId) {
        if(nameMap.containsKey(scopeId)) {
            if(nameMap.get(scopeId).containsKey(name)) {
                return nameMap.get(scopeId).get(name);
            }
        }
        return -1;
    }

    /**
     * cannot change the order, since the scope is a hierarchy.
     */
    public void buildNameScope() {
        buildNameScopeForModules();
        buildNameScopeForFunctions();
        buildNameScopeForClasses();
        buildNameScopeForMethods();
    }


    /**
     * Module: children, imported name.
     */
    private void buildNameScopeForModules() {
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ModuleEntity) {
                int moduleId = entity.getId();
                addInChildren(moduleId, moduleId);
                addInImports(moduleId, moduleId);
            }
        }
    }

    /**
     * Function: children, parameter, located module's visible name, imported name.
     */
    private void buildNameScopeForFunctions() {
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PyFunctionEntity
                    && !(entity instanceof PyMethodEntity)) {
                int functionId = entity.getId();
                addInChildren(functionId, functionId);
                addInParas(functionId, functionId);

                int grandPaId = getGrandParentId(functionId);
                if(grandPaId != -1 && singleCollect.getEntities().get(grandPaId) instanceof ModuleEntity) {
                    addInChildren(functionId, grandPaId);
                }

                addInImports(functionId, functionId);
            }
        }

    }

    /**
     * Class: children, "self",
     *         BaseClass full name,
     *         BaseClass's children (from left to right, breadth first, no-diplicated),
     *         located module's visible name.
     */
    private void buildNameScopeForClasses() {
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ClassEntity) {
                int classId = entity.getId();
                addInChildren(classId, classId);
                addNameMap(classId, ConstantString.SELF, classId);
                addInBaseClassName(classId, classId);
                addInBaseClassChildren(classId, classId);

                int parentId = singleCollect.getEntities().get(classId).getParentId();
                if(parentId != -1 && singleCollect.getEntities().get(parentId) instanceof ModuleEntity) {
                    addInChildren(classId, parentId);
                }


            }
        }
    }

    /**
     * Method: children, parameter, self, BaseClass full name, located module's visible name, imported name.
     */
    private void buildNameScopeForMethods() {
        for(Entity entity :singleCollect.getEntities()) {
            if(entity instanceof PyMethodEntity) {
                int methodId = entity.getId();
                int parentId = singleCollect.getEntities().get(methodId).getParentId();
                addInChildren(methodId, methodId);
                addInParas(methodId, methodId);
                addNameMap(methodId, ConstantString.SELF, parentId);
                addInBaseClassName(methodId, parentId);

                int grandPaId = singleCollect.getEntities().get(parentId).getParentId();
                if(grandPaId != -1 && singleCollect.getEntities().get(grandPaId) instanceof ModuleEntity) {
                    addInChildren(methodId, grandPaId);
                }

                addInImports(methodId, methodId);

            }
        }

    }

    /** {scopeId, {name, nameId}}
     * if duplicated, not add it.
     * @param scopeId
     * @param name
     * @param nameId
     */
    private void addNameMap(int scopeId, String name, int nameId) {
        if (!nameMap.containsKey(scopeId)) {
            nameMap.put(scopeId, new HashMap<String, Integer>());
        }
        if (!nameMap.get(scopeId).containsKey(name)) {
            nameMap.get(scopeId).put(name, nameId);
        }
    }


    /**
     * add entityId's children's names into scopeId
     * @param scopeId
     * @param entityId
     */
    private void addInChildren(int scopeId, int entityId) {
        if(entityId == -1) {
            return;
        }
        for(int childId : singleCollect.getEntities().get(entityId).getChildrenIds()) {
            String childName = singleCollect.getEntities().get(childId).getName();
            addNameMap(scopeId, childName, childId);
        }
    }

    /**
     * add function or module's imported name into scopeId
     * @param scopeId
     * @param functionOrModuleId
     */
    private void addInImports(int scopeId, int functionOrModuleId) {
        if(functionOrModuleId == -1) {
            return;
        }
        ArrayList<ImportStmt> importStmts = null;
        HashMap<Integer, Integer> importedId2Indexes = null;

        if (singleCollect.getEntities().get(functionOrModuleId) instanceof ModuleEntity) {
            importStmts =  ((ModuleEntity) singleCollect.getEntities().get(functionOrModuleId)).getImportStmts();
            importedId2Indexes = ((ModuleEntity) singleCollect.getEntities().get(functionOrModuleId)).getImportedId2Indexs();
        }
        else if(singleCollect.getEntities().get(functionOrModuleId) instanceof PyFunctionEntity) {
            importStmts =  ((PyFunctionEntity) singleCollect.getEntities().get(functionOrModuleId)).getImportStmts();
            importedId2Indexes = ((PyFunctionEntity) singleCollect.getEntities().get(functionOrModuleId)).getImportedId2Indexs();
        }
        if(importedId2Indexes == null || importStmts == null) {
            return;
        }

        for (Tuple<String, Integer> relation : singleCollect.getEntities().get(functionOrModuleId).getRelations()) {
            if(relation.x.equals(ConstantString.RELATION_IMPORT)) {
                int importedId = relation.y;
                ImportStmt importStmt = importStmts.get(importedId2Indexes.get(importedId));
                String importedName = importStmt.getImpor();
                if(!importStmt.getAs().equals(ConstantString.NULL_STRING)) {
                    importedName = importStmt.getAs();
                }
                addNameMap(scopeId, importedName, importedId);
            }
        }
    }

    /**
     * add functionId's parameter into scopeId
     * @param scopeId
     * @param functionId
     */
    private void addInParas(int scopeId, int functionId) {
        if(functionId == -1) {
            return;
        }
        for (int paraId : ( (PyFunctionEntity) singleCollect.getEntities().get(functionId)).getParameters()) {
            String paraName = singleCollect.getEntities().get(paraId).getName();
            addNameMap(scopeId, paraName, paraId);
        }
    }

    /**
     * add classdId's  baseclass's children into scopeId
     * depth first add, from left to right.
     * @param scopeId
     * @param classId
     */
    private void addInBaseClassChildren(int scopeId, int classId) {
        if(classId == -1) {
            return;
        }

        //parent(depth first) list
        ArrayList<Tuple<Integer, String>> allBaseClasses = new ArrayList<Tuple<Integer, String>>();
        //System.out.println("XXX");
        findBasesClassesInDepth(allBaseClasses, classId);
        //System.out.println("YYY");

        for (Tuple<Integer, String> baseInfo : allBaseClasses) {
            int baseId = baseInfo.x;
            //String baseName = baseInfo.y;
            //addNameMap(scopeId, baseName, baseId);
            for(int childId : singleCollect.getEntities().get(baseId).getChildrenIds()) {
                String childName = singleCollect.getEntities().get(childId).getName();
                addNameMap(scopeId, childName, childId);
            }
        }

    }

    /**
     * find parent(depth first) list
     * @param classId
     * @return
     */
    private void findBasesClassesInDepth(ArrayList<Tuple<Integer, String>> allBaseClasses, int classId) {
        //System.out.println("interation: "  + allBaseClasses);
        if(classId == -1) {
            return;
        }
        ArrayList<String> baseNameList = ((ClassEntity) singleCollect.getEntities().get(classId)).getBaseClassNameList();
        ArrayList<Integer> baseIdList = ((ClassEntity) singleCollect.getEntities().get(classId)).getBaseClassIdList();
        for(int index = 0; index < baseIdList.size(); index++) {
            int baseId = baseIdList.get(index);
            String baseName = baseNameList.get(index);
            if(baseId != -1 && !isInList(baseId, allBaseClasses)) {
                Tuple<Integer, String> baseInfo = new Tuple<Integer, String>(baseId, baseName);
                allBaseClasses.add(baseInfo);
                findBasesClassesInDepth(allBaseClasses, baseId);
            }
        }

    }

    /**
     * judge the entityId is in list ot not
     * @param entityId
     * @param tupleList
     * @return
     */
    private boolean isInList(int entityId, ArrayList<Tuple<Integer, String>> tupleList) {
        for(Tuple<Integer, String> tuple : tupleList) {
            if(entityId == tuple.x) {
                return true;
            }
        }
        return false;
    }

    /**
     * add classId's baseclass name (original used name) into scopeId
     * @param scopeId
     * @param classId
     */
    private void addInBaseClassName(int scopeId, int classId) {
        if(classId == -1) {
            return;
        }
        ClassEntity classEntity = (ClassEntity) singleCollect.getEntities().get(classId);
        for(int index = 0; index < classEntity.getBaseClassIdList().size(); index++ ) {
            int baseClassId = classEntity.getBaseClassIdList().get(index);
            String baseClassName = classEntity.getBaseClassNameList().get(index);
            if(baseClassId == -1) {
                continue;
            }
            addNameMap(scopeId, baseClassName, baseClassId);
        }
    }

    /**
     * obtain grand parent's id
     * @param id
     * @return
     */
    private int getGrandParentId(int id) {
        int grandPaId = -1;
        if(id == -1) {
            return grandPaId;
        }
        int parentId = singleCollect.getEntities().get(id).getParentId();
        if(parentId == -1) {
            return grandPaId;
        }

        grandPaId = singleCollect.getEntities().get(parentId).getParentId();
        return grandPaId;
    }



}



