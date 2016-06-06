package Interfaces;

import java.util.ArrayList;

import Model.Model;
import Utils.Return;

public interface IDAO {
	
	public Return insert(Model object);
	
	public Return update(Model object);
	
	public Return save(Model object);
	
	public Return delete(Model object);
	
	public ArrayList<Model> select(Model object); 
	
}
