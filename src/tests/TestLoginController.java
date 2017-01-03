package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.GenericController;
import utils.Config;
import interfaces.IController;
import jresponseclasses.JReturn;

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
	/**
	 * Test if the login is working correct
	 */
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
	/**
	 * Test if the login, recognizes a wrong email and don't login the user
	 */
	public void testWrongEmail(){
		
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "login", as);
		this.setControllerVariables(ic);
		
		//Login with wrong email
		ic.addVariable("email", "micha.meier.siuee@gmail.com");
		ic.execute(response, "login");
		assertFalse(response.isSuccess());
		assertFalse(response.getUser().isLoggedin());
	}
	
	@Test
	/**
	 * Test if the system recognize a wrong password
	 */
	public void testWrongPassword(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "login", as);
		this.setControllerVariables(ic);
		
		//Login with wrong email
		ic.addVariable("senha", "127456");
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
