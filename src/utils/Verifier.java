package utils;

import java.util.regex.Pattern;

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
		String thePattern = "[+_=)({}#$%&*]";
		boolean isFound = Pattern.compile(thePattern).matcher(word).find();
	    return isFound;
	}
	
	public boolean verifyNumbers(String word){
		return word.matches("[0-9]");
	}
	
	public boolean verifyLetters(String word){
		String thePattern = "[A-Za-z]";
		boolean isFound = Pattern.compile(thePattern).matcher(word).find();
		return isFound;
	}

}
