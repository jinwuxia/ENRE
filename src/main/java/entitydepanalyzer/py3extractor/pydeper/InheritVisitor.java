package entitydepanalyzer.py3extractor.pydeper;

import udr.AbsEntity;
import entitytreebuilder.pybuilder.pyentity.ClassEntity;
import entitytreebuilder.pybuilder.pyentity.ModuleEntity;
import util.Configure;
import util.Tuple;

public class InheritVisitor extends DepVisitor{

    @Override
    public void setDep() {
        for(AbsEntity entity: singleCollect.getEntities()) {
            if(entity instanceof ClassEntity) {
                for (String baseClassStr : ((ClassEntity) entity).getBaseClassNameList()) {
                    int baseId = findBaseClass(baseClassStr, entity.getId());

                    ((ClassEntity) singleCollect.getEntities().get(entity.getId())).addBaseClassId(baseId);

                    if(baseId != -1) {
                        //System.out.println("entityclass: " + udr.getName() + "; baseclass: " + baseClassStr + "; basedId: " +  baseId);
                        saveRelation(entity.getId(), baseId, Configure.RELATION_INHERIT, Configure.RELATION_INHERITED_BY);
                    }
                    else {
                        //System.out.println("entityclass: " + udr.getName() + "; baseclass: " + baseClassStr + " not found");
                    }
                }
            }
        }
    }

    /**
     * find base class id for this class
     * the first searcher by prefix which is imported,
     * next searcher by prefix which is parentId
     * @param baseClassStr
     * @param classId
     * @return
     */
    private int findBaseClass(String baseClassStr, int classId) {
        int scopeId = singleCollect.getEntities().get(classId).getParentId();
        int flag = 1;
        while(baseClassStr.contains(Configure.DOT)) { //imported
            Tuple<Integer, String> matchedRes;
            if(flag == 1) {
                // the first time, it's the imported.
                matchedRes = getMatchImportedId(baseClassStr, scopeId);
                flag = 0;
            }
            else {
                // other time, it's the children in each scope
                matchedRes = getMatchChildId(baseClassStr, scopeId);
            }
            scopeId = matchedRes.x;
            String matchedStr = matchedRes.y;
            if(scopeId == -1) {
                return -1;
            }
            baseClassStr = baseClassStr.substring(matchedStr.length() + 1, baseClassStr.length());
        }
        return findClassInModule(baseClassStr, scopeId);
    }


    /**
     * find x in scopeId's children
     * @param baseStr   x.y
     * @param scopeId   module/package id
     * @return
     */
    private Tuple<Integer, String> getMatchChildId(String baseStr, int scopeId) {
        Tuple<Integer, String> res = new Tuple<Integer, String>(-1, "");
        String [] arr = baseStr.split("\\.");
        String name = arr[0];
        for(int childId : singleCollect.getEntities().get(scopeId).getChildrenIds()) {
            String childName = singleCollect.getEntities().get(childId).getName();
            if(singleCollect.getEntities().get(childId) instanceof ModuleEntity) {
                childName = ((ModuleEntity) singleCollect.getEntities().get(childId)).getModuleSimpleName();
            }
            if(name.equals(childName)) {
                res.x = childId;
                res.y = childName;
                return res;
            }
        }
        return res;
    }
    /**
     * in module scopeId, find the imported str is match baseStr
     * @param baseStr
     * @param scopeId
     * @return tuple.x matched str's id;  tuple.y matched String
     */
    private Tuple<Integer, String> getMatchImportedId(String baseStr, int scopeId) {
        Tuple<Integer, String> res = new Tuple<Integer, String>(-1, "");
        ModuleEntity moduleEntity = (ModuleEntity) singleCollect.getEntities().get(scopeId);
        for(Tuple<String,Integer> relation : moduleEntity.getRelations()) {
            if(relation.x.equals(Configure.RELATION_IMPORT)) {
                //int importedId = relation.y;
                int index = moduleEntity.getImportedId2Indexs().get(relation.y);
                String importedName = moduleEntity.getImportStmts().get(index).getImpor();
                if(!moduleEntity.getImportStmts().get(index).getAs().equals(Configure.NULL_STRING)) {
                    importedName = moduleEntity.getImportStmts().get(index).getAs();
                }
                if(baseStr.startsWith(importedName)) {
                    res.x = relation.y;
                    res.y = importedName;
                    return res;
                }
            }
        }
        return res;
    }

    /** find  class by className in module
     *
     * @param className
     * @param moduleId
     * @return
     */
    private int findClassInModule(String className, int moduleId) {
        if (moduleId == -1) {
            return -1;
        }
        for (int childId : singleCollect.getEntities().get(moduleId).getChildrenIds()) {
            if(singleCollect.getEntities().get(childId) instanceof ClassEntity) {
                ClassEntity childEntity = (ClassEntity) singleCollect.getEntities().get(childId);
                if(childEntity.getName().equals(className)) {
                    return childId;
                }
            }
        }
        return -1;
    }
}
