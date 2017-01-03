package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.GenericController;
import utils.Config;
import interfaces.IController;
import jresponseclasses.JReturn;

public class TestUserController {

	Integer id;
	
	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	
	private void setControllerVariables(IController ic){
		ic.addVariable("email","test@test.com");
		ic.addVariable("newSenha", "123456");
		ic.addVariable("repeatedSenha", "123456");
		ic.addVariable("nome","Thiago");
		ic.addVariable("sobrenome","Miranda");
		ic.addVariable("celular","062 9934 5643");
		
		ic.getObject().setClassName("Usuario");
		
	}
	
	
	@Test
	public void testSalvar(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "Usuario", as);
		this.setControllerVariables(ic);
		
		//Login with correct credentials
		ic.execute(response, "salvar");
		assertTrue(response.isSuccess());
		
		
	}

	
	@Test
	public void testEdit(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "Usuario", as);
		this.setControllerVariables(ic);
		
		ic.addVariable("id", this.id);
		ic.addVariable("email","test@test.com");
		ic.addVariable("newSenha", "123456");
		ic.addVariable("repeatedSenha", "123456");
		ic.addVariable("nome","Thiago");
		ic.addVariable("sobrenome","Miranda");
		ic.addVariable("celular","062 9934 5643");
		
		
		//Login with correct credentials
		ic.execute(response, "edit");
		assertTrue(response.isSuccess());
		
		
	}
	
	@Test
	public void testRemove(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		IController ic = GenericController.getController(response, "Usuario", as);
		this.setControllerVariables(ic);
		
		ic.addVariable("id", this.id);
		ic.execute(new JReturn(), "remover");
	}
	
	
}
