package Interfaces;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Model.Model;
import Utils.Return;

public interface IController {
	/**
	 * Gets a hashmap to save parts of json to return it to the view
	 * @return JSON Hashmap
	 */
	public HashMap<String, String> getJsonMapper();
	
	/**
	 * Set a simple string for json r
	 * @param JSON String
	 */
	public void setUniqueJson(String json);

	/**
	 * Get a simple json string
	 * @return JSON String 
	 */
	public String getUniqueJson();

	
	
	/** Esse metodo deve ser chamado apos o comando setVariables, para executar uma a��o. 
	 * @param r 
	 * @param action string que indica uma a��o conhecida pelo controlador.
	 * @return Object Return que indica o status da execu��o. contem uma mensagem
	 */
	void execute(Return r, String action);
	
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

	
	/* View Variable Mapping */
	
	/**
	 * Add a variable from the view to a mapper
	 * @param key
	 * @param value
	 */
	public void addVariable(String key, Object value);
	
	/**
	 * Get a value from the variable mapping
	 * @param key
	 * @return
	 */
	public Object getVariableValue(String key);
	
	/**
	 * Get a iterator of all keys of the variables
	 * @return
	 */
	public Iterator<String> getVariableKeys();
	
	/**
	 * Get a iterator of all values of the variables
	 * @return
	 */
	public Iterator<Object> getVariableValues();
	
	
	/**
	 * Get the application session
	 * @return
	 */
	public IApplicationSession getAppSession();

	/**
	 * Sets the application session
	 * @param appSession
	 */
	public void setAppSession(IApplicationSession appSession);
	
	/**
	 * Get the User Session logged in status
	 * @return
	 */
	public boolean getUserSessionLoggedin();
	
	/**
	 * Set the User Session logged in status
	 * @param loggedin
	 */
	public  void setUserSessionLoggedin(boolean loggedin);
	
	
}
