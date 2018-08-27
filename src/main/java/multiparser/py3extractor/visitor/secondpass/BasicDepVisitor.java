package multiparser.py3extractor.visitor.secondpass;

import multiparser.entity.Entity;
import multiparser.entity.PackageEntity;
import multiparser.extractor.SingleCollect;
import multiparser.py3extractor.ConstantString;
import multiparser.py3extractor.pyentity.ImportStmt;
import multiparser.py3extractor.pyentity.ModuleEntity;
import multiparser.py3extractor.pyentity.PyFunctionEntity;
import multiparser.util.Tuple;


import java.util.ArrayList;
import java.util.HashMap;

public class BasicDepVisitor {

    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    private HashMap<String, Integer> pkg2IdMap = new HashMap<String, Integer>();
    private HashMap<String, Integer> mod2IdMap = new HashMap<String, Integer>();

    public BasicDepVisitor() {
        buildPkgMap(); //fullpathname->id
        //buildModuleMap(); //simplename->id
    }

    public void setDep() {
        bindPkg2Pkg();
        bindMod2Pkg();

        setImportDep();
    }

    /**
     * build parent-child relation between pkgs
      */
    private void bindPkg2Pkg() {
        for(Entity entity :  singleCollect.getEntities()) {
            if(entity instanceof PackageEntity) {
                String dirName = getDir( ((PackageEntity) entity).getFullPath() );
                int parentId = -1;
                if(pkg2IdMap.containsKey(dirName)) {
                    parentId = pkg2IdMap.get(dirName);
                }

                singleCollect.getEntities().get(entity.getId()).setParentId(parentId);
                if(parentId != -1
                        && singleCollect.getEntities().get(parentId) instanceof PackageEntity) {
                    singleCollect.getEntities().get(parentId).addChildId(entity.getId());
                }
            }
        }
    }

    /**
     * module's parent is package.
     * is it possible that a module's parent is also a module.?????????????/
     *
     *
     * build parent-child relation between module and pkgs
     */
    private void bindMod2Pkg() {
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ModuleEntity) {
                String dirName = getDir(entity.getName());
                int parentId = -1;
                if(pkg2IdMap.containsKey(dirName)) {
                    parentId = pkg2IdMap.get(dirName);
                }
                singleCollect.getEntities().get(entity.getId()).setParentId(parentId);
                if(parentId != -1
                        && singleCollect.getEntities().get(parentId) instanceof PackageEntity) {
                    singleCollect.getEntities().get(parentId).addChildId(entity.getId());
                }
            }
        }
    }

    /**
     * map["packagename"] = packageId
     * @return
     */
    private void buildPkgMap() {
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PackageEntity) {
                this.pkg2IdMap.put(((PackageEntity) entity).getFullPath(), entity.getId());
            }
        }
    }

    private void buildModuleMap() {
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ModuleEntity) {
                this.mod2IdMap.put(((ModuleEntity) entity).getModuleSimpleName(), entity.getId());
            }
        }
    }


    private String getDir(String name) {
        String [] arr = name.split("/");
        String dirName = arr[0];
        for(int i = 1; i < arr.length - 1; i++ ) {
            dirName += "/";
            dirName += arr[i];
        }
        return dirName;
    }

    /**
     * if find the import entity, then save to entity relation
     */
    private void setImportDep() {
        for(Entity entity : singleCollect.getEntities()) {
            ArrayList<ImportStmt> importStmts = null;
            if(entity instanceof ModuleEntity) {
                importStmts = ((ModuleEntity) entity).getImportStmts();
            }
            else if(entity instanceof PyFunctionEntity) {
                importStmts = ((PyFunctionEntity) entity).getImportStmts();
            }
            if(importStmts == null) {
                continue;
            }

            for (int index = 0; index < importStmts.size(); index++) {
                ImportStmt importStmt = importStmts.get(index);
                String impstr = importStmt.getImpor();
                if(!importStmt.getFrom().equals(ConstantString.NULL_STRING)) {
                    impstr = (importStmt.getFrom() + ConstantString.DOT + impstr);
                }
                System.out.println("looking for " + impstr);
                int scope = -1; //should get it based on from.
                int id = findImportedEntity(impstr, scope);
                if(id != -1) {
                    saveRelation(entity.getId(), id, ConstantString.RELATION_IMPORT, ConstantString.RELATION_IMPORTED_BY);
                }
                else  {
                    System.out.println("setImportDep: cannot find " + impstr);
                }
            }
        }
    }


    /**
     *
     * @param impstr
     * @param scope
     * @return
     */
    private int findImportedEntity(String impstr, int scope) {
        while(impstr.contains(ConstantString.DOT)) {
            String [] arr = impstr.split("\\.");
            String pre = arr[0];
            String post = impstr.substring(pre.length() + 1, impstr.length());
            scope = findPkgOrMod(pre, scope);
            impstr = post;
            if(scope == -1) {
                return -1;
            }
        }
        return findObject(impstr, scope);
    }

    /**
     * in parent scope, find a str's id
     * @param str
     * @param parentId
     * @return
     */
    private int findObject(String str, int parentId) {
        if(str.equals(ConstantString.STAR)) {
            return parentId;
        }
        if(parentId == -1) { //import q (a is module or package)
            return findPkgOrMod(str, parentId);
        }

        for (int childId : singleCollect.getEntities().get(parentId).getChildrenIds()) {
            String name = singleCollect.getEntities().get(childId).getName();
            if(singleCollect.getEntities().get(childId) instanceof ModuleEntity) {
                name = ((ModuleEntity) singleCollect.getEntities().get(childId)).getModuleSimpleName();
            }
            if(name.equals(str)) {
                return childId;
            }
        }
        return -1;
    }

    /**
     * in scope , find id of package ir module
     * @param str
     * @param scopeId
     * @return
     */
    private int findPkgOrMod(String str, int scopeId) {
        if(scopeId == -1) {
            for (Entity entity : singleCollect.getEntities()) {
                if(isStrAModOrPkg(entity, str)) {
                    return entity.getId();
                }
            }
        }
        else { //scope != -1, it's parent id
            for(int childId : singleCollect.getEntities().get(scopeId).getChildrenIds()) {
                Entity entity = singleCollect.getEntities().get(childId);
                if(isStrAModOrPkg(entity, str)) {
                    return entity.getId();
                }
            }
        }
        return -1;
    }


    /**
     * judge the str is module or package entity's name or not.
     * because module's name is a full path , so the code is here
     */
    private boolean isStrAModOrPkg(Entity entity, String str) {
        String name = "";
        if(entity instanceof PackageEntity) {
            name = entity.getName();
        }
        else if (entity instanceof ModuleEntity) {
            name = ((ModuleEntity) entity).getModuleSimpleName();
        }
        if(name.equals(str)) {
            return true;
        }
        return false;
    }


    /**
     * relationType1: entityId1 -> entityId2
     * relationType2: entityId2 -> entityId1
     * @param entityId1
     * @param entityId2
     * @param relationType1
     * @param relationType2
     */
    private void saveRelation(int entityId1, int entityId2, String relationType1, String relationType2) {
        Tuple<String, Integer> relation1 =
                new Tuple<String, Integer>(relationType1, entityId2);
        singleCollect.getEntities().get(entityId1).addRelation(relation1);

        Tuple<String, Integer> relation2 =
                new Tuple<String, Integer>(relationType2, entityId1);
        singleCollect.getEntities().get(entityId2).addRelation(relation2);
    }

}
