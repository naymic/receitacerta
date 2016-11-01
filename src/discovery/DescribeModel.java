package discovery;

import annotations.AModelClasses;
import model.Describe;
import reflection.ReflectionAnnotation;
import reflection.ReflectionDAO;

public class DescribeModel extends Describe{
	
	DescribeModel(ReflectionDAO rd){
		this.dsetName(rd.getObjectClass().getSimpleName());
		ReflectionAnnotation ra = new ReflectionAnnotation(rd.getObjectClass().getAnnotation(AModelClasses.class));
		this.setAnnotations(ra.getAnnotationMap());
		
	}

	
}
