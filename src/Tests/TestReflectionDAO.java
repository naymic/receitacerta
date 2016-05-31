package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Test;
import GenericDao.DAO;
import Model.City;
import Model.Model;
import Reflection.ReflectionDAO;


public class TestReflectionDAO {

	public City getCity(){
		City c = new City();
		
		c.dsetId(3);
		c.dsetCountry("CH");
		c.dsetName("Luzern");
		c.dsetState("ZH");
		c.dsetType("cidade");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	public City getCity1(){
		City c = new City();
		
		c.dsetId(4);
		c.dsetCountry("CH");
		c.dsetName("Bern");
		c.dsetState("BE");
		c.dsetType("capital");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	public City getCity2(){
		City c = new City();
		
		c.dsetId(5);
		c.dsetCountry("BR");
		c.dsetName("Brasília");
		c.dsetState("123");
		c.dsetType("capital");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	public City getCity3(){
		City c = new City();
		
		c.dsetId(6);
		c.dsetCountry("BR");
		c.dsetName("Joinville");
		c.dsetState("123");
		c.dsetType("cidade");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	@Test 
	public void testAnnotation(){
		City c = this.getCity();
		ReflectionDAO r = new ReflectionDAO(c);
		
		assertEquals(r.getGetPKs().get(0), r.getMethod("dgetId"));
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
		DAO.getTestInstance().delete(c);
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
	
	@Test
	public void testSelect(){
		ArrayList<Model> lista = new ArrayList<>();
		DAO d = DAO.getTestInstance();
		d.save(this.getCity());
		d.save(this.getCity1());
		d.save(this.getCity2());
		d.save(this.getCity3());
		
		City c = new City();
		c.dsetId(this.getCity().dgetId());
		DAO.getTestInstance().select(c);
		assertEquals(this.getCity().dgetId(), c.dgetId());
		assertEquals(this.getCity().dgetName(), c.dgetName());
		
		
		c = new City();
		c.dsetCountry("BR");
		lista = DAO.getInstance().select(c);
		assertEquals(2, lista.size());
		
		d.delete(this.getCity());
		d.delete(this.getCity1());
		d.delete(this.getCity2());
		d.delete(this.getCity3());
	}
	
}
