package discovery;

import annotations.AModelClasses;
import model.Describe;
import reflection.ReflectionDAO;

public class DescribeModel extends Describe{
	String aModel;
	
	DescribeModel(ReflectionDAO rd){
		this.dsetName(rd.getObjectClass().getSimpleName());
		this.setaModel(rd.getObjectClass().getAnnotation(AModelClasses.class).toString());
		
	}

	public String getaModel() {
		return aModel;
	}

	public void setaModel(String aModel) {
		this.aModel = aModel;
	}
	
}
