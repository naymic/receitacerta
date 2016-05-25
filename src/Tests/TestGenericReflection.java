package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.City;
import Reflection.GenericReflection;

public class TestGenericReflection {

	@Test
	public void testObject() {
		City c = new City();
		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		
		assertEquals(c, r.getObject());
		
	}
	
	
	@Test
	public void testMethod() {
		City c = new City();
		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		
		try {
			assertEquals(c.getClass().getMethod("dgetName"), r.getMethod("dgetName"));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testMethodValue() {
		City c = new City();
		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		
		assertEquals("TestValueName", (String)r.getMethodValue("dgetName"));
		
	}
	
	@Test
	public void testSetMethod(){
		City c = new City();
		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		
		
		assertTrue(r.setMethodValue("dsetName", "TestValueName1"));
		assertEquals("TestValueName1", c.dgetName());
		
		
	}
	


}
