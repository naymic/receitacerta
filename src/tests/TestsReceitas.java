package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.GenericController;
import controllers.LoginController;
import db.Config;
import interfaces.IController;
import jresponseclasses.JReturn;
import jresponseclasses.JUser;
import model.Receita;
import model.User;
import model.Usuario;
import views.ViewController;

public class TestsReceitas  extends TestCases {
	
	private Receita savedRecipe;

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(false);
	}

	
	private void setControllerInsertVariables(IController ic){
		ic.addVariable("nome","PamonhaTeste");
		ic.addVariable("tempoPreparo", "26");
		ic.addVariable("receitaRendimentosTipoValor", "34");
		ic.addVariable("receitaRendimentoTipos", "0");
		ic.addVariable("receitaTipos", "1");
		ic.getObject().setClassName("Receita");
	}
	
	private void setControllerListVariables(IController ic){
		ic.addVariable("classname","ReceitaView");
		ic.addVariable("action", "buscaavancada");
		ic.addVariable("usecase", "Crud");
	
	}

	
	private void login(ApplicationSessionForTests  as, JReturn j){
		
		IController ic = GenericController.getController(j, "login", as);
		ic.addVariable("email","micha.meier.siueg@gmail.com");
		ic.addVariable("senha", "123456");
		ic.getObject().setClassName("Usuario");
		
		//Login with correct credentials

		ic.execute(j, "login");
	}

	
	@Test
	/**
	 * Test if the recipe is cadastred
	 */
	public void testeCadastrarReceita(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		login(as, response);
		IController ic = GenericController.getController(response, "Crud", as);
		this.setControllerInsertVariables(ic);
		
		//Login with correct credentials
		ic.execute(response, "salvar");
		assertTrue(response.isSuccess());
		
		savedRecipe = (Receita) response.getData().getData().get(0);
		assertTrue(savedRecipe != null);
		
	}
	
	@Test
	public void testeDeleteRecipe(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		login(as, response);
		IController ic = GenericController.getController(response, "Crud", as);
		this.setControllerListVariables(ic);
		
		//Login with correct credentials
		ic.execute(response, "buscaavancada");
		assertTrue(response.isSuccess());
		
		List<Receita> recipes =  (List)response.getData().getData();
		
		for(Receita recipe:recipes){
			System.out.println(recipe.dgetNome());
		}
	}
	
	@Test
	public void testeListRecipes(){
		ApplicationSessionForTests as = new ApplicationSessionForTests();
		JReturn response = new JReturn();
		login(as, response);
		IController ic = GenericController.getController(response, "Crud", as);
		this.setControllerListVariables(ic);
		
		//Login with correct credentials
		ic.execute(response, "buscaavancada");
		assertTrue(response.isSuccess());
		
		List<Receita> recipes =  (List)response.getData().getData();
		
		for(Receita recipe:recipes){
			System.out.println(recipe.dgetNome());
		}
	}
	
}
