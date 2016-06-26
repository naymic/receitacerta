package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Controller.CrudController;
import Model.Model;
import Model.TestIngredientes;
import Utils.Return;

public class TestCrudController {

	
	public CrudController getCRUDController(){
		CrudController cc = new CrudController();
		cc.addVariable("campo.id", 1);
		cc.addVariable("campo.ingrediente_armazenamentos_id", 2);
		cc.addVariable("campo.calorias", 250.0);
		cc.addVariable("campo.nome", "carne de sol1");
		cc.addVariable("campo.ingredientes_unidades_id", 1);
		cc.addVariable("campo.ingredientes_tipo_id", 1);
		cc.addVariable("className", "TestIngredientes");
		return cc;
	}
	
	public CrudController getCRUDControllerEdit(){
		CrudController cc = new CrudController();
		cc.addVariable("className", "TestIngredientes");
		cc.addVariable("campo.id", 2);
		return cc;
	}
	
	@Test
	public void testInitObject() {
		CrudController cc = this.getCRUDController();
		Return r = new Return();
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
		Return r = new Return();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);
		obj.dsetCalorias(300.0);
		
		String json = cc.saveObject(r, obj);
		
		assertTrue(r.isSuccess());
		//assertTrue(json.contains("Data Ingredientes successfully saved in database"));
		
		obj.dsetNome(null);
		json = cc.saveObject(r, obj);
		obj.verify(r);
		assertFalse(r.isSuccess());


	}
	
	@Test
	public void testRemoveObject() {
		CrudController cc = this.getCRUDController();
		Return r = new Return();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);

		String json = cc.removeObject(r, obj);
		System.out.println(json);
		assertTrue(r.isSuccess());
		//assertTrue(json.contains("Data Ingredientes successfully removed"));
	}
	
	@Test
	public void testNewOject() {
		CrudController cc = this.getCRUDController();
		Return r = new Return();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);
		
		String json = cc.newObject(obj);
		System.out.println(json);
		//assertEquals(1122, json.length());
	}
	
	
	@Test
	public void testEditOject() {
		CrudController cc = this.getCRUDControllerEdit();
		Return r = new Return();
		Model obj = cc.initObj(r);
		
		String json = cc.editObject(r, obj);
		assertTrue(r.isSuccess());
		//assertEquals(549, json.length());
		
		System.out.println(json);
		
		TestIngredientes i = (TestIngredientes)obj;
		i.dsetId(null);
		
		Return r1 = new Return();
		String json1 = cc.editObject(r1, i);
		assertTrue(!r1.isSuccess());
		assertTrue(json1.contains("Primary key is not set."));
	}
	
	
	@Test
	public void testActions(){
		
	}

}
