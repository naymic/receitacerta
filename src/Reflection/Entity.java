package Reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

	boolean pk() default false;
	boolean fk() default false;
	String fk_tablename() default "";
	boolean required() default true;
	String attributeName();
}
