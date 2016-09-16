package controllers;

import jresponseclasses.JReturn;
import model.Model;

public class UsuarioController extends CrudController {
	
	public JReturn novoAction(JReturn r, Model object){
		return r;
	}
	
	public JReturn salvarAction(JReturn r, Model object){
		return super.salvarAction(r, object);
	}
	
	@Override
	public boolean needAuthentication(){
		return false;
	}
}
