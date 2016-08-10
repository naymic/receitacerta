package Tests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import Controller.CrudController;
import GenericDao.DAORelation;
import JsonClasses.JData;
import JsonClasses.JRedirect;
import JsonClasses.JReturn;
import Model.Model;
import Model.TestIngredientes;
import Reflection.ReflectionDAORelation;
import Utils.JSON;

public class TestJSON {

	@Test
	public void testNewObjectArrayToJSON() {
		String json = "";
		TestIngredientes i = new TestIngredientes();
		
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		
		JSON j = new JSON();
		json = j.objectJson(rdr, false);
		System.out.println(json.replaceAll("[\n|\t]", ""));
	}
	
	@Test
	public void testEditObjectArrayToJSON() {
		String json = "";
		TestIngredientes i = new TestIngredientes();
		i.dsetId(4);
		
		List<Model> list = DAORelation.getTestInstance().select(i);
		if(!list.isEmpty()){
			i = (TestIngredientes)list.get(0);
		}
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		

		
		JSON j = new JSON();
		json = j.objectJson(rdr, true);
	
		
		System.out.println(json.replaceAll("[\n|\t]", ""));
		
	}
	
	@Test
	public void listObject(){
		String json = "";
		TestIngredientes i = new TestIngredientes();
		List<Model> list = DAORelation.getTestInstance().select(i);
		if(list.size() > 0){
			i = (TestIngredientes) list.get(0);
			JReturn r = new JReturn();
			r.addAttributeError("TestIngredientes", "nome", "teste de mensagem");
			r.addMsg("Teste de mensagem");
			
			JSON j = new JSON();
			JData d = new JData(i.getClass().getSimpleName());
			d.setData(list);
			r.addData(d);
			
			
			
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
	
	

}
