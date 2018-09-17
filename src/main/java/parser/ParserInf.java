package parser;

import parser.parsego.GolangLexer;
import parser.parsego.GolangParser;
import parser.parsepy.Python3Lexer;
import parser.parsepy.Python3Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import util.Configure;

import java.io.IOException;

public class ParserInf {

    public ParseTree rootEntry(String fileFullPath) throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Configure configure = Configure.getConfigureInstance();
        String lang = configure.getLang();

        ParseTree tree = null;
        if(lang.equals(Configure.GO_LANG)) {
            GolangLexer lexer = new GolangLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            GolangParser parser = new GolangParser(tokens);
            tree = parser.sourceFile();
        }
        else if(lang.equals(Configure.PYTHON_LANG)) {
            Python3Lexer lexer = new Python3Lexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Python3Parser parser = new Python3Parser(tokens);
            tree = parser.file_input();
        }
        return tree;
    }

}
