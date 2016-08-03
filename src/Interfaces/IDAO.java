package Interfaces;

import java.util.ArrayList;

import JsonClasses.JReturn;
import Model.Model;


public interface IDAO {
	
	public JReturn insert(Model object, JReturn r);
	
	public JReturn update(Model object, JReturn r);
	
	public JReturn save(Model object, JReturn r);
	
	public JReturn delete(Model object, JReturn r);
	
	public ArrayList<Model> select(Model object); 
	
}
