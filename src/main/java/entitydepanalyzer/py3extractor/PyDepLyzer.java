package entitydepanalyzer.py3extractor;

import entitydepanalyzer.py3extractor.infer.TypeInfer;
import entitydepanalyzer.py3extractor.pydeper.*;
import entitydepanalyzer.py3extractor.searcher.NameSearch;


public class PyDepLyzer {

    public void identifyDeps() {
        DepVisitor depVisitor = new ImportVisitor();
        depVisitor.setDep();
        System.out.println("\nImport dependency identified successfully\n");

        depVisitor = new InheritVisitor();
        depVisitor.setDep();
        System.out.println("Inherit dependency identified successfully\n");

        NameSearch nameSearch = NameSearch.getNameSearchInstance();
        nameSearch.buildNameScope();

        TypeInfer typeInfer = new TypeInfer();
        typeInfer.inferTypeForVarEntity();
        System.out.println("Type inference finished successfully\n");

        //the var's type must be known before build its scope
        nameSearch.buildNameScopeForVar();
        System.out.println("Name searcher finished successfully\n");

        depVisitor = new CallVisitor();
        depVisitor.setDep();
        System.out.println("Call dependency identified successfully\n");

        UsageVisitor usageVisitor = new UsageVisitor();
        usageVisitor.buildUsage();

        depVisitor = new UseVisitor();
        depVisitor.setDep();
        System.out.println("Use dependency identified successfully\n");

        depVisitor = new SetVisitor();
        depVisitor.setDep();
        System.out.println("Set dependency identified successfully\n");

    }

}
