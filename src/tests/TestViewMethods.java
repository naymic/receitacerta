package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.CrudController;
import controllers.GenericController;
import utils.Config;
import interfaces.IController;
import jrequestclasses.JRequest;
import jresponseclasses.JReturn;
import model.Ingredientes;
import views.ViewController;

public class TestViewMethods extends TestCases{
	
	
	
	
	
	
	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testGetController() {
		ViewController vc = new ViewController();
		JReturn r = new JReturn();
		Class<?> c = CrudController.class;
		IController ic = GenericController.getController(r, "Crud", null);
		assertEquals(ic.getClass(), CrudController.class);
		assertTrue(r.isSuccess());
		
		ic = GenericController.getController(r, "BliBla", null);
		assertFalse(r.isSuccess());
		assertEquals(1, r.getSimpleErrors().size());
		
	}
	
	
	@Test
	public void testRedirect() {
		ViewController vc = new ViewController();
		JReturn r = new JReturn();
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		
	
		JRequest jrequ = new JRequest();
		jrequ.setAction("busca");
		jrequ.setUsecase("Crud");
		jrequ.setClassname("Ingredientes");
		
		
		Config.getInstance().setTestDB(false);
		vc.process(jrequ, r, as);
		Config.getInstance().setTestDB(true);
		
		
		assertFalse(r.isSuccess());
		assertEquals("login", r.getRedirect().getAction());
		assertEquals("Usuario", r.getRedirect().getClassname());
		assertEquals("login",r.getRedirect().getUsecase());
		
	}
	
	
	@Test 
	public void testCompareObject(){
		Ingredientes ing1 = new Ingredientes();
		Ingredientes ing2 = new Ingredientes();
		
		
		ing1.dsetId(1);
		ing2.dsetId(1);
		ing1.dsetNome("G");
		ing2.dsetNome("G");
		
		if(ing1.equals(ing2))
			assertTrue(true);
	}
	


}
