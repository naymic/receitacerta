package Utils;

import java.util.ArrayList;

public class Transform {

	public static <E> ArrayList<E> vectorToArray(E[] objects){
		ArrayList<E> list = new ArrayList<>();
		
		for(E object: objects){
			list.add(object);
		}
		
		return list;
	}
	
}
