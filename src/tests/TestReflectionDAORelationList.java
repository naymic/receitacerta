package tests;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import dao.DAORelationList;
import db.Config;
import model.Model;
import model.Receita;

public class TestReflectionDAORelationList extends TestCases {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testDAORelationList(){
		
		Receita rc = new Receita();
		
		ArrayList<Model> mList = DAORelationList.getInstance().select(rc);
		Gson g = new Gson();
		String l = g.toJson(mList);
		System.out.println(l);
		
	}
	

}
