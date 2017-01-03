package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import utils.Config;
import model.TestCity;
import reflection.GenericReflection;

public class TestGenericReflection extends TestCases {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testObject() {
		TestCity c = new TestCity();
		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		
		assertEquals(c, r.getObject());
		
	}
	
	
	@Test
	public void testMethod() {
		TestCity c = new TestCity();
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
		TestCity c = new TestCity();
		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		
		assertEquals("TestValueName", (String)r.getMethodValue("dgetName"));
		
	}
	
	@Test
	public void testSetMethod(){
		TestCity c = new TestCity();
		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		
		
		assertTrue(r.setMethodValue("dsetName", "TestValueName1"));
		assertEquals("TestValueName1", c.dgetName());
		
		
	}
	
	@Test
	public void testGetFieldValue() {
		
		TestCity c = new TestCity();

		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		
		assertEquals(c.dgetName(), r.getFieldValue("name"));
	}
	
	
	@Test
	public void testSetFieldValue() {
		
		TestCity c = new TestCity();

		c.dsetName("TestValueName");
		GenericReflection r = new GenericReflection(c);
		r.setFieldValue("name", "testString");
		assertEquals(c.dgetName(), "testString");
	}
	
	


}
