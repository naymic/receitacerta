package Controller;

import java.util.ArrayList;
import java.util.List;

import GenericDao.DAO;
import Interfaces.IApplicationSession;
import Model.Model;
import Model.Usuario;
import Utils.JSON;
import Utils.Return;

public class LoginController extends GenericController{
	
	IApplicationSession appSession;
	

	public LoginController(){
		super();
		
	}
	
	@Override
	/**
	 * Gets the list of valid actions for this controller
	 */
	public List<String> getValidActionsList() {
		validActions = new ArrayList<String>();
				
		//Tries to login a user by its email an password
		validActions.add("login");
	
		//Logout the user from the session
		validActions.add("logout");
		
		return validActions;
	}
	
	
	@Override
	public void execute(Return r, String action) {
		r = this.validateAction(action);
		JSON j = new JSON();
		
		//Iniciate user from view
		Usuario loginUser = (Usuario)super.initObj(r);
				
		if(r.isSuccess()){
			
			if(action.equalsIgnoreCase("login")){
				this.login(loginUser, r);
			}else if(action.equalsIgnoreCase("logout")){
				this.logout(r);
			}
		}
		
		this.setUniqueJson(j.messageConstruct(r));
		
	}
	
	
	public void login(Usuario loginUser, Return r){
		boolean test = false;
		Usuario u = null;
		//Get a list of all users
		ArrayList<Model> usuarios = DAO.getInstance().select(new Usuario());
		
		//Check if exist user
		for(Model bdUser : usuarios){
			u = (Usuario) bdUser;
			
			if(u.dgetEmail().equalsIgnoreCase(loginUser.dgetEmail()) && u.dgetSenha().equalsIgnoreCase(loginUser.dgetSenha())){
				test = true;
				break;
			}
		}
		
		if(test){		
			this.setUserLoggedin(true);
			this.setUser(u);
			r.addMsg("User sucessfully indentified!");
			r.setRedirect(this.getRedirect()); //Sets the redirection previos saved
		}else{
			this.setUserLoggedin(false);
			this.setUser(null);
			r.addSimpleError("Email and password combination could not be found. Login failed!");
		}
	}
	
	public void logout(Return r){
		this.setUserLoggedin(false);
		this.setUser(null);
		this.setRedirect("index");
	}
	
	
	
	
	
	
	/* App Session  */
	public IApplicationSession getAppSession() {return appSession;}
	public void setAppSession(IApplicationSession appSession) {this.appSession = appSession;}
	

	/* Specific User Methods */
	

	public boolean isUserLoggedin(){
		
		if(this.getAppSession().getMapAttribute("loggedin") == null)
			return false;
		
		return (boolean)this.getAppSession().getMapAttribute("loggedin");
	}
	
	public  void setUserLoggedin(boolean loggedin){
		this.getAppSession().setMapAttribute("loggendin", loggedin);
	}
	
	public void setUser( Usuario u){
		this.getAppSession().setMapAttribute("user", u);
	}
	
	public Usuario getUser(){
		return (Usuario) this.getAppSession().getMapAttribute("user");
	}
	
}
