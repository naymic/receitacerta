package controllers;

import annotations.AControllerMethod;
import jresponseclasses.JReturn;
import model.Model;

public class UsuarioController extends CrudController {
	
	@AControllerMethod(checkAttributes = false)
	public JReturn novoAction(JReturn r, Model object){
		return r;
	}
	
	@AControllerMethod(checkAttributes = true)
	public JReturn salvarAction(JReturn r, Model object){
		return super.salvarAction(r, object);
	}
	
	@Override
	public boolean needAuthentication(){
		return false;
	}
}
