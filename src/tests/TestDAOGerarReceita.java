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

public class TestDAOGerarReceita {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testFilterMaxCalories() {
		Receita r = new Receita();
		
		
		ArrayList<Model> list= DAOGerarReceita.getInstance().select(r);
		
		list = DAOGerarReceita.getInstance().filterByMaxCalories(1000.0, list);
		
		assertEquals(0 ,list.size());
		
		
	}
	
	
	

}
