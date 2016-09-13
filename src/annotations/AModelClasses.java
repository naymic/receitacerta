package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author micha
 *
 * This annotation is used for Model classes in the package model
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AModelClasses {
	
	/**
	 * Force a Model class to set the Logged User Object
	 * @return
	 */
	boolean needUserObject() default false;
	
	/**
	 * Assing to class, that is used for user
	 * @return
	 */
	boolean isUserModel() default false;
}
