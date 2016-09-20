package interfaces;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import exceptions.NoActionException;
import jresponseclasses.JReturn;
import reflection.ReflectionController;
import utils.RequestObject;

public interface IController {
	/**
	 * Gets a hashmap to save parts of json to return it to the view
	 * @return JSON Hashmap
	 */
	public String getJson();
	
	/**
	 * Set a simple string for json r
	 * @param JSON String
	 */
	public void setJson(String json);
	
	
	/** Esse metodo deve ser chamado apos o comando setVariables, para executar uma a��o. 
	 * @param r 
	 * @param action string que indica uma a��o conhecida pelo controlador.
	 * @return Object Return que indica o status da execu��o. contem uma mensagem
	 */
	public void execute(JReturn r, String action);
	

	
	/** executa as valida��es para uma a��o
	 * @param action
	 * @return 
	 * @return retorn um Objeto Return informado a situa��o da valida��o.
	 */
	Method validateAction(ReflectionController rController, String action) throws NoActionException;
	
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
	public boolean isUserSessionLoggedin();
	
	/**
	 * Set the User Session logged in status
	 * @param loggedin
	 */
	public  void setUserSession(IUser user);
	
	/**
	 * Get the User Session logged in status
	 * @param loggedin
	 */
	public  IUser getUserSession();
	
	/**
	 * Get the object received from the view
	 */
	public RequestObject getObject();

	
	/**
	 * Get page number and pass it to DAO to 
	 * limit the min and max selected rows
	 * @return
	 */
	public Integer getPageNumber();

	/**
	 * Set page number and pass it to DAO to 
	 * limit the min and max selected rows
	 * @return
	 */
	public void setPageNumber(Integer object);
	
}
