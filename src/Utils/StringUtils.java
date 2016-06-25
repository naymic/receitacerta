package Utils;

public class StringUtils {

	/**
	 * Returns a string vector as a comma separated string
	 * @param stringVect
	 * @return
	 */
	public static String getCommaString(String[] stringVect){
		String commaString = "";
		
		for(int i=0 ; i<stringVect.length; i++){
			commaString += "," + stringVect[i];
		}
		
		
		return commaString.substring(1);
	}
	
	/**
	 * Returns a comma separated string as a string vector
	 * @param commaSepString
	 * @return
	 */
	public static String[] getStringVector(String commaSepString){
		return commaSepString.split(",");
	}
	
	/**
	 * Separate a string vector by a needle and add =? (Used for SQL Prepared Statements)
	 * @param stringVect
	 * @param separator
	 * @return
	 */
	public static String getPrepStmtColumns(String[] stringVect, String separator){
		String string = "";

		for(String s : stringVect){
			string += separator+" "+ s + "=?";
		}
		
		return string.substring(separator.length());
		
	}
	
	
	/**
	 * Return a string removing all non number chars
	 * @param mixedString
	 * @return
	 */
	public static String justNumbers(String mixedString){
		return mixedString.replaceAll("\\D+", "");
	}
	
}
