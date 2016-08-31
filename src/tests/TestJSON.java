package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import controllers.CrudController;
import dao.DAORelation;
import db.Config;
import jsonclasses.*;
import model.Model;
import model.TestIngredientes;
import reflection.ReflectionDAORelation;

public class TestJSON extends TestCases{


	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testNewObjectArrayToJSON() {
		String json = "";
		TestIngredientes i = new TestIngredientes();
		
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
	
		
		Gson g = new Gson();
		System.out.println(g.toJson(rdr.getObject()));
	}
	
	@Test
	public void testEditObjectArrayToJSON() {
		String json = "";
		TestIngredientes i = new TestIngredientes();
		i.dsetId(4);
		
		List<Model> list = DAORelation.getInstance().select(i);
		if(!list.isEmpty()){
			i = (TestIngredientes)list.get(0);
		}
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		

		
		/*JSON j = new JSON();
		json = j.objectJson(rdr, true);*/
	
		
		System.out.println(json.replaceAll("[\n|\t]", ""));
		
	}
	
	@Test
	public void listObject(){
		String json = "";
		TestIngredientes i = new TestIngredientes();
		List<Model> list = DAORelation.getInstance().select(i);
		if(list.size() > 0){
			i = (TestIngredientes) list.get(0);
			JReturn r = new JReturn();
			r.addAttributeError("TestIngredientes", "nome", "teste de mensagem");
			r.addMsg("Teste de mensagem");
			
			r.getData().setDataList(list);
			
			
			
		}
		System.out.println("\n test list objects:\n"+json);
		
	}
	
	
	@Test
	public void saveObject(){
		String json = "";
		TestIngredientes i = new TestIngredientes();
		i.dsetCalorias(new Double(0.0));
		CrudController cc = new CrudController();

		

		JReturn r = new JReturn();
		i.verify(r);
		if(r.isSuccess())
			cc.saveObject(r, i);
		
		
		JRedirect jr = new JRedirect();
		jr.setRedirection("Login", "login", "login");
		
		r.addMsg("Mensagem de teste1");
		r.addMsg("Mensagem de teste2");
		
		r.addSimpleError("Mensagem de teste de erro1");
		r.addSimpleError("Mensagem de teste de erro2");
		
		assertEquals(4, r.getAtberrors().size());
		assertEquals(2, r.getSimpleErrors().size());
		assertEquals(2, r.getMessages().size());
		
		Gson g = new Gson();
		System.out.println(g.toJson(r));
		
		

	}
	
	@Test
	public void requestToJson(){
		String json ="[{\"classname\":\"Pessoa\",\"usecase\":\"crud\",\"action\":\"delete\",\"data\":{\"nome_pessoa\":\"Thiago\",\"idade_pessoa\":\"26\",\"rg_pessoa\":\"559988\"}},{\"classname\":\"Pessoa\",\"usecase\":\"crud\",\"action\":\"insert\",\"data\":{\"nome_pessoa\":\"Thiago1\",\"idade_pessoa\":\"261\",\"rg_pessoa\":\"5599881\"}}]";
		Gson g = new Gson();
		JRequest[] requs;
		
		requs = g.fromJson(json,  JRequest[].class);
		
		assertEquals("Pessoa", requs[0].getClassname());
		assertEquals("insert", requs[1].getAction());
		assertEquals("5599881", requs[1].getData().get("rg_pessoa"));
		
	}
	
	

}
