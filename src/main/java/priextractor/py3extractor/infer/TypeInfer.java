package priextractor.py3extractor.infer;

import uerr.AbsEntity;
import uerr.AbsVAREntity;
import uerr.SingleCollect;
import entitybuilder.pybuilder.PyConstantString;
import entitybuilder.pybuilder.pyentity.ClassEntity;
import entitybuilder.pybuilder.pyentity.ClassMethodEntity;
import priextractor.py3extractor.searcher.NameSearch;
import util.Configure;

import java.util.ArrayList;

/**
 * This TypeInfer focus on the explicit type:
 * eg.  obj  =  ClassName()
 * eg.  function func(cls,...)
 */
public class TypeInfer {
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    private NameSearch nameSearch = NameSearch.getNameSearchInstance();

    public void inferTypeForVarEntity() {
        for(AbsEntity entity : singleCollect.getEntities()) {

            if(entity instanceof AbsVAREntity) {
                /**
                 * * if var_entity is obj = ClassName() , so this var is a class object.
                 */
                if(((AbsVAREntity) entity).getValue() != null &&
                        ((AbsVAREntity) entity).getValue().contains(Configure.LEFT_PARENTHESES)
                        && ((AbsVAREntity) entity).getValue().contains(Configure.RIGHT_PARENTHESES)) {
                    inferTypeForClassObjectVar(entity.getId());
                }

            }

            /**
             * if entity is a parameter named as cls, as the parameter of class's method.
             * based on the tradition, cls refers to the class type.
             */
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
        String value = ((AbsVAREntity) singleCollect.getEntityById(varId)).getValue();
        String name = singleCollect.getEntityById(varId).getName();
        String []  arr = value.split("\\(");
        String likelyClassName = arr[0];

        int parentId = singleCollect.getEntityById(varId).getParentId();
        if(parentId == -1) {
            return;
        }
        int typeId = nameSearch.getIdByNameInScope(likelyClassName, parentId);
        if(typeId == -1) {
            return;
        }
        String typeName = "";
        if(singleCollect.getEntityById(typeId) instanceof ClassEntity) {
            ((AbsVAREntity) singleCollect.getEntityById(varId)).setTypeId(typeId);
            typeName = singleCollect.getEntityById(typeId).getName();
        }
        else {
            ((AbsVAREntity) singleCollect.getEntityById(varId)).setTypeId(-1);
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

         if(singleCollect.getEntityById(paraId).getName().equals(PyConstantString.CLASS_METHOD_CLS_PARAMETER)) {
             int classId = singleCollect.getEntityById(entityId).getParentId();
             if(classId != -1
                     && singleCollect.getEntityById(classId) instanceof ClassEntity) {
                 ( (AbsVAREntity) singleCollect.getEntityById(paraId)).setTypeId(classId);
                 String methodname = singleCollect.getEntityById(entityId).getName();
                 String paramname = singleCollect.getEntityById(paraId).getName();
                 String typeName = singleCollect.getEntityById(classId).getName();
                 //System.out.println("method: " + methodname + "; para: " + paramname + "; typeName: "  + typeName);

             }
         }
    }



}
