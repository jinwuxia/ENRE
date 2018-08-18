package multiparser.goextractor;

import multiparser.goextractor.visitor.firstpass.FileParser;
import multiparser.extractor.TemplateWork;
import multiparser.goextractor.infer.TypeInfer;
import multiparser.util.Configure;
import multiparser.util.FileUtil;
import multiparser.goextractor.visitor.secondpass.BasicDepVisitor;
import multiparser.goextractor.visitor.secondpass.FuncDepVisitor;
import multiparser.goextractor.visitor.secondpass.MapInFun;

import java.io.IOException;

public class GolangWork extends TemplateWork {

    @Override
    protected void identifyEntities() {
        FileUtil fileUtil = new FileUtil(configure.getInputSrcPath());
        for (String fileFullPath : fileUtil.getFileNameList(Configure.GO_PRO_SUFFIX)) {
            System.out.println(fileFullPath);
            FileParser fileParser = new FileParser(fileFullPath);
            try {
                fileParser.parserOneFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Identify entities successfully...");
    }


    @Override
    protected void identifyDeps() {
        BasicDepVisitor basicDepVisitor = new BasicDepVisitor();
        MapInFun mapInFun = new MapInFun();
        FuncDepVisitor funcDepVisitor = new FuncDepVisitor();
        TypeInfer typeSearch = new TypeInfer();

        //imports, embeded, receiver relations
        basicDepVisitor.setUnsuredDeps();
        System.out.println("Imports, Embed, Receiver relations are built successfully...");

        // build map for operandName inside a function.
        mapInFun.buildNameSearchTable();
        System.out.println("Name search table is built successfully...");

        //multiparser.goextractor.infer type for all varEntities
        typeSearch.inferTypeForVarEntity();
        System.out.println("Infer type successfully...");

        //typeSearch.output();
        // function Call, Set, Dep, Parameter, Return relations
        funcDepVisitor.setFuncDeps();
        System.out.println("Call, Set, Dep, Parameter, Return relations are built end...");
    }





}
