package tests;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dao.DAOGerarReceita;
import utils.Config;
import model.Model;
import model.ReceitaView;

public class TestDAOGerarReceita {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testFilterMaxCalories() {
		ReceitaView r = new ReceitaView();
		
		r.setStringIngredientesId("20");
		r.dsetMaxCalories(10000.0);
		
		
		ArrayList<Model> list= DAOGerarReceita.getInstance().search(r, new ArrayList<Model>(), null, null);
		
		
		
		assertEquals(5 ,list.size());
		
		
	}
	
	
	

}
