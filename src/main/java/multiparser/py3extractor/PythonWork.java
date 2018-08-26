package multiparser.py3extractor;

import multiparser.extractor.TemplateWork;
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
        BasicDepVisitor basicDepVisitor = new BasicDepVisitor();
        basicDepVisitor.setDep();
    }
}
