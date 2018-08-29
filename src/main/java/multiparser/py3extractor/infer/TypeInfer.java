package multiparser.py3extractor.infer;

import multiparser.entity.Entity;
import multiparser.entity.VarEntity;
import multiparser.extractor.SingleCollect;
import multiparser.py3extractor.ConstantString;
import multiparser.py3extractor.pyentity.ClassEntity;
import multiparser.py3extractor.search.NameSearch;

public class TypeInfer {
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    private NameSearch nameSearch = NameSearch.getNameSearchInstance();

    public void inferTypeForVarEntity() {
        //type infer for class object vars
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof VarEntity) {
                if(((VarEntity) entity).getValue() != null &&
                        ((VarEntity) entity).getValue().contains(ConstantString.LEFT_PARENTHESES)
                        && ((VarEntity) entity).getValue().contains(ConstantString.RIGHT_PARENTHESES)) {
                    inferTypeForClassObjectVar(entity.getId());
                }
            }
        }
    }

    private void inferTypeForClassObjectVar(int varId) {
        String value = ((VarEntity) singleCollect.getEntities().get(varId)).getValue();
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
            ((VarEntity) singleCollect.getEntities().get(varId)).setTypeId(typeId);
            typeName = singleCollect.getEntities().get(typeId).getName();
        }
        else {
            ((VarEntity) singleCollect.getEntities().get(varId)).setTypeId(-1);
        }
        System.out.println("var name: " + name + "; value:" + value + "; likelyClassName: " + likelyClassName + "; typeName: "  + typeName);
    }


}
