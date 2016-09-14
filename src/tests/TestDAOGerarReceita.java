package tests;


import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dao.DAOGerarReceita;
import dao.DAORelation;
import db.Config;
import model.Model;
import model.Receita;
import model.ReceitaView;
import reflection.ReflectionDAO;

public class TestDAOGerarReceita {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testFilterMaxCalories() {
		ReceitaView r = new ReceitaView();
		
		r.setStringIngredientesId("1,2,3");
		r.setMaxCalories(1000.0);
		
		ReflectionDAO rd = new ReflectionDAO(r);
		
		ArrayList<Model> list= DAOGerarReceita.getInstance().select(r, new ArrayList<Model>());
		
		
		
		assertEquals(8 ,list.size());
		
		
	}
	
	
	

}
