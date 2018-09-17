package entitytreebuilder.pybuilder.pyvisitor;


import parser.parsepy.Python3Lexer;
import parser.parsepy.Python3Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class FileParser {
    private String fileFullPath;
    public FileParser(String str) {
        this.fileFullPath = str;
    }

    public void parserOneFile() throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Python3Lexer lexer = new Python3Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokens);
        ParseTree tree = parser.file_input(); // root rule: file_input

        PyEntityVisitor entityVisitor = new PyEntityVisitor(fileFullPath);
        entityVisitor.visit(tree);
    }
}
