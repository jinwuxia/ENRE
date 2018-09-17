package entitydepanalyzer.goextractor;

import entitydepanalyzer.goextractor.infer.TypeInfer;
import entitydepanalyzer.goextractor.godeper.BasicDepVisitor;
import entitydepanalyzer.goextractor.godeper.FuncDepVisitor;
import entitydepanalyzer.goextractor.godeper.MapInFun;


public class GoDepLyzer{

    public void identifyDeps() {
        BasicDepVisitor basicDepVisitor = new BasicDepVisitor();
        MapInFun mapInFun = new MapInFun();
        FuncDepVisitor funcDepVisitor = new FuncDepVisitor();
        TypeInfer typeSearch = new TypeInfer();

        //imports, embeded, receiver relations
        basicDepVisitor.setUnsuredDeps();
        System.out.println("Imports, Embed, Receiver relations are built successfully...");

        // build map for operandName inside a function.
        mapInFun.buildNameSearchTable();
        System.out.println("Name searcher table is built successfully...");

        //entitydepanalyzer.goextractor.infer type for all varEntities
        typeSearch.inferTypeForVarEntity();
        System.out.println("Infer type successfully...");

        //typeSearch.output();
        // function Call, Set, Dep, Parameter, Return relations
        funcDepVisitor.setFuncDeps();
        System.out.println("Call, Set, Dep, Parameter, Return relations are built end...");
    }





}
