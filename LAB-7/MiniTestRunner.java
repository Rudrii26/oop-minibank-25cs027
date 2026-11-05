import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class TestCases {
    @Run
    public void testAddition() {
        System.out.println("Addition test executed");
    }

    @Run
    public void testSubtraction() {
        System.out.println("Subtraction test executed");
    }

    @Run
    public void testMultiplication() {
        System.out.println("Multiplication test executed");
    }

    public void normalMethod() {
        System.out.println("Normal method executed");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) {
        TestCases obj = new TestCases();

        Method[] methods = obj.getClass().getDeclaredMethods();

        int count = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(Run.class)) {
                try {
                    method.invoke(obj);
                    count++;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Total methods executed: " + count);
    }
}