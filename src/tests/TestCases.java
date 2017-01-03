package tests;

import org.junit.Before;


public abstract class TestCases {

	/**
	 * Used to initialize the testdatabase 
	 * "@Before initTestDatabase(){
	 * 		Config.getInstance().setTestDB(true); 
	 * }"
	 */
	@Before
	public abstract void initTestDatabase();
	

}
