package priextractor.py3extractor.pydeper;

import entitybuilder.pybuilder.PyConstantString;
import priextractor.py3extractor.DepVisitor;
import uerr.AbsEntity;
import uerr.AbsFLDEntity;
import entitybuilder.pybuilder.pyentity.ImportStmt;
import entitybuilder.pybuilder.pyentity.ModuleEntity;
import entitybuilder.pybuilder.pyentity.PyFunctionEntity;
import util.Configure;
import util.StringUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportVisitor extends DepVisitor {


    @Override
    public void setDep() {
        setImportDep(); //import
    }


    /**
     * if find the import uerr, then save to uerr relation
     */
    private void setImportDep() {
        for(AbsEntity entity : singleCollect.getEntities()) {
            int entityId = entity.getId();
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
                if(!importStmt.getFrom().equals(Configure.NULL_STRING)) {
                    impstr = (importStmt.getFrom() + Configure.DOT + impstr);
                }
                //System.out.println("looking for " + impstr);

                int importId = findImport(impstr, entityId);

                //int scope = -1; //should get it based on from.
                //int id = findImportedEntity(impstr, scope);

                if(importId != -1) {
                    //save (importedID, importsList_index) into uerr
                    saveId2Id(entityId, importId, index);
                    saveRelation(entityId, importId, Configure.RELATION_IMPORT, Configure.RELATION_IMPORTED_BY);
                    //System.out.println("setImportDep: find " + singleCollect.getLongName(importId));
                }
                else  {
                    //System.out.println("setImportDep: cannot find " + impstr);
                }
            }
        }
    }


    /**
     * impStr can be:
     *     1) full qualified name:  x.y.z.... in this case, x is root.
     *     2) current name: y.z or y. in this case, y is a entity under current file's parent=>package
     *
     *     for the two case, split qualified name into arry,
     *     first find the root, i.e., [0], then treat as parent to find next
     * @param impStr
     * @param entityId the entity that has import statement
     * @return
     */
    private int findImport(String impStr, int entityId) {
        String[] impStrList = impStr.split("\\.");

        String rootName = impStrList[0];
        int rootId = singleCollect.getRoot(rootName);
        if( rootId == -1) {
            rootId = findRootInLocatedFolder(rootName, entityId);
        }
        if(rootId == -1) {
            return -1;
        }

        // in parentId, find child who has name impStrList[i]
        int parentId = rootId;
        for (int i = 1; i < impStrList.length; i++) {
            String name = impStrList[i];
            if(i == impStrList.length - 1 && name.equals(Configure.STAR)) {
                return parentId;
            }

            int childId = findChildByName(name, parentId);
            if(childId == -1) {
                return -1;
            }
            parentId = childId;

        }
        return parentId;
    }


    private int findRootInLocatedFolder(String name, int entityId) {
        int folderId = findLocatedFolder(entityId);
        if(folderId == -1) {
            return -1;
        }
        return findChildByName(name, folderId);
    }


    /**
     * find the folder which contains entityId
     * @param entityId
     * @return
     */
    private int findLocatedFolder(int entityId) {
        int id = entityId;
        while (id != -1 && !(singleCollect.getEntityById(id) instanceof AbsFLDEntity)) {
            id = singleCollect.getEntityById(id).getParentId();
        }
        return id;
    }


    /**
     *  look for child who has name
     *  if id is a package, then add _init_'s children and _all_'s content into  childrenlist.
     * @param searchName
     * @param id
     * @return
     */
    private int findChildByName(String searchName, int id) {
        List<Integer> children = singleCollect.getEntityById(id).getChildrenIds();
        if(singleCollect.getEntityById(id) instanceof AbsFLDEntity) {
            int initFileId = getInitForPackage(id);
            if(initFileId != -1) {
                children.addAll(singleCollect.getEntityById(initFileId).getChildrenIds());
            }
        }
        for (int childId : children) {
            AbsEntity childEntity = singleCollect.getEntityById(childId);
            String childSimpleName = childEntity.getSimpleName();
            if(childEntity instanceof ModuleEntity) {
                childSimpleName = ((ModuleEntity) childEntity).getModuleSimpleName();
            }
            if(searchName.equals(childSimpleName)) {
                return childId;
            }
        }
        return -1;
    }


    /**
     * find init file id for this package
     * @param pkgId
     * @return
     */
    private int getInitForPackage(int pkgId) {
        for (int childId : singleCollect.getEntityById(pkgId).getChildrenIds()) {
            if(singleCollect.getEntityById(childId) instanceof ModuleEntity) {
                String childName = singleCollect.getEntityById(childId).getName();
                if(childName.endsWith(PyConstantString.INIT_FILE_NAME)) {
                    return  childId;
                }
            }
        }
        return -1;
    }


    /**
     * save (importedID, importsList_index) into uerr
     * @param entityId
     * @param importedId
     * @param index
     */
    private void saveId2Id(int entityId, int importedId, int index) {
        if(singleCollect.getEntityById(entityId) instanceof ModuleEntity) {
            ((ModuleEntity) singleCollect.getEntityById(entityId)).updateImportedId2Indexs(importedId, index);
        }
        else if (singleCollect.getEntityById(entityId) instanceof PyFunctionEntity) {
            ((PyFunctionEntity) singleCollect.getEntityById(entityId)).updateImportedId2Indexs(importedId, index);
        }
    }


}
