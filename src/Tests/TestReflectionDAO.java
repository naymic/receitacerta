package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Test;
import GenericDao.DAO;
import Model.TestCity;
import Model.Model;
import Reflection.ReflectionDAO;
import Utils.Return;


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
		
		assertFalse(DAO.getTestInstance().existModel(c));
		DAO.getTestInstance().save(c);
		
		
		assertTrue(DAO.getTestInstance().existModel(c));
		DAO.getTestInstance().delete(c);
	}
	
	@Test
	public void testInsert(){
		TestCity c = this.getCity();
		TestCity c1 = this.getCity4();

		assertTrue(DAO.getTestInstance().save(c).isSuccess());
		DAO.getTestInstance().delete(c).isSuccess();
		
		assertTrue(DAO.getTestInstance().save(c1).isSuccess());
		DAO.getTestInstance().delete(c1);
	}
	
	@Test 
	public void testDelete(){
		TestCity c = getCity();
		
		DAO.getTestInstance().save(c);
		assertTrue(DAO.getTestInstance().delete(c).isSuccess());
		
	}
	
	@Test
	public void testUpdate(){
		TestCity c = getCity();
		
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
		
		d.delete(this.getCity());
		d.delete(this.getCity1());
		d.delete(this.getCity2());
		d.delete(this.getCity3());
	}
	
	@Test
	public void testVerify(){
		TestCity c = this.getCity();
		Return r = new Return();
		c.verify(r);
		c.dsetNotice(null);
		assertTrue(r.isSuccess());
		
		DAO.getTestInstance().save(c);
		DAO.getTestInstance().delete(c);
		
		
		
		c.dsetCountry(null);
		c.dsetState(null);
		r = new Return();
		r = c.verify(r);
		assertFalse(r.isSuccess());
		System.out.println(r.getMessageMap().toString());
	}
	
}
