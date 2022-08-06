package ru.aslcraft.api.ejcore.json.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Exclude class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Exclude {}
