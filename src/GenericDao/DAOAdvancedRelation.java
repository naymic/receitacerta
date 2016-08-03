package GenericDao;

public class DAOAdvancedRelation extends DAORelation{
	
	private static DAOAdvancedRelation dr = null;
	
	public static DAOAdvancedRelation getInstance(){
		if(dr == null){
			dr = new DAOAdvancedRelation(false);
		}
		return dr;
	}
	
	public static DAOAdvancedRelation getTestInstance(){
		if(dr == null){
			dr = new DAOAdvancedRelation(true);
		}
		return dr;
	}
	
	protected DAOAdvancedRelation(boolean istestDB){
		super(istestDB);	
	};
	
}
