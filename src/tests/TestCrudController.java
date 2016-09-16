package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.CrudController;
import dao.DAO;
import dao.DAORelation;
import db.Config;
import exceptions.NoActionException;
import jresponseclasses.JReturn;
import model.IngredienteArmazenamentos;
import model.IngredienteTipo;
import model.IngredienteUnidades;
import model.Ingredientes;
import model.Model;
import model.Receita;
import model.TestIngredientes;
import utils.Transform;

public class TestCrudController extends TestCases{
	
	/*
	 * Auxiliary methods
	 */
	public CrudController getCRUDController(){
		CrudController cc = new CrudController();
		cc.addVariable("id", 1);
		cc.addVariable("ingredienteArmazenamentosId", 2);
		cc.addVariable("calorias", 250.0);
		cc.addVariable("nome", "carne de sol1");
		cc.addVariable("ingredientesUnidade", 1);
		cc.addVariable("ingredientesTipoId", 1);
		cc.getObject().setClassName("TestIngredientes");
		return cc;
	}
	
	public CrudController getCRUDControllerEdit(){
		CrudController cc = new CrudController();
		cc.addVariable("id", 4);
		cc.getObject().setClassName("TestIngredientes");
		
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
		//TestIngredientes obj = (TestIngredientes)cc.initObj(r);
				
		 cc.execute(r, "salvar");
		
		assertTrue(r.isSuccess());

		assertTrue(r.getMessages().get(0).contains("Data TestIngredientes successfully saved in database"));
		
		
		cc.getObject().addAttribute("nome", null);
		cc.execute(r, "salvar");
		
		
		assertFalse(r.isSuccess());


	}
	
	@Test
	public void testRemoveObject() {
		CrudController cc = this.getCRUDController();
		JReturn r = new JReturn();
		TestIngredientes obj = (TestIngredientes)cc.initObj(r);

		DAORelation.getInstance().save(obj, new JReturn());
		
		cc.execute(r, "remover");
		System.out.println(cc.getJson());
		assertTrue(r.isSuccess());
		assertTrue(r.getMessages().get(0).contains("Data TestIngredientes successfully removed"));
		DAORelation.getInstance().save(obj, r);
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
		assertEquals(new Integer(4), ing.dgetId());
		
		System.out.println(cc.getJson());
		
		
		TestIngredientes i = (TestIngredientes)obj;
		i.dsetId(null);
		cc.addVariable("id", null);
		cc.execute(r, "edit");
		assertTrue(!r.isSuccess());
		assertTrue(Transform.objectToJson(r).contains("Primary key is not set."));
	}
	
	@Test
	public void testSelectObject(){
		CrudController cc = this.getCRUDControllerEdit();
		JReturn r = new JReturn();
		TestIngredientes obj = new TestIngredientes();
		obj.dsetId(2);
		cc.setModelObject(obj);
		
		
		cc.execute(r, "busca");
		
		assertTrue(r.isSuccess());
		assertEquals(1, r.getData().getData().size());
	}
	
}
