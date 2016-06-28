package Model;

import java.lang.reflect.Method;
import java.util.ArrayList;

import Reflection.ReflectionDAO;
import Utils.Return;

public abstract class Model {
	
	public abstract String getTableName();
	
	//public abstract Return verify(Return r, boolean checkSuper);
	
	public Return verify(Return r){
		ReflectionDAO rd = new ReflectionDAO(this);
		
		ArrayList<Method> getMethods = rd.getGetMethods();
		for (int i = 0; i < getMethods.size(); i++) {
			Method m = getMethods.get(i);
			if(rd.isRequired(m) && !rd.isPK(m) && (rd.getMethodValue(m) == null || rd.getMethodValue(m).toString().length() == 0)){
				r.addAttributeError(rd.getObject().getClass().getSimpleName(),rd.getColumnName(m),"Attribute: "+ rd.getColumnName(m) +" is required but null in object ");
			}
			
		}
		return r;
		
	}
	
	
	
}
