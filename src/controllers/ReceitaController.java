package controllers;

import annotations.AControllerMethod;
import jresponseclasses.JReturn;
import model.Model;
import model.Receita;
import utils.Verifier;

public class ReceitaController extends CrudController {
	
	@Override
	public JReturn salvarAction(JReturn r, Model object){
		
		Verifier vf = Verifier.getVerifier();
		Receita recipe = (Receita) object;
		
		
		if(vf.verifyNumbers(recipe.dgetNome()) || vf.verifySpecialCharacters(recipe.dgetNome()){
			r.setSuccess(false);
			r.addMsg("Nome não pode conter caracteres especiais");
		}else if(vf.verifySpecialCharacters(recipe.dgetReceitaRendimentosTipoValor())){
		
		r = super.salvarAction(r, object);
	}

}
