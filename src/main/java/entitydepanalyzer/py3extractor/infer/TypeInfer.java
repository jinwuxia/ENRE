package entitydepanalyzer.py3extractor.infer;

import udr.AbsEntity;
import udr.AbsVAREntity;
import udr.SingleCollect;
import entitytreebuilder.pybuilder.PyConstantString;
import entitytreebuilder.pybuilder.pyentity.ClassEntity;
import entitytreebuilder.pybuilder.pyentity.ClassMethodEntity;
import entitydepanalyzer.py3extractor.searcher.NameSearch;
import util.Configure;

import java.util.ArrayList;

public class TypeInfer {
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    private NameSearch nameSearch = NameSearch.getNameSearchInstance();

    public void inferTypeForVarEntity() {
        for(AbsEntity entity : singleCollect.getEntities()) {

            if(entity instanceof AbsVAREntity) {
                if(((AbsVAREntity) entity).getValue() != null &&
                        ((AbsVAREntity) entity).getValue().contains(Configure.LEFT_PARENTHESES)
                        && ((AbsVAREntity) entity).getValue().contains(Configure.RIGHT_PARENTHESES)) {
                    inferTypeForClassObjectVar(entity.getId());
                }
            }

            if(entity instanceof ClassMethodEntity) {
                inferTypeForParaCls(entity.getId());
            }
        }
    }

    /**
     * type infer for class object vars
     * var = Classname()  or var = Classname
     * @param varId
     */
    private void inferTypeForClassObjectVar(int varId) {
        String value = ((AbsVAREntity) singleCollect.getEntities().get(varId)).getValue();
        String name = singleCollect.getEntities().get(varId).getName();
        String []  arr = value.split("\\(");
        String likelyClassName = arr[0];

        int parentId = singleCollect.getEntities().get(varId).getParentId();
        if(parentId == -1) {
            return;
        }
        int typeId = nameSearch.getIdByNameInScope(likelyClassName, parentId);
        if(typeId == -1) {
            return;
        }
        String typeName = "";
        if(singleCollect.getEntities().get(typeId) instanceof ClassEntity) {
            ((AbsVAREntity) singleCollect.getEntities().get(varId)).setTypeId(typeId);
            typeName = singleCollect.getEntities().get(typeId).getName();
        }
        else {
            ((AbsVAREntity) singleCollect.getEntities().get(varId)).setTypeId(-1);
        }
        //System.out.println("var name: " + name + "; value:" + value + "; likelyClassName: " + likelyClassName + "; typeName: "  + typeName);
    }

    /**
     * type infer for classmethod's first parameter: cls

     * @param entityId
     */
    private void inferTypeForParaCls(int entityId) {
        ArrayList<Integer> paras = ((ClassMethodEntity) SingleCollect.getSingleCollectInstance().getEntities().get(entityId)).getParameters();
         if(paras.isEmpty()) {
             return;
         }
         int paraId = paras.get(0);

         if(singleCollect.getEntities().get(paraId).getName().equals(PyConstantString.CLASS_METHOD_CLS_PARAMETER)) {
             int classId = singleCollect.getEntities().get(entityId).getParentId();
             if(classId != -1
                     && singleCollect.getEntities().get(classId) instanceof ClassEntity) {
                 ( (AbsVAREntity) singleCollect.getEntities().get(paraId)).setTypeId(classId);
                 String methodname = singleCollect.getEntities().get(entityId).getName();
                 String paramname = singleCollect.getEntities().get(paraId).getName();
                 String typeName = singleCollect.getEntities().get(classId).getName();
                 //System.out.println("method: " + methodname + "; para: " + paramname + "; typeName: "  + typeName);

             }
         }
    }



}
