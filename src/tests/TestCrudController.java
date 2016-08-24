package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.CrudController;
import dao.DAO;
import db.Config;
import exceptions.NoActionException;
import jsonclasses.JReturn;
import model.Ingredientes;
import model.Model;
import model.TestIngredientes;
import utils.Transform;

public class TestCrudController extends TestCases{

	/*
	 * Auxiliary methods
	 */
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
	
	
	/*
	 * Testcase methods
	 */
	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testSaveObject() {
		CrudController cc = this.getCRUDController();
		JReturn r = new JReturn();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);
		obj.dsetCalorias(300.0);
		
		
		 cc.execute(r, "salvar");
		
		assertTrue(r.isSuccess());

		assertTrue(r.getMessages().get(0).contains("Data TestIngredientes successfully saved in database"));
		
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

		cc.execute(r, "remover");
		System.out.println(cc.getJson());
		assertTrue(r.isSuccess());
		assertTrue(r.getMessages().get(0).contains("Data TestIngredientes successfully removed"));
	}
	
	@Test
	public void testNewOject() {
		CrudController cc = this.getCRUDController();
		JReturn r = new JReturn();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);
		
		//Delete object and check if don't exist anymore in the Database
		DAO.getInstance().delete(obj, new JReturn());
		assertFalse(DAO.getInstance().existModel(obj, new JReturn()));
		
		//Save with the execute "salvar" action
		cc.execute(r, "salvar");
		
		//Check if object exist in the Database
		assertTrue(DAO.getInstance().existModel(obj, new JReturn()));
		
		assertTrue(r.getMessages().get(0).contains("Data TestIngredientes successfully saved in database"));
		System.out.println(cc.getJson());
		
	}
	
	
	@Test
	public void testEditOject() {
		CrudController cc = this.getCRUDControllerEdit();
		JReturn r = new JReturn();
		Model obj = cc.initObj(r);
		
		cc.execute(r, "edit");
		assertTrue(r.isSuccess());
		assertEquals(1, r.getData().getData().size());
		TestIngredientes ing = (TestIngredientes) r.getData().getData().get(0);
		assertEquals(new Integer(2), ing.dgetId());
		
		System.out.println(cc.getJson());
		
		
		TestIngredientes i = (TestIngredientes)obj;
		i.dsetId(null);
		r = cc.editObject(r, i);
		assertTrue(!r.isSuccess());
		assertTrue(Transform.objectToJson(r).contains("Primary key is not set."));
	}
	
}
