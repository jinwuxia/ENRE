package entitybuilder.pybuilder.pyvisitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacer {
	/**
	 * Replace the part of code
	 */
	public String replace(String input) {
		return replace_var(replace_string(input));
	}
	/**
	 * use the regularization method theory replace string in API
	 * The case of regular recognition:
	 * (Identify the contents of double quotes)
	 * just like [ print("hello world!") ] it will be recognized as [ hello world! ]
	 */
	public String replace_string (String testlist) {
		String REGEX = "\"(.*?)\"";
		String REPLACE = "Literal";
		    
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(testlist);
		StringBuffer sb = new StringBuffer();
		while(m.find()){
			m.appendReplacement(sb,REPLACE);
		}
		m.appendTail(sb);
 		   
		return sb.toString();
	}
	
	/**
	 * use the regularization method theory replace variable in API
	 * The case of regular recognition:
	 * (Identify the contents with lower case letters, upper case letters, number and underline, but cannot be only number)
	 * just like " var ", " test_1 " can be recognited
	 * but number 1, 2 cannot be recognited
	 */
	public String replace_var (String testlist) {
		    
		Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");  
		Matcher matcher = pattern.matcher(testlist);
		
		StringBuffer sb = new StringBuffer();
		int i = 0;
		String res = "VAR";
		while(matcher.find()){
			
			String res_in = matcher.group();
			Pattern pattern_in = Pattern.compile("(?![0-9]+$)[A-Za-z0-9_]+");
			Matcher matcher_in = pattern_in.matcher(res_in);
			StringBuffer sb2 = new StringBuffer();
			while(matcher_in.find()) {
				if(matcher_in.group().equals("Literal")) continue;
				matcher_in.appendReplacement(sb2, res + String.valueOf(i));
				i++;
			}
			matcher_in.appendTail(sb2);
			res_in = sb2.toString();
			matcher.appendReplacement(sb, res_in);
	   
		}
		matcher.appendTail(sb);
		
		return sb.toString();
	}

}
