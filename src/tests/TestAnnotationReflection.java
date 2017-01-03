package tests;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import annotations.AEntity;
import utils.Config;
import enums.EMType;
import model.Ingredientes;
import reflection.ReflectionDAO;
import reflection.ReflectionAnnotation;

public class TestAnnotationReflection extends TestCases {

	@Test
	public void testAnnotationReflection() {
		Ingredientes ingr = new Ingredientes();
		ReflectionDAO rd = new ReflectionDAO(ingr);
		
		AEntity e = rd.getEntity(rd.getMethodByFieldname("id", EMType.get, null));
		ReflectionAnnotation ra = new ReflectionAnnotation(e);
		
		HashMap<String, Object> map = ra.getAnnotationMap();
		map.getClass();
		//equals, toString, hashCode, annotationType;
	}

	@Before
	public void initTestDatabase() {
		Config.getInstance().setTestDB(true);		
	}
	
	
}
