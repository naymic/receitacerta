package controllers;

import annotations.AControllerMethod;
import jresponseclasses.JReturn;
import model.Model;
import model.Receita;
import reflection.ReflectionDAO;
import utils.Verifier;

public class ReceitaController extends CrudController {
	
	@Override
	@AControllerMethod(checkAttributes = true, needAuthentication = true)
	public JReturn salvarAction(JReturn r, Model object){
		
		Verifier vf = Verifier.getVerifier();
		Receita recipe = (Receita) object;
		ReflectionDAO rd = new ReflectionDAO(object);
		
		if(vf.verifyNumbers(recipe.dgetNome()) || vf.verifySpecialCharacters(recipe.dgetNome())){
			
			r.addAttributeError(rd.getObjectClass().getName(), "nome", "26");//Nome n�o pode conter caracteres especiais ou n�meros

			
		}else if(vf.verifySpecialCharacters(Integer.toString(recipe.dgetReceitaRendimentosTipoValor())) ||
				 vf.verifyLetters(Integer.toString(recipe.dgetReceitaRendimentosTipoValor()))){
			
			r.setSuccess(false);
			
		}
		r = super.salvarAction(r, object);
		return r;
	}
}
