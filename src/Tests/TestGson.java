package Tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import GenericDao.DAORelation;
import Interfaces.ISimpleConverter;
import Model.Ingredientes;
import Model.Model;
import Model.Usuario;
import Reflection.ReflectionDAORelation;
import Utils.CryptString;

public class TestGson extends TestCases {

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
		System.out.println("TestRelationSelectToGson: "+g.toJson(l));
		
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
