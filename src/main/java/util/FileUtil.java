package util;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {

    private ArrayList<String> fileNameList = new ArrayList<String>();

    public FileUtil(String dirName) {
        RecursiveFindFile(dirName);
    }

    public void RecursiveFindFile(String dirName)
    {
        //System.out.println(dirName);
        File dir = new File(dirName);
        if(dir.isFile()) {
            this.fileNameList.add(dir.getAbsolutePath());
            return;
        }
        for (File eachFile : dir.listFiles())
        {
            if(eachFile.isFile())
            {
                this.fileNameList.add(eachFile.getAbsolutePath());
            }
            else if(eachFile.isDirectory())
            {
                RecursiveFindFile(eachFile.getAbsolutePath());
            }
        }
    }


    public ArrayList<String> getFileNameList(String suffix)
    {
        ArrayList<String> filePathList = new ArrayList<String>();
        for(String fileName : this.fileNameList)
        {
            if(fileName.endsWith(suffix))
            {
                filePathList.add(fileName);
            }
        }
        return filePathList;
    }
}
