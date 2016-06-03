package GenericDao;

import java.util.Stack;

public class DAORelation extends DAO{

	private static DAORelation dr = null;
	
	public static DAORelation getInstance(){
		if(dr == null){
			dr = new DAORelation();
		}
		return dr;
	}
	
	private DAORelation(){};
	
}
