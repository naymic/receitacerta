package controllers;

import annotations.AControllerMethod;
import dao.DAO;
import dao.DAORelation;
import jresponseclasses.JRedirect;
import jresponseclasses.JReturn;
import model.Model;
import model.Usuario;

public class UsuarioController extends CrudController {
	
	private static int minLenght = 5;
	
	/**
	 * Action to create a new user
	 */
	@AControllerMethod(checkAttributes = false, needAuthentication = false)
	public JReturn novoAction(JReturn r, Model object){
		return super.novoAction(r, object);
	}
	
	
	/**
	 * Action to save a new
	 * checks if userpassword combine or not
	 */
	@AControllerMethod(checkAttributes = true, needAuthentication = false)
	public JReturn salvarAction(JReturn r, Model object){

		Usuario u = (Usuario)object;

		//Check if passwords are set & if they combine
		if(this.areNewPasswordsSet(u)){

			if(this.newPasswordsCombine(u, r)){
				//Check minimum password lenght
				if(checkMinPasswordLenght(u, r)){
					u.dsetSenha(u.getNewSenha());
					return super.salvarAction(r, u);	
				}
			}
			
			return r;
		}else{
			r.addSimpleError("The passwords do not combine, please try it again!");
			return r;
		}

	}
	
	
	/**
	 * Get the userinformations, change them, and 
	 */
	@AControllerMethod(checkAttributes = false, needAuthentication = true)
	public JReturn editAction(JReturn r, Model object){
		
		try{
		Usuario u = new Usuario();
		u.setId(this.getUserSession().getId());
		super.editAction(r, u);
		}catch(NullPointerException npe){
			
			//set the redirection to the HTTP session
			JRedirect jr = new JRedirect();
			jr.setRedirection("Usuario", "Usuario", "edit");
			this.setRedirect(jr);
			
			//set the redirection to the JReturn
			JRedirect jr1 = new JRedirect();
			jr.setRedirection("Login", "Login", "login");
			r.setRedirect(jr);
		}
		
		return r;
	}
	
	
	/**
	 * Saves the edited userinformations
	 * @param r
	 * @param object
	 * @return
	 */
	@AControllerMethod(checkAttributes = false, needAuthentication = true)
	public JReturn editsalvarAction(JReturn r, Model object){
		
		Usuario u = new Usuario();
		u.setId(this.getUserSession().getId());
		Usuario user = (Usuario)DAORelation.getInstance().select(u).get(0);
		
		Usuario updatedUser = (Usuario)object;
		
		//Check if passwords are set
		if(this.areNewAndOldPasswordSet(updatedUser)  ){
			
			if(!checkMinPasswordLenght(updatedUser, r)){
				return r;
			}else if(!newPasswordsCombine(updatedUser, r) ){
				return r;
			}else if(!this.oldPasswordCombine(updatedUser, user)){
				r.addSimpleError("The old password don't combine with the password in the database!");
			}else{
				updatedUser.dsetId(user.getId());
				updatedUser.dsetSenha(updatedUser.getNewSenha());
				DAORelation.getInstance().save(updatedUser, r);
				r.addMsg("Your password and Userinformations are updated!");
			}
			
		}else{

			updatedUser.dsetId(user.getId());
			updatedUser.dsetSenha(user.dgetSenha());
			DAORelation.getInstance().save(updatedUser, r);
			r.addMsg("Your Userinformations are updated!");
		}
		
		
		return r;
	}
	
	
	/**
	 * Check if old and new passwords are set
	 * @param usr
	 * @return
	 */
	private boolean areNewPasswordsSet(Usuario usr){
		boolean test = true;
		
		if(usr.getNewSenha() == null || usr.getNewSenha().length() == 0){
			test = false;
		}
		
		if(usr.getRepeatedSenha() == null || usr.getRepeatedSenha().length() == 0){
			test = false;
		}
		
		
		
		return test;
		
	}

	/**
	 * Check if the new and old password are set in the object
	 * @param usr
	 * @return
	 */
	private boolean areNewAndOldPasswordSet(Usuario usr) {
		boolean test = true;
		
		if(usr.getOldSenha() == null || usr.getOldSenha().length() == 0 || !this.areNewPasswordsSet(usr)){
			test = false;
		}
		return test ;
	}
	
	
	/**
	 * Checks if new password and repeated password combine
	 * @param usr
	 * @param r 
	 * @return
	 */
	private boolean newPasswordsCombine(Usuario usr, JReturn r){
		if(usr.getNewSenha().equalsIgnoreCase(usr.getRepeatedSenha()))
				return true;
		else
			r.addSimpleError("The new password and repeated password don't combine!");
		
		return false;
	}
	
	/**
	 * 	 * Checks if old password combine with password stored in database
	 * @param usr
	 * @param userFromDatabase
	 * @return
	 */
	private boolean oldPasswordCombine(Usuario usr, Usuario userFromDatabase){
		return utils.CryptString.crypt(usr.getOldSenha()).equalsIgnoreCase(userFromDatabase.dgetSenha());
	}
	
	/**
	 * Checks the minimum password lenght
	 * @param usr
	 * @param r 
	 * @return
	 */
	private boolean checkMinPasswordLenght(Usuario usr, JReturn r){
		boolean test = true;
		
		
		if( usr.getNewSenha().length() < minLenght || usr.getRepeatedSenha().length() < minLenght){
			test = false;
			r.addSimpleError("The password lenght need to be more than "+ minLenght +" !");
		}
	
		
		return test;
	}
	

}
