package controllers;

import annotations.AControllerMethod;
import jresponseclasses.JReturn;
import model.Model;
import model.Receita;
import utils.Verifier;

public class ReceitaController extends CrudController {
	
	@Override
	@AControllerMethod(checkAttributes = true, needAuthentication = true)
	public JReturn salvarAction(JReturn r, Model object){
		
		Verifier vf = Verifier.getVerifier();
		Receita recipe = (Receita) object;
		
		
		if(vf.verifyNumbers(recipe.dgetNome()) || vf.verifySpecialCharacters(recipe.dgetNome())){
			
			r.addSimpleError("26");//Nome não pode conter caracteres especiais
			r.setSuccess(false);
			
		}else if(vf.verifySpecialCharacters(Integer.toString(recipe.dgetReceitaRendimentosTipoValor())) ||
				 vf.verifyLetters(Integer.toString(recipe.dgetReceitaRendimentosTipoValor()))){
			
			
			r.setSuccess(false);
			
		}
		r = super.salvarAction(r, object);
		return r;
	}
}
