package reflection;

import annotations.AModelClasses;
import model.Model;

public class ReflectionModel {
	
	public static AModelClasses getAnnotationFromModel(Class<?> class1)throws RuntimeException{
		
		try{
			AModelClasses amc = class1.getAnnotation(AModelClasses.class);
			return amc;
			
		}catch(NullPointerException npe){
			System.out.println(npe.getMessage());
			throw new RuntimeException(npe.getMessage());
		}
		
	}
	
	
}
