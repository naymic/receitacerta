package controllers;

import annotations.AControllerMethod;
import dao.DAO;
import dao.DAORelation;
import jresponseclasses.JRedirect;
import jresponseclasses.JReturn;
import model.Model;
import model.Usuario;

public class UsuarioController extends CrudController {
	
	@AControllerMethod(checkAttributes = false)
	public JReturn novoAction(JReturn r, Model object){
		return r;
	}
	
	@AControllerMethod(checkAttributes = true)
	public JReturn salvarAction(JReturn r, Model object){
		return super.salvarAction(r, object);
	}
	
	@AControllerMethod(checkAttributes = false)
	public JReturn editAction(JReturn r, Model object){
		
		try{
		Usuario u = new Usuario();
		u.setId(this.getUserSession().getId());
		Model user = DAORelation.getInstance().select(u).get(0);
		r.getData().setDataObject(user);
		}catch(NullPointerException npe){
			JRedirect jr = new JRedirect();
			jr.setRedirection("Login", "Login", "login");
			r.setRedirect(jr);
		}
		
		return r;
	}
	
	@AControllerMethod(checkAttributes = false)
	public JReturn editsalvarAction(JReturn r, Model object){
		
		Usuario u = new Usuario();
		u.setId(this.getUserSession().getId());
		Usuario user = (Usuario)DAORelation.getInstance().select(u).get(0);
		
		Usuario updatedUser = (Usuario)object;
		
		if(user.getOldSenha() !=null && updatedUser.getOldSenha().length() > 0 &&  updatedUser.getNewSenha() != null && updatedUser.getNewSenha().length() == 0){
			r.addSimpleError("Your new password is empty, but your old is set, this is not allowed!");
		}else if(updatedUser.getOldSenha() != null && utils.CryptString.crypt(updatedUser.getOldSenha()).equals(user.dgetSenha()) ){
			
			if(updatedUser.getNewSenha() != null && updatedUser.getNewSenha1() != null && updatedUser.getNewSenha().equals(updatedUser.getNewSenha1())){
				updatedUser.dsetId(user.getId());
				updatedUser.dsetSenha(updatedUser.getNewSenha());
				DAORelation.getInstance().save(updatedUser, r);
				r.addMsg("Your password and Userinformations are updated!");
			}else{
				r.addSimpleError("Your new password repetition is not combining!");
			}
				
		}else if(user.getOldSenha() == null){

			updatedUser.dsetId(user.getId());
			updatedUser.dsetSenha(user.dgetSenha());
			DAORelation.getInstance().save(updatedUser, r);
			r.addMsg("Your Userinformations are updated!");
		}else{
			r.addSimpleError("Your old password was wrong, update was interrupted!");
		}
		
		
		return r;
	}
	
	
	
	
	
	
	
	@Override
	public boolean needAuthentication(){
		return false;
	}
}
