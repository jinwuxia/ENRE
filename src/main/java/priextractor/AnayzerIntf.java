package priextractor;

import priextractor.goextractor.GoDepLyzer;
import priextractor.py3extractor.PyDepLyzer;
import util.Configure;

public class AnayzerIntf {

    public void run() {
        Configure configure = Configure.getConfigureInstance();
        String lang = configure.getLang();

        if(lang.equals(Configure.GO_LANG)) {
            GoDepLyzer goDepLyzer = new GoDepLyzer();
            goDepLyzer.identifyDeps();
        }
        else if(lang.equals(Configure.PYTHON_LANG)) {
            PyDepLyzer pyDepLyzer = new PyDepLyzer();
            pyDepLyzer.identifyDeps();
        }
    }
}
