package controllers;

import annotations.AControllerMethod;
import dao.DAO;
import dao.DAORelation;
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
	
	@AControllerMethod(checkAttributes = false, needAuthentication=true)
	public JReturn editAction(JReturn r, Model object){
		
		Usuario u = new Usuario();
		u.setId(r.getUser().getId());
		Model user = DAORelation.getInstance().select(u).get(0);
		r.getData().setDataObject(user);
		
		return r;
	}
	
	
	
	@Override
	public boolean needAuthentication(){
		return false;
	}
}
