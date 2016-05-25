package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Hashtable;

import org.junit.Test;
import org.junit.runner.RunWith;

import GenericDao.DAO;
import Model.City;
import Reflection.ReflectionDAO;


public class TestReflectionDAO {

	public City getCity(){
		City c = new City();
		c.dsetId(1);
		c.dsetCountry("Test1");
		c.dsetName("Test2");
		c.dsetState("123");
		c.dsetType("Tipo1");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	@Test 
	public void testAnnotation(){
		City c = this.getCity();
		ReflectionDAO r = new ReflectionDAO(c);
		
		assertEquals(r.getPKs().get(0), r.getMethod("dgetId"));
	}


	
	@Test
	public void testFieldsDB(){
		City c = this.getCity();
		ReflectionDAO r = new ReflectionDAO(c);
		
		assertEquals(6, r.getFields().size());
		
		Hashtable<String, Object> f = new Hashtable<String, Object>();
		f.put("dgetId", 1);
		f.put("dgetCountry", "Test1");
		f.put("dgetName", "Test2");
		f.put("dgetState", "123");
		f.put("dgetType", "Tipo1");
		f.put("dgetNotice", "Notiz 12345678990");
		
		assertEquals(f.get("dgetId"), r.getFields().get("dgetId"));
		assertEquals(f.get("dgetName"), r.getFields().get("dgetName"));
		assertEquals(f.get("dgetState"), r.getFields().get("dgetState"));
		assertEquals(f.get("dgetType"), r.getFields().get("dgetType"));
		assertEquals(f.get("dgetNotice"), r.getFields().get("dgetNotice"));
		
		
	}
	
	@Test
	public void testInsert(){
		City c = this.getCity();

		assertTrue(DAO.getInstance().insert(c).isSuccess());
		DAO.getInstance().delete(c).isSuccess();
	}
	
	@Test 
	public void testDelete(){
		City c = getCity();
		
		DAO.getInstance().insert(c).isSuccess();
		assertTrue(DAO.getInstance().delete(c).isSuccess());
	}
	
	@Test
	public void testUpdate(){
		City c = getCity();
		
		DAO.getInstance().insert(c);
		
		c.dsetName("Nome update");
		c.dsetCountry("Country update");
		c.dsetNotice("jakljaljdfaj sda update");
		c.dsetType("tipo de cidade update");
		
		assertTrue(DAO.getInstance().update(c).isSuccess());
		DAO.getInstance().delete(c);
	}
	
}
