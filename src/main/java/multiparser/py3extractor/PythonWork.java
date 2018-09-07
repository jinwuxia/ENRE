package multiparser.py3extractor;

import multiparser.entity.Entity;
import multiparser.extractor.SingleCollect;
import multiparser.extractor.TemplateWork;
import multiparser.py3extractor.infer.TypeInfer;
import multiparser.py3extractor.search.NameSearch;
import multiparser.py3extractor.visitor.firstpass.FileParser;
import multiparser.py3extractor.visitor.secondpass.*;
import multiparser.util.Configure;
import multiparser.util.FileUtil;

import java.io.IOException;

public class PythonWork extends TemplateWork {

    @Override
    protected void identifyEntities() {
        FileUtil fileUtil = new FileUtil(configure.getInputSrcPath());
        for (String fileFullPath : fileUtil.getFileNameList(Configure.PY_PRO_SUFFIX)) {
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
        System.out.println("Name search finished successfully\n");

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
