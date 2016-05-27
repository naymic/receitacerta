package Tests;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;
import GenericDao.DAO;
import Model.City;
import Reflection.ReflectionDAO;


public class TestReflectionDAO {

	public City getCity(){
		City c = new City();
		
		c.dsetId(3);
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
		f.put("dgetId", c.dgetId());
		f.put("dgetCountry", c.dgetCountry());
		f.put("dgetName", c.dgetName());
		f.put("dgetState", c.dgetState());
		f.put("dgetType", c.dgetType());
		f.put("dgetNotice", c.dgetNotice());
		
		assertEquals(f.get("dgetId"), r.getFields().get("dgetId"));
		assertEquals(f.get("dgetName"), r.getFields().get("dgetName"));
		assertEquals(f.get("dgetState"), r.getFields().get("dgetState"));
		assertEquals(f.get("dgetType"), r.getFields().get("dgetType"));
		assertEquals(f.get("dgetNotice"), r.getFields().get("dgetNotice"));
		
		
	}
	
	@Test
	public void testExistModel(){
		City c = this.getCity();
		
		assertFalse(DAO.getTestInstance().existModel(c));
		DAO.getTestInstance().save(c);
		
		
		assertTrue(DAO.getTestInstance().existModel(c));
		DAO.getInstance().delete(c);
	}
	
	@Test
	public void testInsert(){
		City c = this.getCity();

		assertTrue(DAO.getTestInstance().save(c).isSuccess());
		DAO.getTestInstance().delete(c).isSuccess();
	}
	
	@Test 
	public void testDelete(){
		City c = getCity();
		
		DAO.getTestInstance().save(c);
		assertTrue(DAO.getTestInstance().delete(c).isSuccess());
		
	}
	
	@Test
	public void testUpdate(){
		City c = getCity();
		
		DAO.getTestInstance().save(c);
		
		c.dsetName("Nome update");
		c.dsetCountry("Country update");
		c.dsetNotice("jakljaljdfaj sda update");
		c.dsetType("tipo de cidade update");
		
		assertTrue(DAO.getTestInstance().save(c).isSuccess());
		DAO.getTestInstance().delete(c);
	}
	
}
