package DAOInterfaces;

import GenericDao.Return;
import Model.Model;

public interface DAOInterface {
	
	Return insert(Model object);
	
	Return update(Model object);
	
	public Return save(Model object);
	
	public Return delete(Model object);
	
	public Model select(Model object); 
	
}
