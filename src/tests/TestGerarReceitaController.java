package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.GenericController;
import controllers.GerarReceitasController;
import db.Config;
import jsonclasses.JReturn;

public class TestGerarReceitaController {

	
	public GerarReceitasController getGerarReceitasController(){
		GerarReceitasController cc = new GerarReceitasController();
		cc.addVariable("maxCalories", new Double(2500000.0));
		cc.addVariable("stringIngredientesId", new String("1,2,3"));
		cc.getObject().setClassName("ReceitaView");
		return cc;
	}
	
	
	
	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	
	@Test
	public void testSelectGerarReceita() {
		JReturn r = new JReturn();
		
		GerarReceitasController grc = getGerarReceitasController();
		grc.execute(r, "busca");
		
		assertTrue(r.isSuccess());
		
	}

}
