package interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import model.Model;
/***
 * 
 * @author micha
 *
 * Interface that defines methods to a generic cache
 * for objects
 *
 */
public interface ICache {
		
	
	public void addToCache(Class<?> objectClass, Integer pk, Model value);
	
	public Model getObjectFromCache(Class<?> objectClass, Integer pk);
	
	public boolean existClassInCache(Class<?> objectClass);
	
	public Model existObjectInCache(Class<?> objectClass, Integer pk);
	
	public void removeFromCache(Class<?> objectClass, Integer pk);
	
}
