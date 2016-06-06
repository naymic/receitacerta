package Interfaces;

import java.util.HashMap;
import java.util.List;

import Model.Model;
import Utils.Return;

public interface IController {
	/**
	 * @return string contendo o nome do caso de uso(usar padrao de nome de v�riavel)
	 */
	
	
	
	public void setAction(String action);
	
	public String getAction(String action);
	
	
	/** Esse metodo deve ser chamado apos o comando setVariables, para executar uma a��o. 
	 * @param action string que indica uma a��o conhecida pelo controlador.
	 * @return Object Return que indica o status da execu��o. contem uma mensagem
	 */
	Return execute(String action);
	
	/** retorna a lista de a��es reconhecidas pelo controlador
	 * @return
	 */
	public List<String> getValidActionsList();
	
	/** executa as valida��es para uma a��o
	 * @param action
	 * @return retorn um Objeto Return informado a situa��o da valida��o.
	 */
	public Return validateAction(String action);
	
	/**
	 * @return true se o caso de uso necessita de autentica��o.
	 */
	public boolean needAuthentication();
	
	/**
	 * @return retorn true para exibir a p�gina comum a todos, false caso n�o queira.
	 */
	public boolean dontShowCommonPage();


}
