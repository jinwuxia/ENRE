package priextractor.py3extractor;

import priextractor.py3extractor.infer.TypeInfer;
import priextractor.py3extractor.newdeper.InferenceDependencyVisitor;
import priextractor.py3extractor.newdeper.ResolverTemplate;
import priextractor.py3extractor.pydeper.*;
import priextractor.py3extractor.searcher.NameSearch;


public class PyDepLyzer {

    public void identifyDeps() {
        DepVisitor depVisitor = new ImportVisitor();
        depVisitor.setDep();
        System.out.println("Import dependency identified successfully");

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



    }

}
