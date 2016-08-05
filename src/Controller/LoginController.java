package Controller;

import java.util.ArrayList;
import java.util.List;

import GenericDao.DAO;
import Interfaces.IApplicationSession;
import JsonClasses.JRedirect;
import JsonClasses.JReturn;
import Model.Model;
import Model.Usuario;
import Utils.JSON;

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
	public void execute(JReturn r, String action) {
		super.execute(r, action);
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
		
		this.setJson(j.returnConstruct(r));
		
	}
	
	
	public void login(Usuario loginUser, JReturn r){
		boolean test = false;
		Usuario u = null;
		//Get a list of all users
		ArrayList<Model> usuarios = DAO.getInstance().select(new Usuario());
		
		//Check if exist user
		for(Model bdUser : usuarios){
			u = (Usuario) bdUser;
			System.out.println(u.dgetEmail()+" "+ u.dgetSenha());
			System.out.println(loginUser.dgetEmail()+" "+ loginUser.dgetSenha());
			if(u.dgetEmail().equalsIgnoreCase(loginUser.dgetEmail()) && u.dgetSenha().equalsIgnoreCase(loginUser.dgetSenha())){
				test = true;
				break;
			}
		}
		
		if(test){		
			this.setUserSessionLoggedin(true);
			this.setUserSession(u);
			r.getUser().setLoggedin(true);
			r.addMsg("User sucessfully indentified!");
			
			JRedirect redirect = new JRedirect();
			redirect.setRedirection((String)this.getAppSession().getMapAttribute("redirectUseCase"), (String)this.getAppSession().getMapAttribute("redirectAction") ,(String)this.getAppSession().getMapAttribute("redirectClassname")); //Sets the redirection previos saved
			r.setRedirect(redirect);
		}else{
			this.setUserSessionLoggedin(false);
			this.setUserSession(null);
			r.addSimpleError("Email and password combination could not be found. Login failed!");
		}
	}
	
	public void logout(JReturn r){
		this.setUserSessionLoggedin(false);
		this.setUserSession(null);
		JRedirect redirect = new JRedirect();
		redirect.setRedirection("Login", "login", "login");
		r.setRedirect(redirect);
	}
	
	
	
	
	
	
	/* App Session  */
	public IApplicationSession getAppSession() {return appSession;}
	public void setAppSession(IApplicationSession appSession) {this.appSession = appSession;}
	

	/* Specific User Session Methods */
	


	

	public void setUserSession( Usuario u){
		this.getAppSession().setMapAttribute("user", u);
	}
	
	public Usuario getUserSession(){
		if(this.getAppSession().getMapAttribute("user") == null)
			return null;
		
		return (Usuario) this.getAppSession().getMapAttribute("user");
	}
}
