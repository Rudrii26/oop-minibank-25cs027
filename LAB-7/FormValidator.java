import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    @MaxLength(20)
    String name;

    @NotBlank
    @MaxLength(30)
    String email;

    @NotBlank
    @MaxLength(10)
    String mobile;

    SignupForm(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
}

class FormChecker {
    ArrayList<String> validate(Object obj) {
        ArrayList<String> errors = new ArrayList<>();

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                String fieldName = field.getName();

                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (value == null || value.toString().trim().isEmpty()) {
                        errors.add(fieldName + " cannot be blank");
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength max = field.getAnnotation(MaxLength.class);

                    if (value != null && value.toString().length() > max.value()) {
                        errors.add(fieldName + " exceeds maximum length " + max.value());
                    }
                }

            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }

        return errors;
    }
}

public class FormValidator {
    public static void main(String[] args) {
        SignupForm form = new SignupForm(
            "",
            "verylongemailaddress@example.com",
            "98765432101"
        );

        FormChecker checker = new FormChecker();

        ArrayList<String> errors = checker.validate(form);

        if (errors.isEmpty()) {
            System.out.println("Form is valid");
        } else {
            System.out.println("Validation Errors:");

            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}