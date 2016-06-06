package DAOInterfaces;

import java.util.ArrayList;

import Model.Model;
import Utils.Return;

public interface DAOInterface {
	
	Return insert(Model object);
	
	Return update(Model object);
	
	public Return save(Model object);
	
	public Return delete(Model object);
	
	public ArrayList<Model> select(Model object); 
	
}
