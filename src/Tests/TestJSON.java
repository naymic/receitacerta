package Tests;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Controller.CRUDController;
import GenericDao.DAO;
import GenericDao.DAORelation;
import Model.Model;
import Model.TestIngredientes;
import Reflection.ReflectionDAORelation;
import Utils.JSON;
import Utils.Return;

public class TestJSON {

	@Test
	public void testNewObjectArrayToJSON() {
		String json = "";
		TestIngredientes i = new TestIngredientes();
		
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		
		JSON j = new JSON();
		json = j.objectJson(rdr, false);
		//System.out.println(json.replaceAll("[\n|\t]", ""));
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
			Return r = new Return();
			r.addAttributeError("TestIngredientes", "nome", "teste de mensagem");
			r.addMsg("Teste de mensagem");
			
			JSON j = new JSON();
			json = j.listConstruct(i.getClass().getSimpleName(), r, list);
		}
		System.out.println("\n test list objects:\n"+json);
		
	}
	
	
	@Test
	public void saveObject(){
		String json = "";
		TestIngredientes i = new TestIngredientes();
		i.dsetCalorias(new Double(0.0));
		CRUDController cc = new CRUDController();

		

		Return r = new Return();
		i.verify(r);
		if(r.isSuccess())
			cc.saveObject(r, i);
		
		JSON j = new JSON();

		assertEquals(4, r.getMessageMap().entrySet().size());
		
		
		System.out.println(j.messageConstruct(r));
		
	}
	
	

}
