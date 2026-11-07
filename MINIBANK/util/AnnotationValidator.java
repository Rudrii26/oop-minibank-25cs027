package util;

import model.annotation.MaxLength;
import model.annotation.Positive;

import java.lang.reflect.Field;
import java.util.ArrayList;

// Practical 7: Reflection-based validator checking custom annotations at runtime
public class AnnotationValidator {

    public static String[] validate(Object obj) {
        if (obj == null) {
            return new String[0];
        }

        ArrayList<String> errors = new ArrayList<>();

        // Inspect fields of the object's class and its parent class (Account)
        for (Class<?> c = obj.getClass(); c != null && c != Object.class; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();

            for (Field field : fields) {
                // Bypass private access modifier so reflection can read the field value
                field.setAccessible(true);

                try {
                    // Check @Positive annotation on numeric fields (e.g. balance)
                    if (field.isAnnotationPresent(Positive.class)) {
                        Object val = field.get(obj);
                        if (val instanceof Number) {
                            long num = ((Number) val).longValue();
                            if (num <= 0) {
                                Positive pos = field.getAnnotation(Positive.class);
                                errors.add(pos.message());
                            }
                        }
                    }

                    // Check @MaxLength annotation on String fields
                    if (field.isAnnotationPresent(MaxLength.class)) {
                        Object val = field.get(obj);
                        if (val instanceof String) {
                            String str = (String) val;
                            MaxLength max = field.getAnnotation(MaxLength.class);
                            if (str.length() > max.value()) {
                                errors.add(field.getName() + " exceeds max length of " + max.value());
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Cannot access field: " + field.getName());
                }
            }
        }

        return errors.toArray(new String[0]);
    }
}
