package controllers;

public class UsuarioController extends CrudController {

	@Override
	public boolean needAuthentication(){
		return false;
	}
}
