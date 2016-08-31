package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.LoginController;
import db.Config;

public class TestLoginController  extends TestCases {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}

	
	@Test
	public void testRedirect(){
		
		
		
			
		
	}
	
}
