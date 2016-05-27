package Model;

import Utils.Return;

public abstract class Model {
	protected String tableName;
	
	protected void setTableName(String tableName){
		this.tableName = tableName;
	}
	
	public abstract String getTableName();
	
	public abstract Return verify();
	
}
