package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import controllers.CrudController;
import controllers.GenericController;
import utils.Config;
import exceptions.NoActionException;
import jresponseclasses.JReturn;
import model.TestIngredientes;
import reflection.ReflectionController;

public class TestGenericController {
	
	public GenericController getGenericControllerFalseClass(){
		CrudController cc = new CrudController();
		cc.getObject().setClassName("TestIngredi");
		return cc;
	}
	
	public GenericController getGenericController(){
		GenericController cc = new GenericController();
		cc.addVariable("id", new Integer(1));
		cc.addVariable("ingredienteArmazenamentosId", new Integer(2));
		cc.addVariable("calorias", new Double(250.0));
		cc.addVariable("nome", "carne de sol1");
		cc.addVariable("ingredientesUnidade", new Integer(1));
		cc.addVariable("ingredientesTipoId", new Integer(1));
		cc.getObject().setClassName("TestIngredientes");
		return cc;
	}
	
	
	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testAction(){
		
		//Usecase update don't exist
		GenericController cc = new GenericController();
		ReflectionController rController = new ReflectionController(cc);
		JReturn r1 = new JReturn();
		
		
		try {
			cc.validateAction(rController,"edit");
			
		} catch (NoActionException e) {
			r1.addSimpleError(e.getMessage());
		}
				
		assertFalse(r1.isSuccess());
		
		

		//Usecase update don't exist
		CrudController crud = new CrudController();
		ReflectionController crudController = new ReflectionController(crud);
		JReturn r = new JReturn();
		
		
		try {
			cc.validateAction(crudController,"salvar");
			
		} catch (NoActionException e) {
			r.addSimpleError(e.getMessage());
		}
				
		assertFalse(!r.isSuccess());
	}
	
	
	@Test
	public void testInitObject() {
		GenericController cc = getGenericControllerFalseClass();
		JReturn r = new JReturn();
		
		//Test false Model name
		Object obj = (TestIngredientes)cc.initObj(r);
		assertFalse(r.isSuccess());
		assertEquals(1, r.getSimpleErrors().size());
		assertEquals("Given classname don't exist! Classname: model.TestIngredi", r.getSimpleErrors().get(0));
		
		
		//Check with correct Model Class name
		GenericController gc = getGenericController();
		TestIngredientes obj1 = (TestIngredientes)gc.initObj(r);
		assertEquals(new Integer(1), obj1.dgetId());
		assertEquals(new Integer(2), obj1.dgetIngredienteArmazenamentosId().dgetId());
		assertEquals(new Double(250.0), obj1.dgetCalorias());
		assertEquals("carne de sol1", obj1.dgetNome());
		assertEquals(new Integer(1), obj1.dgetIngredientesUnidade().dgetId());
		assertEquals(new Integer(1), obj1.dgetIngredientesTipoId().dgetId());
		
		
	}
	

}
