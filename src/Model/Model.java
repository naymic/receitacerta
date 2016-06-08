package Model;

import java.lang.reflect.Method;
import java.util.ArrayList;

import Reflection.ReflectionDAO;
import Utils.Return;

public abstract class Model {
	
	public abstract String getTableName();
	
	public Return verify(){
		Return r = new Return();
		ReflectionDAO rd = new ReflectionDAO(this);
		
		ArrayList<Method> getMethods = rd.getGetMethods();
		for (int i = 0; i < getMethods.size(); i++) {
			Method m = getMethods.get(i);
			if(rd.isRequired(m) && rd.isValueNull(m)){
				r.addAttributeError(rd.getObject().getClass().getSimpleName(),rd.getColumnName(m),"Attribute: "+ rd.getColumnName(m) +" is required but null in object ");
			}
			
		}
		return r;
		
	}
	
}
