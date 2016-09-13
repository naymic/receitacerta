package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * 
 * @author micha
 *
 * This annotation ist uset for Usecase Action Methods
 * Usecase Action Methods are in the Package controllers
 * and allways concatenates <action name>Action
 *  e.g buscaAction(JReturn r, Model m)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AControllerMethod {
	boolean checkAttributes();
	boolean needAuthentication() default false; //not implemented allready
}
