package controllers;

import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import exceptions.NoActionException;
import interfaces.IApplicationSession;
import interfaces.IUser;
import jsonclasses.JRedirect;
import jsonclasses.JReturn;
import model.Model;
import model.Usuario;
import utils.Transform;

public class LoginController extends GenericController{
	
	
	public void loginAction(Usuario loginUser, JReturn r){
		boolean test = false;
		Usuario u = null;
		//Get a list of all users
		
		ArrayList<Model> usuarios = DAO.getInstance().select(loginUser);
		
		//Check if exist user
		for(Model bdUser : usuarios){
			u = (Usuario) bdUser;
			//System.out.println(u.dgetEmail()+" "+ u.dgetSenha());
			//System.out.println(loginUser.dgetEmail()+" "+ loginUser.dgetSenha());
			if(u.dgetEmail().equalsIgnoreCase(loginUser.dgetEmail()) && u.dgetSenha().equalsIgnoreCase(loginUser.dgetSenha())){
				test = true;
				break;
			}
		}
		
		if(test){		
			u.setLoggedin(true);
			this.setUserSession(u);
			r.setUser(u);
			r.addMsg("User sucessfully indentified!");
			r.setRedirect((JRedirect)this.getAppSession().getMapAttribute("redirect"));
			
		}else{
			r.setUser(this.resetUser());
			r.addSimpleError("Email and password combination could not be found. Login failed!");
		}
	}
	
	/**
	 * Log out a logged user and send a redirect to the login page
	 * @param r
	 */
	public void logoutAction(JReturn r){
		
		r.setUser(this.resetUser());
		r.getRedirect().setRedirection("Login", "login", "login");
	}
	
	/**
	 * Return an empty user and reset the Session
	 * @return IUser
	 */
	private IUser resetUser(){
		Usuario user = new Usuario();
		user.setLoggedin(false);
		this.setUserSession(user);
		return user;
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
