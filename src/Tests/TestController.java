package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Controller.CRUDController;
import Model.Ingredientes;
import Model.Model;
import Utils.Return;

public class TestController {

	
	public CRUDController getCRUDController(){
		CRUDController cc = new CRUDController();
		cc.addVariable("Ingredientes.id", 1);
		cc.addVariable("Ingredientes.ingrediente_armazenamentos_id", 2);
		cc.addVariable("Ingredientes.calorias", 250.0);
		cc.addVariable("Ingredientes.nome", "carne de sol1");
		cc.addVariable("Ingredientes.ingredientes_unidades_id", 1);
		cc.addVariable("Ingredientes.ingredientes_tipo_id", 1);
		cc.addVariable("className", "Ingredientes");
		return cc;
	}
	
	public CRUDController getCRUDControllerEdit(){
		CRUDController cc = new CRUDController();
		cc.addVariable("className", "Ingredientes");
		cc.addVariable("Ingredientes.id", 1);
		return cc;
	}
	
	@Test
	public void testInitObject() {
		CRUDController cc = this.getCRUDController();
		Ingredientes obj = (Ingredientes)cc.initObj();
		
		
		assertEquals(new Integer(1), obj.dgetId());
		assertEquals(new Integer(2), obj.dgetIngredienteArmazenamentos().dgetId());
		assertEquals(new Double(250.0), obj.dgetCalorias());
		assertEquals("carne de sol1", obj.dgetNome());
		assertEquals(new Integer(1), obj.dgetIngredientesUnidade().dgetId());
		assertEquals(new Integer(1), obj.dgetIngredientesTipo().dgetId());
		
	}
	
	@Test
	public void testSaveObject() {
		CRUDController cc = this.getCRUDController();
		Ingredientes obj = (Ingredientes)cc.initObj();
		obj.dsetCalorias(300.0);
		
		Return r = new Return();
		String json = cc.saveObject(r, obj);
		
		assertTrue(r.isSuccess());
		assertTrue(json.contains("Data Ingredientes successfully saved in database"));
		
		/*obj.dsetNome(null);
		json = cc.saveObject(r, obj);
		assertTrue(json.contains("Column 'nome' cannot be null"));
		assertTrue(json.contains("Data Ingredientes could not be saved in database"));*/

	}
	
	@Test
	public void testRemoveObject() {
		CRUDController cc = this.getCRUDController();
		Ingredientes obj = (Ingredientes)cc.initObj();

		
		Return r = new Return();
		String json = cc.removeObject(r, obj);
		System.out.println(json);
		assertTrue(r.isSuccess());
		assertTrue(json.contains("Data Ingredientes successfully removed"));
	}
	
	@Test
	public void testNewOject() {
		CRUDController cc = this.getCRUDController();
		Ingredientes obj = (Ingredientes)cc.initObj();
		
		String json = cc.newObject(obj);
		System.out.println(json);
		//assertEquals(1122, json.length());
	}
	
	
	@Test
	public void testEditOject() {
		CRUDController cc = this.getCRUDControllerEdit();
		Model obj = cc.initObj();
	
		Return r = new Return();
		String json = cc.editObject(r, obj);
		assertTrue(r.isSuccess());
		//assertEquals(549, json.length());
		
		System.out.println(json);
		
		Ingredientes i = (Ingredientes)obj;
		i.dsetId(null);
		
		Return r1 = new Return();
		String json1 = cc.editObject(r1, i);
		assertTrue(!r1.isSuccess());
		assertTrue(json1.contains("Primary key is not set. Object Ingredientes not found!"));
	}
	
	
	@Test
	public void testActions(){
		
	}

}
