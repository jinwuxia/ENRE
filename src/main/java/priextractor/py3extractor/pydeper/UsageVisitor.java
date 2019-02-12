package priextractor.py3extractor.pydeper;

import uerr.AbsEntity;
import uerr.LocalName;
import uerr.SingleCollect;
import entitybuilder.pybuilder.pyentity.PyFunctionEntity;
import priextractor.py3extractor.searcher.NameSearch;
import util.Configure;

import java.util.ArrayList;
import java.util.Map;

public class UsageVisitor{
    private NameSearch nameSearch = NameSearch.getNameSearchInstance();
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();


    /**
     * build finalUsage for functions
     */
    public void buildUsage() {
        for (AbsEntity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity) {
                int functionId = entity.getId();
                //generate localName2IDMap
                findLocalName2IDForEntity(functionId);
                //use localName2IdMap and localName to build finalUsageMap
                buildUsageMapForEntity(functionId);
            }
        }
    }

    /**
     * for one functionEntity, build its final usage map
     * @param id
     */
    private void buildUsageMapForEntity(int id) {
        ArrayList<LocalName> localNames = null;
        Map<String, Integer> name2IdMap = null;
        if(singleCollect.getEntityById(id) instanceof PyFunctionEntity) {
            localNames = ((PyFunctionEntity) singleCollect.getEntityById(id)).getLocalNames();
            name2IdMap = ((PyFunctionEntity) singleCollect.getEntityById(id)).getName2IdMap();
        }
        if(localNames == null || name2IdMap == null) {
            return;
        }

        for(LocalName localName : localNames) {
            int localNameId = name2IdMap.get(localName.getName());
            if(localNameId == -1) {
                continue;
            }
            for(Map.Entry<String, Integer> entry : localName.getWeightedUsages().entrySet()) {
                String usage = entry.getKey();
                int weight = entry.getValue();
                if(singleCollect.getEntityById(id) instanceof PyFunctionEntity) {
                    ((PyFunctionEntity) singleCollect.getEntityById(id)).updateFinalUsageMap(usage, localNameId, weight);
                }
            }
        }
    }


    /**
     * for one functionEntity, find entitiIds for the localNames
     *
     * @param id
     */
    private void findLocalName2IDForEntity(int id) {
        for (LocalName localNameObject : ((PyFunctionEntity) singleCollect.getEntityById(id)).getLocalNames()) {
            String localName = localNameObject.getName();
            int localNameEntityId = searchNameInScope(localName, id);
            ((PyFunctionEntity) singleCollect.getEntityById(id)).getName2IdMap().put(localName, localNameEntityId);
        }
    }


    /**
     * name searcher for local name(x or x.y.z)
     * return the name 's corresponding uerr id
     *
     * @param localName
     * @param scopeId
     * @return
     */
    private int searchNameInScope(String localName, int scopeId) {
        int localNameId;
        while (localName != null && !localName.equals(Configure.NULL_STRING)) {
            String pre = localName.split("\\.")[0];
            if (scopeId != -1) {
                scopeId = nameSearch.getIdByNameInScope(pre, scopeId);
                if(pre.length() == localName.length()) {
                    localName = Configure.NULL_STRING;
                }
                else {
                    localName = localName.substring(pre.length() + 1, localName.length());
                }
            } else {
                break;
            }
        }
        localNameId = scopeId;
        return localNameId;
    }



}
