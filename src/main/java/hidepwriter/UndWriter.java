package hidepwriter;

import entitytreebuilder.pybuilder.pyentity.ModuleEntity;
import entitytreebuilder.pybuilder.pyentity.PyMethodEntity;
import sun.security.krb5.Config;
import udr.AbsEntity;
import udr.SingleCollect;
import util.Configure;
import util.Tuple;

import java.util.ArrayList;
import java.util.List;

public class UndWriter {
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    public void writeUnd() {
        CsvWriter writer = new CsvWriter();
        Configure configure = Configure.getConfigureInstance();

        String undEntFileName = configure.getAnalyzedProjectName() + "_jwx_und_ent.csv";
        writer.writeCsv(getEntitiesList(), undEntFileName);

        String undDepFileName = configure.getAnalyzedProjectName() + "_jwx_und_dep.csv";
        writer.writeCsv(getDepList(), undDepFileName);
    }


    private List<String[]> getDepList() {
        List<String[]> deplist = new ArrayList<String[]>();
        for(AbsEntity entity : singleCollect.getEntities()) {
            int id1 = entity.getId();
            String shortname1 = entity.getSimpleName();
            String longname1 = getLongName(id1);
            for (Tuple<String, Integer> relation : entity.getRelations()) {
                String depType = getDepType(relation.x);
                int id2 = relation.y;
                if(!depType.equals("")) {
                    String shorname2 = singleCollect.getEntities().get(id2).getSimpleName();
                    String longname2 = getLongName(id2);
                    String[] arr = new String[]{longname1, shortname1, longname2, shorname2};
                    deplist.add(arr);
                }
            }
        }
        return deplist;
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
                String shotname = entity.getSimpleName();
                String[] arr = new String[] {type, longname, shotname};
                entlist.add(arr);
            }
        }
        return entlist;
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
        return "";
        //if(depStr.equals(Configure.RELATION_SET))
    }


    private String getEntityType(int id) {
        if(singleCollect.isFolder(id)) {
            return "Package";
        }
        if(singleCollect.isFile(id)) {
            return "Module";
        }
        if(singleCollect.isClass(id)) {
            return "Class";
        }
        if (singleCollect.isFunction(id) &&
                !(singleCollect.getEntities().get(id) instanceof PyMethodEntity)){
            return "Function";
        }
        if(singleCollect.getEntities().get(id) instanceof PyMethodEntity) {
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
        AbsEntity entity = singleCollect.getEntities().get(id);
        if(entity instanceof ModuleEntity) {
            return entity.getName();
        }
        String longname = "";
        while(id != -1) {
            //System.out.println("name:" + singleCollect.getEntities().get(id).getName());
            //System.out.println("simplename:" + singleCollect.getEntities().get(id).getSimpleName());
            String name = singleCollect.getEntities().get(id).getSimpleName();
            if(name.endsWith(".py")) {
                name = name.split("\\.py")[0];
            }
            if(!longname.equals("")) {
                longname = name + "." + longname;
            }
            else {
                longname = name;
            }
            id = singleCollect.getEntities().get(id).getParentId();
        }
        return longname;
    }


}
