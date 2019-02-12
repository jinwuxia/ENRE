package priextractor.py3extractor.pydeper;

import uerr.AbsEntity;
import entitybuilder.pybuilder.PyConstantString;
import entitybuilder.pybuilder.pyentity.PyFunctionEntity;
import util.Configure;

import java.util.Map;

public class UseVisitor extends DepVisitor{

    @Override
    public void setDep() {
        for(AbsEntity entity : singleCollect.getEntities()) {

            if( !(entity instanceof PyFunctionEntity)) {
                continue;
            }

            Map<String, Map<Integer, Integer>> finalUsages = ((PyFunctionEntity) entity).getFinalUsageMap();
            for(Map.Entry<String, Map<Integer, Integer>> entry1 : finalUsages.entrySet()) {
                String usage = entry1.getKey();

                if (!usage.equals(PyConstantString.NAME_USAGE_USE)) {
                    continue;
                }

                for (Map.Entry<Integer, Integer> entry2 : entry1.getValue().entrySet()) {
                    int usageId = entry2.getKey();
                    int weight = entry2.getValue();
                    saveRelation(entity.getId(), usageId, Configure.RELATION_USE, Configure.RELATION_USED_BY);
                }
            }
        }
    }





}
