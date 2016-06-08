package Tests;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Controller.CRUDController;
import GenericDao.DAO;
import GenericDao.DAORelation;
import Model.*;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;
import Utils.JSON;
import Utils.Return;

public class TestJSON {

	@Test
	public void testNewObjectArrayToJSON() {
		String json = "";
		Ingredientes i = new Ingredientes();
		
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		
		
		
		JSON j = new JSON();
		json = j.objectJson(rdr, false);
		System.out.println(json);
	}
	
	@Test
	public void testEditObjectArrayToJSON() {
		String json = "";
		Ingredientes i = new Ingredientes();
		i.dsetId(1);
		
		List<Model> list = DAORelation.getTestInstance().select(i);
		if(!list.isEmpty()){
			i = (Ingredientes)list.get(0);
		}
		ReflectionDAORelation rdr = new ReflectionDAORelation(i);
		

		
		JSON j = new JSON();
		json = j.objectJson(rdr, true);
		
		System.out.println(json);
		
	}
	
	@Test
	public void listObject(){
		String json = "";
		Ingredientes i = new Ingredientes();
		List<Model> list = DAORelation.getTestInstance().select(i);
		if(list.size() > 0){
			i = (Ingredientes) list.get(0);
			Return r = new Return();
			r.addAttributeError("Ingredientes", "nome", "teste de mensagem");
			r.addMsg("Teste de mensagem");
			
			JSON j = new JSON();
			json = j.listConstruct(i.getClass().getSimpleName(), r, list);
		}
		System.out.println("\n test list objects:\n"+json);
		
	}
	
	
	@Test
	public void saveObject(){
		String json = "";
		Ingredientes i = new Ingredientes();
		CRUDController cc = new CRUDController();

		

		Return r = i.verify();
		if(r.isSuccess())
			cc.saveObject(r, i);
		
		JSON j = new JSON();

		assertEquals(6, r.getMessageMap().entrySet().size());
		
		
		System.out.println(j.messageConstruct(r));
		
	}
	
	

}
