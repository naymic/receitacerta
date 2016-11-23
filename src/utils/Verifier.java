package utils;

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
		return word.matches("\\W");
	}
	
	public boolean verifyNumbers(String word){
		return word.matches("\\d");
	}

}
