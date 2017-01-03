package tests;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import dao.DAORelation;
import utils.Config;
import model.Ingredientes;
import model.Model;
import model.Usuario;

public class TestGson extends TestCases {

	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void TestSimpleSelectToGson(){
		
		
		Usuario u = new Usuario();
		ArrayList<Model> l = DAORelation.getInstance().select(u);
		
		u= (Usuario) l.get(0);
		
		Gson g = new Gson();
		System.out.println(g.toJson(l));
		
	}
	
	
	@Test
	public void TestRelationSelectToGson(){
		
		
		Ingredientes u = new Ingredientes();
		ArrayList<Model> l = DAORelation.getInstance().select(u);
		
		u= (Ingredientes) l.get(0);
		
		Gson g = new Gson();
		System.out.println("TestRelationSelectToGson:  "+g.toJson(l));
		
	}
	
	
	@Test
	public void TestRelationFormToGson(){
		
		
		Ingredientes u = new Ingredientes();
		ArrayList<Model> l = DAORelation.getInstance().select(u);
		
		u = (Ingredientes) l.get(0);
		
		
		Gson g = new Gson();
		System.out.println("TestRelationFormToGson: "+g.toJson(l));
		
	}
	
}
