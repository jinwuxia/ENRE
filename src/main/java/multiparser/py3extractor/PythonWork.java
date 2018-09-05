package multiparser.py3extractor;

import multiparser.entity.Entity;
import multiparser.extractor.SingleCollect;
import multiparser.extractor.TemplateWork;
import multiparser.py3extractor.infer.TypeInfer;
import multiparser.py3extractor.search.NameSearch;
import multiparser.py3extractor.visitor.firstpass.FileParser;
import multiparser.py3extractor.visitor.secondpass.CallVisitor;
import multiparser.py3extractor.visitor.secondpass.DepVisitor;
import multiparser.py3extractor.visitor.secondpass.ImportVisitor;
import multiparser.py3extractor.visitor.secondpass.InheritVisitor;
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

        depVisitor = new InheritVisitor();
        depVisitor.setDep();

        NameSearch nameSearch = NameSearch.getNameSearchInstance();
        nameSearch.buildNameScope();

        for(Entity entity: SingleCollect.getSingleCollectInstance().getEntities()) {
            System.out.println("id:  " +  entity.getId());
            System.out.println("name:" + entity.getName());
            System.out.println(nameSearch.getNameMapOfScope(entity.getId()) + "\n");
        }

        TypeInfer typeInfer = new TypeInfer();
        typeInfer.inferTypeForVarEntity();

        depVisitor = new CallVisitor();
        depVisitor.setDep();



    }
}
