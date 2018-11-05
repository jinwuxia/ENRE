package entitybuilder;

import parser.ParserInf;
import entitybuilder.gobuilder.govisitor.GoEntityVisitor;
import entitybuilder.pybuilder.pyvisitor.PyEntityVisitor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import util.Configure;
import util.FileUtil;

import java.io.IOException;

public class BuilderIntf {

    private Configure configure = Configure.getConfigureInstance();
    private AbstractParseTreeVisitor visitor = null; //from this package
    private ParseTree tree = null;

    /**
     * get visitor from current package
     * @param fileFullPath
     */
    private void setVisitor(String fileFullPath) {
        if(configure.getLang().equals(Configure.GO_LANG)) {
            if(!fileFullPath.endsWith("_test.go")) {
                visitor = new GoEntityVisitor(fileFullPath);
            }
        }
        else if (configure.getLang().equals(Configure.PYTHON_LANG)) {
            if(!fileFullPath.endsWith("_test.py")) {
                visitor = new PyEntityVisitor(fileFullPath);
            }
        }
    }

    /**
     * get tree from parser package (antlr4 package)
     * @param fileFullPath
     */
    private void setTree(String fileFullPath) {
        ParserInf parserInterface = new ParserInf();
        try {
            tree = parserInterface.rootEntry(fileFullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void run(){
        FileUtil fileUtil = new FileUtil(configure.getInputSrcPath());
        for (String fileFullPath : fileUtil.getFileNameList(configure.getCurr_pro_suffix())) {

            setTree(fileFullPath);
            setVisitor(fileFullPath);

            if(tree != null && visitor != null) {
                System.out.println(fileFullPath);
                visitor.visit(tree);
            }
            tree = null;
            visitor = null;
        }
        System.out.println("Identify entities successfully...");


    }


}
