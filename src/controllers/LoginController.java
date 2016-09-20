package controllers;

import java.util.ArrayList;
import java.util.List;

import annotations.AControllerMethod;
import dao.DAO;
import exceptions.NoActionException;
import interfaces.IApplicationSession;
import interfaces.IUser;
import jresponseclasses.JRedirect;
import jresponseclasses.JReturn;
import model.Model;
import model.Usuario;
import utils.Transform;

public class LoginController extends GenericController{
	
	@AControllerMethod(checkAttributes = false)
	public void loginAction(JReturn r, Model obj){
		boolean test = false;
		Usuario loginUser = this.prepareUserObjectForLogin((Usuario)obj); //Remove any value, except password and email
		
		Usuario u = null;
		//Get a list of all users
		
		
		ArrayList<Model> usuarios = DAO.getInstance().select(loginUser);
		
		//Check if exist user
		for(Model bdUser : usuarios){
			u = (Usuario) bdUser;

			if(u.dgetEmail().trim().equalsIgnoreCase(loginUser.dgetEmail()) && u.dgetSenha().trim().equalsIgnoreCase(loginUser.dgetSenha())){
				test = true;
				break;
			}
		}
		
		if(test){		
			u.setLoggedin(true);
			this.setUserSession(u);
			r.setUser(u);
			r.addMsg("User sucessfully indentified!");
			//r.setRedirect((JRedirect)this.getAppSession().getMapAttribute("redirect"));
			
		}else{
			r.setUser(this.resetUser());
			r.addSimpleError("Email and password combination could not be found. Login failed!");
		}
	}
	
	/**
	 * Log out a logged user and send a redirect to the login page
	 * @param r
	 */
	@AControllerMethod(checkAttributes = false)
	public void logoutAction(JReturn r, Model loginUser){
		
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
	
	
	/**
	 * Removes any value from the User object, 
	 * exceptio password and email
	 * @param usr			User object from the view
	 * @return Usuario 		Clean user Object
	 */
	public Usuario prepareUserObjectForLogin(Usuario usr){
		Usuario loginUsr = new Usuario();
		loginUsr.dsetEmail(usr.dgetEmail().trim());
		loginUsr.dsetSenha(usr.dgetSenha().trim());
		return loginUsr;
	}

	
	
	@Override
	public boolean needAuthentication() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
