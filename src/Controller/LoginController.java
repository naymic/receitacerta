package Controller;

import java.util.ArrayList;
import java.util.List;

import GenericDao.DAO;
import Interfaces.IApplicationSesssion;
import Model.Model;
import Model.Usuario;
import Utils.JSON;
import Utils.Return;

public class LoginController extends GenericController{
	
	
	LoginController(){
		super();
		
	}
	
	
	@Override
	public void execute(Return r, String action) {
		r = this.validateAction(action);
		JSON j = new JSON();
		
		//Iniciate user from view
		Usuario loginUser = (Usuario)super.initObj(r);
				
		if(r.isSuccess()){
			
			//Get a list of all users
			ArrayList<Model> usuarios = DAO.getInstance().select(new Usuario());
			
			
		}else{
			this.setUniqueJson(j.messageConstruct(r));
		}
		
	}
	
	
	public void loginCheck(Usuario loginUser, List<Model> usuarios, Return r){
		boolean test = false;
		
		//Check if exist user
		for(Model bdUser : usuarios){
			Usuario u = (Usuario) bdUser;
			
			if(u.dgetEmail().equalsIgnoreCase(loginUser.dgetEmail()) && u.dgetSenha().equalsIgnoreCase(loginUser.dgetSenha())){
				test = true;
				break;
			}
		}
		
		if(test)
			r.addMsg("User sucessfully indentified");
		else
			r.addSimpleError("Email and password combination do not combine!");
		
	}
	
	public boolean isUserAuthenticate(IApplicationSesssion ics){
		return (boolean)ics.getMapAttribute("loggedin");
	}
	
	public void setUserAuthenticate(IApplicationSesssion ics, boolean loggedin){
		ics.setMapAttribute("loggendin", loggedin);
	}
	
}
