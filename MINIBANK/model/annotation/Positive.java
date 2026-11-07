package model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Practical 7: Custom validation annotation requiring positive numeric values
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Positive {
    String message() default "must be > 0";
}
