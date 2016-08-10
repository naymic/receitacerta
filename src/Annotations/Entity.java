package Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation interface for Model Classes 
 * used to get informations about the attributes of
 * a Class and in the database tables
 * @author naymic
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

	boolean pk() default false;
	boolean fk() default false;
	String fk_modelname() default "";
	boolean required() default true;
	String attributeName();
	boolean isMapped() default false;
}
