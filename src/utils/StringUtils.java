package utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import model.Model;
import reflection.ReflectionDAORelation;

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
	 * Returns a substituted vector as a comma separated string
	 * @param stringVect
	 * @return
	 */
	public static String getSubstitueCommaString(String[] stringVect, String substitute){
		String commaString = "";
		
		for(int i=0 ; i<stringVect.length; i++){
			commaString += "," + substitute;
		}
		
		
		return commaString.substring(1);
	}
	
	/**
	 * Returns a substituted vector as a comma separated string
	 * @param stringVect
	 * @return
	 */
	public static String getSubstitueCommaString(ArrayList<String> stringVect, String substitute){
		String commaString = "";
		
		for(String s : stringVect){
			commaString += "," + substitute;
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
	 * @param search 
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
	 * Separate a string vector by a needle and add =? (Used for SQL Prepared Statements)
	 * @param stringVect
	 * @param separator
	 * @param search 
	 * @return
	 */
	public static String getPrepStmtColumnsSearch(ArrayList<Class<?>> classes,String[] stringVect){
		String string = "";
		String separator =" AND";
		
		
		for(int i=0; i < classes.size(); i++){
			if(classes.get(i) == String.class)
				string += separator+" "+ stringVect[i] + " LIKE ?";
			else
				string += separator+" "+ stringVect[i] + " = ?";
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
	
	
	public static String searchString(Model object){
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		String searchParams = "";
		
		for(Method m : rdr.getGetMethods()){
			if(rdr.getMethodValue(m) != null)
				searchParams += ", "+rdr.getMethodValue(m);
		}
		
		
		return searchParams.substring(2);		
	}
	
	/**
	 * Changes the First letter to Uppercase
	 * @param s
	 * @return
	 */
	public static String setFirstLetterUppercase(String s){
		String s1 = s.substring(0, 1).toUpperCase();
		return s1 + s.substring(1); 
	}
	
	
	
	/**
	 * Method used to calculate the page limit
	 * @param page
	 * @return
	 */
	public static String getPageLimit(Integer page, Integer linesPerPage){
		Integer lineMin = page*linesPerPage-linesPerPage;
		Integer lineMax = linesPerPage;
		return " Limit "+lineMin+","+ lineMax+" " ;
	}
	
}
