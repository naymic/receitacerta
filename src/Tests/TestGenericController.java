package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Controller.CrudController;
import Controller.GenericController;
import Exceptions.NoActionException;
import JsonClasses.JReturn;
import Model.TestIngredientes;

public class TestGenericController {
	
	public CrudController getCRUDControllerFalseClass(){
		CrudController cc = new CrudController();
		cc.getObject().setClassName("TestIngredi");
		return cc;
	}
	
	
	@Test
	public void testAction(){
		
		//Usecase update don't exist
		GenericController cc = new GenericController();
		JReturn r = new JReturn();
		JReturn r1 = new JReturn();
		try {
			cc.validateAction("index");
			
			
			
			cc.validateAction("edit");
			
		} catch (NoActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(r.isSuccess());
		
		
		
		
		assertFalse(r1.isSuccess());
	}
	
	
	@Test
	public void testInitObject() {
		GenericController cc = getCRUDControllerFalseClass();
		JReturn r = new JReturn();
		Object obj = (TestIngredientes)cc.initObj(r);
		assertFalse(r.isSuccess());
		assertEquals(1, r.getSimpleErrors().size());
		assertEquals("Given classname don't exist! Classname: Model.TestIngredi", r.getSimpleErrors().get(0));
	}
}
