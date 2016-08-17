package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Controller.CrudController;
import JsonClasses.JReturn;
import Model.Ingredientes;
import Model.Model;
import Model.TestIngredientes;
import Utils.Transform;

public class TestCrudController extends TestCases{

	
	public CrudController getCRUDController(){
		CrudController cc = new CrudController();
		cc.addVariable("id", 1);
		cc.addVariable("ingrediente_armazenamentos_id", 2);
		cc.addVariable("calorias", 250.0);
		cc.addVariable("nome", "carne de sol1");
		cc.addVariable("ingredientes_unidades_id", 1);
		cc.addVariable("ingredientes_tipo_id", 1);
		cc.getObject().setClassName("TestIngredientes");
		return cc;
	}
	
	public CrudController getCRUDControllerEdit(){
		CrudController cc = new CrudController();
		cc.getObject().setClassName("TestIngredientes");
		cc.addVariable("id", 2);
		return cc;
	}
	
	@Test
	public void testInitObject() {
		CrudController cc = this.getCRUDController();
		JReturn r = new JReturn();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);
		
		
		assertEquals(new Integer(1), obj.dgetId());
		assertEquals(new Integer(2), obj.dgetIngredienteArmazenamentos().dgetId());
		assertEquals(new Double(250.0), obj.dgetCalorias());
		assertEquals("carne de sol1", obj.dgetNome());
		assertEquals(new Integer(1), obj.dgetIngredientesUnidade().dgetId());
		assertEquals(new Integer(1), obj.dgetIngredientesTipo().dgetId());
		
	}
	
	@Test
	public void testSaveObject() {
		CrudController cc = this.getCRUDController();
		JReturn r = new JReturn();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);
		obj.dsetCalorias(300.0);
		
		r = cc.saveObject(r, obj);
		
		assertTrue(r.isSuccess());
		//assertTrue(json.contains("Data Ingredientes successfully saved in database"));
		
		obj.dsetNome(null);
		r =  cc.saveObject(r, obj);
		obj.verify(r);
		assertFalse(r.isSuccess());


	}
	
	@Test
	public void testRemoveObject() {
		CrudController cc = this.getCRUDController();
		JReturn r = new JReturn();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);

		r = cc.removeObject(r, obj);
		System.out.println(Transform.objectToJson(r));
		assertTrue(r.isSuccess());
		//assertTrue(json.contains("Data Ingredientes successfully removed"));
	}
	
	@Test
	public void testNewOject() {
		CrudController cc = this.getCRUDController();
		JReturn r = new JReturn();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);
		
		r = cc.newObject(r, obj);
		System.out.println(Transform.objectToJson(r));
		//assertEquals(1122, json.length());
	}
	
	
	@Test
	public void testEditOject() {
		CrudController cc = this.getCRUDControllerEdit();
		JReturn r = new JReturn();
		Model obj = cc.initObj(r);
		
		r = cc.editObject(r, obj);
		assertTrue(r.isSuccess());
		assertEquals(1, r.getData().getData().size());
		TestIngredientes ing = (TestIngredientes) r.getData().getData().get(0);
		assertEquals(new Integer(2), ing.dgetId());
		
		System.out.println(Transform.objectToJson(r));
		
		
		TestIngredientes i = (TestIngredientes)obj;
		i.dsetId(null);
		r = cc.editObject(r, i);
		assertTrue(!r.isSuccess());
		assertTrue(Transform.objectToJson(r).contains("Primary key is not set."));
	}
	
	
	@Test
	public void testActions(){
		
	}

}
