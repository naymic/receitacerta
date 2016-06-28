package Interfaces;

import java.util.ArrayList;

import Model.Model;
import Utils.Return;

public interface IDAO {
	
	public Return insert(Model object, Return r);
	
	public Return update(Model object, Return r);
	
	public Return save(Model object, Return r);
	
	public Return delete(Model object, Return r);
	
	public ArrayList<Model> select(Model object); 
	
}
