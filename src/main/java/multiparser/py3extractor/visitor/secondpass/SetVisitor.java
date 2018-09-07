package multiparser.py3extractor.visitor.secondpass;

import multiparser.entity.Entity;
import multiparser.py3extractor.ConstantString;
import multiparser.py3extractor.pyentity.PyFunctionEntity;
import multiparser.util.Configure;

import java.util.Map;

public class SetVisitor extends DepVisitor{
    @Override
    public void setDep() {
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PyFunctionEntity) {
                Map<String, Map<Integer, Integer>> finalUsages = ((PyFunctionEntity) entity).getFinalUsageMap();
                for(Map.Entry<String, Map<Integer, Integer>> entry1 : finalUsages.entrySet()) {
                    String usage = entry1.getKey();
                    if(usage.equals(ConstantString.NAME_USAGE_SET)) {
                        for (Map.Entry<Integer, Integer> entry2 : entry1.getValue().entrySet()) {
                            int usageId = entry2.getKey();
                            int weight = entry2.getValue();
                            saveRelation(entity.getId(), usageId, Configure.RELATION_SET, Configure.RELATION_SETED_BY);
                        }
                    }
                }
            }
        }
    }


}
