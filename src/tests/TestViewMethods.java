package tests;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import controllers.CrudController;
import db.Config;
import interfaces.IController;
import jsonclasses.JReturn;
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
		IController ic = vc.getController(r, "Crud", null);
		assertEquals(ic.getClass(), CrudController.class);
		assertTrue(r.isSuccess());
		
		ic = vc.getController(r, "BliBla", null);
		assertFalse(r.isSuccess());
		assertEquals(1, r.getSimpleErrors().size());
		
	}
	
	
	@Test
	public void testRedirect() {
		ViewController vc = new ViewController();
		JReturn r = new JReturn();
		
	
		
		
		
	}
	


}
