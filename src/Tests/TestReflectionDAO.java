package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Test;

import com.google.gson.Gson;

import GenericDao.DAO;
import JsonClasses.JReturn;
import Model.TestCity;
import Model.Model;
import Reflection.ReflectionDAO;


public class TestReflectionDAO {

	public TestCity getCity(){
		TestCity c = new TestCity();
		
		c.dsetId(3);
		c.dsetCountry("CH");
		c.dsetName("Luzern");
		c.dsetState("ZH");
		c.dsetType("cidade");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	public TestCity getCity1(){
		TestCity c = new TestCity();
		
		c.dsetId(4);
		c.dsetCountry("CH");
		c.dsetName("Bern");
		c.dsetState("BE");
		c.dsetType("capital");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	public TestCity getCity2(){
		TestCity c = new TestCity();
		
		c.dsetId(5);
		c.dsetCountry("BR");
		c.dsetName("Brasília");
		c.dsetState("123");
		c.dsetType("capital");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	public TestCity getCity3(){
		TestCity c = new TestCity();
		
		c.dsetId(6);
		c.dsetCountry("BR");
		c.dsetName("Joinville");
		c.dsetState("123");
		c.dsetType("cidade");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	public TestCity getCity4(){
		TestCity c = new TestCity();
		
		
		c.dsetCountry("CH");
		c.dsetName("Basel");
		c.dsetState("BS");
		c.dsetType("cidade");
		c.dsetNotice("Notiz 12345678990");
		return c;
	}
	
	@Test 
	public void testAnnotation(){
		TestCity c = this.getCity();
		ReflectionDAO r = new ReflectionDAO(c);
		
		assertEquals(r.getGetPKs().get(0), r.getMethod("dgetId"));
	}


	
	@Test
	public void testFieldsDB(){
		TestCity c = this.getCity();
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
		TestCity c = this.getCity();
		JReturn r = new JReturn();
		assertFalse(DAO.getTestInstance().existModel(c, r));
		DAO.getTestInstance().save(c, r);
		
		
		assertTrue(DAO.getTestInstance().existModel(c, r));
		DAO.getTestInstance().delete(c, r);
	}
	
	@Test
	public void testInsert(){
		TestCity c = this.getCity();
		TestCity c1 = this.getCity4();
		
		JReturn r = new JReturn();
		assertTrue(DAO.getTestInstance().save(c, r).isSuccess());
		r = new JReturn();
		DAO.getTestInstance().delete(c , r).isSuccess();
		r = new JReturn();
		assertTrue(DAO.getTestInstance().save(c1, r).isSuccess());
		r = new JReturn();
		DAO.getTestInstance().delete(c1, r);
	}
	
	@Test 
	public void testDelete(){
		TestCity c = getCity();
		
		JReturn r = new JReturn();
		DAO.getTestInstance().save(c, r);
		r = new JReturn();
		assertTrue(DAO.getTestInstance().delete(c, r).isSuccess());
		
	}
	
	@Test
	public void testUpdate(){
		TestCity c = getCity();
		
		JReturn r = new JReturn();
		DAO.getTestInstance().save(c,r);
		
		c.dsetName("Nome update");
		c.dsetCountry("Country update");
		c.dsetNotice("jakljaljdfaj sda update");
		c.dsetType("tipo de cidade update");
		
		r = new JReturn();
		assertTrue(DAO.getTestInstance().save(c,r).isSuccess());
		r = new JReturn();
		DAO.getTestInstance().delete(c, r);
	}
	
	@Test
	public void testSelect(){
		ArrayList<Model> lista = new ArrayList<>();
		DAO d = DAO.getTestInstance();
		JReturn r = new JReturn();
		d.save(this.getCity(), r);
		r = new JReturn();
		d.save(this.getCity1(), r);
		r = new JReturn();
		d.save(this.getCity2(), r);
		r = new JReturn();
		d.save(this.getCity3(), r);
		TestCity c1 = null;
		TestCity c = new TestCity();
		
		c.dsetId(this.getCity().dgetId());
		lista = DAO.getTestInstance().select(c);
		c = (TestCity)lista.get(0);
		assertEquals(this.getCity().dgetId(), c.dgetId());
		assertEquals(this.getCity().dgetName(), c.dgetName());
		
		
		c = new TestCity();

		c.dsetCountry("BR");
		lista = DAO.getInstance().select(c);
		assertEquals(2, lista.size());
		c = (TestCity) lista.get(0);
		c1 = (TestCity) lista.get(1);

		assertEquals(this.getCity2().dgetId(), c.dgetId());
		assertEquals(this.getCity2().dgetNotice(), c.dgetNotice());
		
		assertEquals(this.getCity3().dgetId(), c1.dgetId());
		assertEquals(this.getCity3().dgetNotice(), c1.dgetNotice());
		
		r = new JReturn();
		d.delete(this.getCity(),r );
		r = new JReturn();
		d.delete(this.getCity1(),r);
		r = new JReturn();
		d.delete(this.getCity2(),r);
		r = new JReturn();
		d.delete(this.getCity3(),r);
	}
	
	@Test
	public void testVerify(){
		TestCity c = this.getCity();
		JReturn r = new JReturn();
		c.verify(r);
		c.dsetNotice(null);
		assertTrue(r.isSuccess());
		r = new JReturn();
		DAO.getTestInstance().save(c, r);
		r = new JReturn();
		DAO.getTestInstance().delete(c, r);
		
		
		
		c.dsetCountry(null);
		c.dsetState(null);
		r = new JReturn();
		r = c.verify(r);
		assertFalse(r.isSuccess());
		
		Gson g = new Gson();
		
		System.out.println(g.toJson(r));
	}
	
}
