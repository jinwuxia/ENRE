package multiparser.py3extractor;

import multiparser.py3extractor.antlr4.Python3BaseVisitor;

public class PyEntityVisitor extends Python3BaseVisitor {
    private String fileFullPath;

    public PyEntityVisitor(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

}
