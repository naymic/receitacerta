package Model;

import java.lang.reflect.Method;
import java.util.ArrayList;

import Reflection.ReflectionDAO;
import Utils.Return;

public abstract class Model {
	protected String tableName;
	
	protected void setTableName(String tableName){
		this.tableName = tableName;
	}
	
	public abstract String getTableName();
	
	public Return verify(){
		Return r = new Return();
		ReflectionDAO rd = new ReflectionDAO(this);
		
		ArrayList<Method> getMethods = rd.getGetMethods();
		for (int i = 0; i < getMethods.size(); i++) {
			Method m = getMethods.get(i);
			if(rd.isRequired(m) && rd.isValueNull(m)){
				r.addError("Attribute "+rd.getColumnName(m)+" is required but null in object " + rd.getObject().getClass());
			}
			
		}
		
		return r;
	}
	
}
