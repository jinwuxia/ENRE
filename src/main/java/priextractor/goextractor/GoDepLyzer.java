package priextractor.goextractor;

import priextractor.goextractor.infer.TypeInfer;
import priextractor.goextractor.godeper.BasicDepVisitor;
import priextractor.goextractor.godeper.FuncDepVisitor;
import priextractor.goextractor.godeper.MapInFun;


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

        //priextractor.goextractor.infer type for all varEntities
        typeSearch.inferTypeForVarEntity();
        System.out.println("Infer type successfully...");

        //typeSearch.output();
        // function Call, Set, Dep, Parameter, Return relations
        funcDepVisitor.setFuncDeps();
        System.out.println("Call, Set, Dep, Parameter, Return relations are built end...");
    }





}
