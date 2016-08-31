package utils;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Transform {

	public static <E> ArrayList<E> vectorToArray(E[] objects){
		ArrayList<E> list = new ArrayList<>();
		
		for(E object: objects){
			list.add(object);
		}
		
		return list;
	}
	
	
	public static String objectToJson(Object object){
		Gson g = new Gson();
		return g.toJson(object);
	}
	
	public static <E> E jsonToObject(String json, E object){
		Gson g = new Gson();
		object = (E) g.fromJson(json, object.getClass());
		
		return object;
	}
	
	
}
