package entitybuilder.pybuilder.pyvisitor;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csvreader.CsvReader;

import writer.CsvWriter;

public class Fileoperation{
	
	
	public static String readTxtLine(String txtPath, int lineNo,int lastlineNo, int rowNo, int lastrowNo) {
		 
        String line = "";
        String finalline = "";
        String encoding="GBK";
        try {
            File txtFile = new File(txtPath);
            InputStream in = new FileInputStream(txtFile);
            InputStreamReader read = new InputStreamReader(in,encoding);
            BufferedReader reader = new BufferedReader(read);
            int i = 0;
            while (i < lineNo ) {
                line = reader.readLine();
                if(i == lineNo - 1) {
                	if(lineNo == lastlineNo) {
                		return line.substring(rowNo,lastrowNo);
                	}
                	finalline = line.substring(rowNo,line.length()-1);
                	i++;
                	while(i < lastlineNo) {
                		line = reader.readLine();
                		if(i == lastlineNo - 1) {
                			finalline += line.substring(0,lastrowNo);
                		}
                		else finalline += line;
                		i++;
                	}
                }
                i++;
            }
            reader.close();
        } catch (Exception e) {
        }
        return finalline;
    }
	
	 

}


