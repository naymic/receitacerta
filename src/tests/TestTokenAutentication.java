package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import db.Config;
import jrequestclasses.JRequest;
import jresponseclasses.JReturn;
import views.ViewController;

public class TestTokenAutentication extends TestCases{
	
	@Override
	public void initTestDatabase() {
		Config.getInstance().setTestDB(false);
	}
	
	@Test
	public void testTokenAutentication() {
		JRequest requ = new JRequest();
		requ.setAction("busca");
		requ.setClassname("TestIngredientes");
		requ.setUsecase("crud");
		requ.setToken("micha.meier.siueg@gmail.com_|_123456");
		
		JReturn response = new JReturn();
		
		ApplicationSessionForTests ast = new ApplicationSessionForTests();
		
		ViewController vc = new ViewController();
		vc.process(requ, response, ast);
		
		assertTrue(response.isSuccess());
		
		
	}

	

}
