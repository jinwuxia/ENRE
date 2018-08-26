package multiparser.py3extractor;

import multiparser.entity.Entity;
import multiparser.entity.PackageEntity;
import multiparser.extractor.SingleCollect;
import multiparser.py3extractor.pyentity.ModuleEntity;

import java.util.HashMap;

public class BasicDepVisitor {

    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    public void setDep() {
        HashMap<String, Integer> pkg2IdMap = buildPkgMap();
        bindPkg2Pkg(pkg2IdMap);
        bindMod2Pkg(pkg2IdMap);
    }

    /**
     * build parent-child relation between pkgs
      */
    private void bindPkg2Pkg(HashMap<String, Integer> pkg2IdMap) {
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
    private void bindMod2Pkg(HashMap<String, Integer> pkg2IdMap) {
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
    private HashMap<String, Integer> buildPkgMap() {
        HashMap<String , Integer> pkg2IdMap = new HashMap<String, Integer>();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PackageEntity) {
                pkg2IdMap.put(((PackageEntity) entity).getFullPath(), entity.getId());
            }
        }
        return pkg2IdMap;
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


}
