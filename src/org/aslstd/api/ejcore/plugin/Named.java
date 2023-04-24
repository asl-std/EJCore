package org.aslstd.api.ejcore.plugin;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

@Target(TYPE)
public @interface Named {
	String key() default "";
}
