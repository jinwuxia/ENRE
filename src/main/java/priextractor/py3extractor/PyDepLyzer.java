package priextractor.py3extractor;

import entitybuilder.pybuilder.pyentity.ModuleEntity;
import priextractor.py3extractor.infer.TypeInfer;
import priextractor.py3extractor.newdeper.InferenceDependencyVisitor;
import priextractor.py3extractor.newdeper.ResolverTemplate;
import priextractor.py3extractor.pydeper.*;
import priextractor.py3extractor.searcher.NameSearch;
import uerr.AbsEntity;
import uerr.AbsFLDEntity;
import uerr.SingleCollect;
import util.StringUtil;

import java.util.HashMap;


public class PyDepLyzer {
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    public void identifyDeps() {

        buildParentChildDepForFileFolder();

        SingleCollect.getSingleCollectInstance().printAllEntity();

        DepVisitor depVisitor = new ImportVisitor();
        depVisitor.setDep();
        System.out.println("Import dependency identified successfully");


        /**
        depVisitor = new InheritVisitor();
        depVisitor.setDep();
        System.out.println("Inherit dependency identified successfully");

        NameSearch nameSearch = NameSearch.getNameSearchInstance();
        nameSearch.buildNameScope();

        TypeInfer typeInfer = new TypeInfer();
        typeInfer.inferTypeForVarEntity();
        System.out.println("Type inference finished successfully");

        //the var's type must be known before build its scope
        nameSearch.buildNameScopeForVar();
        System.out.println("Name searcher finished successfully");


        ResolverTemplate resolverTemplate = new ResolverTemplate();
        resolverTemplate.run();

        InferenceDependencyVisitor inferenceDependencyVisitor = new InferenceDependencyVisitor();
        inferenceDependencyVisitor.setDep();
        inferenceDependencyVisitor.setDepByCategory();
        System.out.println("resolve expression and save implicit dependency successfully");
         */


    }


    private void buildParentChildDepForFileFolder() {
        HashMap<String, Integer> pkg2IdMap =  buildPkgMap(); //fullpathname->id
        bindPkg2Pkg(pkg2IdMap);
        bindMod2Pkg(pkg2IdMap);

    }

    /**
     * build parent-child relation between pkgs
     */
    private void bindPkg2Pkg(HashMap<String, Integer> pkg2IdMap) {
        for(AbsEntity entity :  singleCollect.getEntities()) {
            if(entity instanceof AbsFLDEntity) {
                String dirName = StringUtil.deleteLastStrByPathDelimiter(((AbsFLDEntity) entity).getFullPath());
                int parentId = -1;
                if(pkg2IdMap.containsKey(dirName)) {
                    parentId = pkg2IdMap.get(dirName);
                }

                singleCollect.getEntityById(entity.getId()).setParentId(parentId);
                if(parentId != -1
                        && singleCollect.getEntityById(parentId) instanceof AbsFLDEntity) {
                    singleCollect.getEntityById(parentId).addChildId(entity.getId());
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
        for (AbsEntity entity : singleCollect.getEntities()) {
            if(entity instanceof ModuleEntity) {
                String dirName = StringUtil.deleteLastStrByPathDelimiter(entity.getName());
                int parentId = -1;
                if(pkg2IdMap.containsKey(dirName)) {
                    parentId = pkg2IdMap.get(dirName);
                }
                singleCollect.getEntityById(entity.getId()).setParentId(parentId);
                if(parentId != -1
                        && singleCollect.getEntityById(parentId) instanceof AbsFLDEntity) {
                    singleCollect.getEntityById(parentId).addChildId(entity.getId());
                }
            }
        }
    }

    /**
     * map["packagename"] = packageId
     * @return
     */
    private HashMap<String, Integer> buildPkgMap() {
        HashMap<String, Integer> pkg2IdMap = new HashMap<>();
        for(AbsEntity entity : singleCollect.getEntities()) {
            if(entity instanceof AbsFLDEntity) {
                pkg2IdMap.put(((AbsFLDEntity) entity).getFullPath(), entity.getId());
            }
        }
        return pkg2IdMap;
    }



}
