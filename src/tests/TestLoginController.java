package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.GenericController;
import controllers.LoginController;
import db.Config;
import interfaces.IController;
import jresponseclasses.JReturn;
import views.ViewController;

public class TestLoginController  extends TestCases {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	
	private void setControllerVariables(IController ic){
		ic.addVariable("email","micha.meier.siueg@gmail.com");
		ic.addVariable("senha", "123456");
		ic.getObject().setClassName("Usuario");
	}
	
	
	@Test
	public void testRedirect(){
		
		
		
	}
	
	@Test
	public void testLogin(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "login", as);
		this.setControllerVariables(ic);
		
		//Login with correct credentials
		ic.execute(response, "login");
		assertTrue(response.isSuccess());
		assertTrue(response.getUser().isLoggedin());
		assertEquals(new Integer(4), response.getUser().getId());
		assertEquals("Micha", response.getUser().getUsername());
		
	}
	
	@Test
	public void testWrongEmail(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "login", as);
		this.setControllerVariables(ic);
		
		//Login with wrong email
		ic.addVariable("email", "micha.meier.siue@gmail.com");
		ic.execute(response, "login");
		assertFalse(response.isSuccess());
		assertFalse(response.getUser().isLoggedin());
	}
	
	@Test
	public void testWrongPassword(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "login", as);
		this.setControllerVariables(ic);
		
		//Login with wrong email
		ic.addVariable("senha", "12456");
		ic.execute(response, "login");
		assertFalse(response.isSuccess());
		assertFalse(response.getUser().isLoggedin());
		assertEquals(1, response.getSimpleErrors().size());
	}
	
	
	
	@Test
	public void testLogout(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "login", as);
		
		this.setControllerVariables(ic);
		
		//Login an test if its logged in
		ic.execute(response, "login");
		assertTrue(response.getUser().isLoggedin());
	
		
		//Logout and test if the user is logged out
		ic.execute(response, "logout");
		assertTrue(response.isSuccess());
		assertFalse(response.getUser().isLoggedin());
		assertEquals(null,response.getUser().getId());
		assertEquals(null,response.getUser().getUsername());
		
		
	}
	
}
