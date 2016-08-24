package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dao.DAO;
import dao.DAORelation;
import db.Config;
import jsonclasses.JReturn;
import model.IngredienteArmazenamentos;
import model.IngredienteTipo;
import model.IngredienteUnidades;
import model.Model;
import model.TestIngredientes;
import reflection.ReflectionDAORelation;




public class TestReflectionDAORelation extends TestCases {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}

	@Test
	public void testSetValue(){
			
		Integer id = new Integer(1);
		TestIngredientes i = new TestIngredientes();
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		
		rdr.setMethodRelationValue("dsetIngredienteArmazenamentos", id);
		assertEquals(id, i.dgetIngredienteArmazenamentos().dgetId());
	}
	
	@Test
	public void testSetValueFromAtrributeName(){
			
		Integer id = new Integer(2);
		TestIngredientes i = new TestIngredientes();
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		
		
		rdr.setValueFromAttributename("ingredientes_unidades_id", id);
		assertEquals(id, i.dgetIngredientesUnidade().dgetId());
	}
	
	@Test
	public void testGetValueFromAtrributeName(){
			
		TestIngredientes i = new TestIngredientes();
		i.dsetId(new Integer(4));
		
		ArrayList<Model> list = DAORelation.getInstance().select(i);
		i = (TestIngredientes) list.get(0);
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		
		
		Model object = (Model) rdr.getValueFromAttributeName("ingredientes_unidades_id");
		assertEquals(object.getClass().getSimpleName(), "IngredienteUnidades");
	}
	
	@Test
	public void testGetValueFromRelation(){
			
		TestIngredientes i = new TestIngredientes();
		i.dsetId(new Integer(2));
		
		ArrayList<Model> list = DAORelation.getInstance().select(i);
		i = (TestIngredientes) list.get(0);
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		
		
		assertEquals(i.dgetIngredientesUnidade().dgetNomeUnidade(), rdr.getMethodRelationValue("ingredientes_unidades_id"));
		
	}


	@Test
	public void testSelect(){
		TestIngredientes i = new TestIngredientes();
		
		ArrayList<Model> lm = new ArrayList<>();		
		i.dsetId(1);
		lm = DAORelation.getInstance().select(i);
		
		i = (TestIngredientes)lm.get(0);
		
		assertEquals(new Integer(1), i.dgetId());
		assertEquals("carne de sol1", i.dgetNome());
		assertEquals(new Double(250), i.dgetCalorias());
		
		assertEquals(new Integer(1),i.dgetIngredientesUnidade().dgetId());
		assertEquals("l",i.dgetIngredientesUnidade().dgetNomeUnidade());
		
		assertEquals(new Integer(2),i.dgetIngredienteArmazenamentos().dgetId());
		assertEquals("test1",i.dgetIngredienteArmazenamentos().dgetNomeArmazenamento());
		
		assertEquals(new Integer(1),i.dgetIngredientesTipo().dgetId());
		assertEquals("fruta",i.dgetIngredientesTipo().dgetNomeTipo());
		
	}
	
	@Test
	public void testSave(){
		
		TestIngredientes i = new TestIngredientes();
		i.dsetCalorias(23.45);
		i.dsetNome("Abobra");
		
		IngredienteTipo it = new IngredienteTipo();
		it.dsetId(new Integer(2));
		i.dsetIngredientesTipo(it);
		
		IngredienteUnidades iu = new IngredienteUnidades();
		iu.dsetId(new Integer(1));
		i.dsetIngredientesUnidade(iu);
		
		IngredienteArmazenamentos ia = new IngredienteArmazenamentos();
		ia.dsetId(new Integer(1));
		i.dsetIngredienteArmazenamentos(ia);
		
		JReturn r = new JReturn();
		assertTrue(DAORelation.getInstance().save(i, r).isSuccess());
		
		r = new JReturn();
		i.dsetId(3);
		DAORelation.getInstance().delete(i, r);
		
		r = new JReturn();
		i.dsetId(2);
		i.dsetCalorias(i.dgetCalorias()+1);
		assertTrue(DAORelation.getInstance().save(i, r).isSuccess());
	}
	
	
	@Test
	public void testDelete(){
		TestIngredientes i = new TestIngredientes();
		i.dsetCalorias(23.45);
		i.dsetNome("Abobra");
		
		IngredienteTipo it = new IngredienteTipo();
		it.dsetId(new Integer(2));
		i.dsetIngredientesTipo(it);
		
		IngredienteUnidades iu = new IngredienteUnidades();
		iu.dsetId(new Integer(1));
		i.dsetIngredientesUnidade(iu);
		
		IngredienteArmazenamentos ia = new IngredienteArmazenamentos();
		ia.dsetId(new Integer(1));
		i.dsetIngredienteArmazenamentos(ia);
		
		JReturn r = new JReturn();
		DAORelation.getInstance().save(i, r);
		
		r = new JReturn();
		assertTrue(DAO.getInstance().existModel(i, r));
		
		r = new JReturn();
		i.dsetId(3);
		assertTrue(DAORelation.getInstance().delete(i, r).isSuccess());
		
		r = new JReturn();
		assertFalse(DAO.getInstance().existModel(i, r));
	}

}
