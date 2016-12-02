package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import jresponseclasses.JReturn;

public class Verifier {
	
	private static Verifier aVerifier;
	
	public Verifier(){
		
	}
	
	public static Verifier getVerifier(){
		if(aVerifier == null){
			return aVerifier = new Verifier();
		}
		return aVerifier;
		
	}
	
	public boolean verifySpecialCharacters(String word){
		String thePattern = "[^A-Za-z0-9À-ú\\s]+";
		boolean isFound = Pattern.compile(thePattern).matcher(word).find();
	    return isFound;
	}
	
	public boolean verifyNumbers(String word){
		return word.matches(".*\\d+.*");
	}
	
	public boolean verifyLetters(String word){
		String thePattern = "[A-Za-z]";
		boolean isFound = Pattern.compile(thePattern).matcher(word).find();
		return isFound;
	}

}
