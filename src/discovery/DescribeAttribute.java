package discovery;

import java.lang.reflect.Method;

import annotations.AEntity;
import enums.EMType;
import model.Describe;
import model.Model;
import reflection.ReflectionAnnotation;
import reflection.ReflectionDAO;

public class DescribeAttribute extends Describe{
	String attributeClassName;


	public DescribeAttribute(ReflectionDAO rd, Method method){
		this.dsetName(rd.getAttributeNameFromMethod(method));
		
		AEntity e = rd.getEntity(method);
		ReflectionAnnotation ra = new ReflectionAnnotation(e);
		this.setAnnotations(ra.getAnnotationMap());
		
		
		this.setAttributeClassName(rd.getMethodValueClass(method).getSimpleName());
	}
	
	public String getAttributeClassName() {
		return attributeClassName;
	}

	public void setAttributeClassName(String attributeClass) {
		this.attributeClassName = attributeClass;
	}


	

	
}
