package writer;

import entitybuilder.pybuilder.pyentity.ModuleEntity;
import entitybuilder.pybuilder.pyentity.PyFunctionEntity;
import entitybuilder.pybuilder.pyentity.PyMethodEntity;
import uerr.AbsEntity;
import uerr.SingleCollect;
import util.Configure;
import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UndWriter {
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    public void writeUnd() {
        CsvWriter writer = new CsvWriter();
        Configure configure = Configure.getConfigureInstance();

        String undEntFileName = configure.getAnalyzedProjectName() + "_jwx_und_ent.csv";
        writer.writeCsv(getEntitiesList(), undEntFileName);
        System.out.println("Export " + undEntFileName);

        String undDepFileName = configure.getAnalyzedProjectName() + "_jwx_und_dep.csv";
        writer.writeCsv(getDepList(), undDepFileName);
        System.out.println("Export " + undDepFileName);

    }


    private List<String[]> getDepList() {
        List<String[]> deplist = new ArrayList<String[]>();
        for(AbsEntity entity : singleCollect.getEntities()) {
            int id1 = entity.getId();
            String shortname1 = getShortName(id1);
            String longname1 = getLongName(id1);
            for (Tuple<String, Integer> relation : entity.getRelations()) {
                String depType = getDepType(relation.x);
                int id2 = relation.y;
                if(!depType.equals("")) {
                    String shorname2 = getShortName(id2);
                    String longname2 = getLongName(id2);
                    String[] arr = new String[]{depType, longname1, shortname1, longname2, shorname2};
                    deplist.add(arr);
                }
            }
        }
        return deplist;
    }

    /**
     * with no weight
     * @return
     */
    public String priDepStatis() {
        Map<String, Integer> res = new HashMap<String, Integer>();
        Map<String, Map<String, Map<String, Integer>>> depSta = new HashMap<String, Map<String, Map<String, Integer>>>();
        for(AbsEntity entity : singleCollect.getEntities()) {
            int id1 = entity.getId();
            String longname1 = getLongName(id1);
            for (Tuple<String, Integer> relation : entity.getRelations()) {
                String depType = getDepType(relation.x);
                int id2 = relation.y;
                if(!depType.equals("")) {
                    String longname2 = getLongName(id2);
                    if(!depSta.containsKey(depType)) {
                        depSta.put(depType, new HashMap<String, Map<String, Integer>>());
                    }
                    if(!depSta.get(depType).containsKey(longname1)) {
                        depSta.get(depType).put(longname1, new HashMap<String, Integer>());
                    }
                    depSta.get(depType).get(longname1).put(longname2, 0);
                }
            }
        }

        for (Map.Entry<String, Map<String, Map<String, Integer>>> entry1 : depSta.entrySet()) {
            String depStr = entry1.getKey();
            int count = 0;
            for (Map.Entry<String, Map<String, Integer>> entry2: entry1.getValue().entrySet()) {
                count += (entry2.getValue().size());
                //for (Map.Entry<String, Integer> entry3 : entry2.getValue().entrySet()) {
                //    count += 1;
                //}
            }
            res.put(depStr, count);
        }
        String str = "";
        for(Map.Entry<String, Integer> entry : res.entrySet()) {
            if(entry.getValue() != 0) {
                str += entry.getKey();
                str += ":           ";
                str += Integer.toString(entry.getValue());
                str += "\n";
            }
        }
        return str;
    }

    /**
     *
     * @return
     */
    //[type, longname, shortname]
    private List<String[]> getEntitiesList () {
        List<String[]> entlist = new ArrayList<String[]>();
        for (AbsEntity entity : singleCollect.getEntities()) {
            int id = entity.getId();
            String type = getEntityType(id);
            if(!type.equals("")) {
                String longname = getLongName(id);
                String shotname = getShortName(id);
                String[] arr = new String[] {type, longname, shotname};
                entlist.add(arr);
            }
        }
        return entlist;
    }

    private String getShortName(int id) {
        AbsEntity entity = singleCollect.getEntityById(id);
        if(entity instanceof PyFunctionEntity && entity.getName().endsWith("__main__")) {
            int fileId = entity.getParentId();
            String fileName = singleCollect.getEntityById(fileId).getSimpleName();
            return fileName;
        }
        return singleCollect.getEntityById(id).getSimpleName();
    }

    private String getDepType(String depStr) {
        if(depStr.equals(Configure.RELATION_CALL)) {
            return Configure.RELATION_CALL;
        }
        if(depStr.equals(Configure.RELATION_IMPORT)) {
            return Configure.RELATION_IMPORT;
        }
        if(depStr.equals(Configure.RELATION_INHERIT)) {
            return Configure.RELATION_EXTEND;
        }
        if(depStr.equals(Configure.RELATION_SET)) {
            return Configure.RELATION_SET;
        }
        if(depStr.equals(Configure.RELATION_USE)) {
            return Configure.RELATION_USE;
        }
        if(depStr.endsWith(Configure.RELATION_PARAMETER)) {
            return Configure.RELATION_PARAMETER;
        }
        if(depStr.endsWith(Configure.RELATION_RETURN)) {
            return Configure.RELATION_RETURN;
        }
        if(depStr.equals(Configure.RELATION_IMPLICIT_INTERNAL_CALL)) {
            return Configure.RELATION_IMPLICIT_INTERNAL_CALL;
        }
        if(depStr.equals(Configure.RELATION_IMPLICIT_EXTERNAL_CALL)) {
            return Configure.RELATION_IMPLICIT_EXTERNAL_CALL;
        }

        return "";
        //if(depStr.equals(Configure.RELATION_SET))
    }


    private String getEntityType(int id) {
        if(singleCollect.isFolder(id)) {
            return "Package";
        }
        if(singleCollect.isFile(id)) {
            return "File";
        }
        if(singleCollect.isClass(id)) {
            return "Class";
        }
        if (singleCollect.isFunction(id) &&
                !(singleCollect.getEntityById(id) instanceof PyMethodEntity)){
            return "Function";
        }
        if(singleCollect.getEntityById(id) instanceof PyMethodEntity) {
            return "Method";
        }
        return "";
    }

    /**
     * if module, it longname = fileName
     * if others, it longname = parentsimplename.parentsimplename....
     * @param id
     * @return
     */
    private String getLongName(int id) {
        AbsEntity entity = singleCollect.getEntityById(id);
        if(entity instanceof ModuleEntity) {
            return entity.getName();
        }
        if(entity instanceof PyFunctionEntity && entity.getName().endsWith("__main__")) {
            return singleCollect.getEntityById(entity.getParentId()).getName();
        }
        String longname = "";
        while(id != -1) {
            //System.out.println("name:" + singleCollect.getEntityById(id).getName());
            //System.out.println("simplename:" + singleCollect.getEntityById(id).getSimpleName());
            String name = singleCollect.getEntityById(id).getSimpleName();
            if(name.endsWith(".py")) {
                name = name.split("\\.py")[0];
            }
            if(name.endsWith(".go")) {
                name = name.split("\\.go")[0];
            }
            if(!longname.equals("")) {
                longname = name + "." + longname;
            }
            else {
                longname = name;
            }
            id = singleCollect.getEntityById(id).getParentId();
        }
        return longname;
    }


}
