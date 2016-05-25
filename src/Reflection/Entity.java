package Reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

	boolean pk() default false;
	boolean fk() default false;
	boolean required() default true;
	String attributeName();
}
