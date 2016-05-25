package Reflection;

public class SqlStringUtils {

	public static String getCommaString(String[] stringVect){
		String commaString = "";
		
		for(int i=0 ; i<stringVect.length; i++){
			commaString += "," + stringVect[i];
		}
		
		
		return commaString.substring(1);
	}
	
	public static String[] getStringVector(String commaSepString){
		return commaSepString.split(",");
	}
	
	public static String getPrepStmtColumns(String[] stringVect, String separator){
		String string = "";
		
		for(String s : stringVect){
			string += separator+" "+ s + "=?";
		}
		
		return string.substring(separator.length());
		
	}
	
}
