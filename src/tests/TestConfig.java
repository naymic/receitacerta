package tests;

import org.junit.Before;
import org.junit.Test;

import db.Config;


public class TestConfig extends TestCases {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testCOnfigDB() {
		
		
	}
	
	
	@Test
	public void testConfiTestgDB() {
		Config.getInstance().setTestDB(true);
		
	}
	

}
