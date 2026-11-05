class Box<T> {
    private T value;

    void set(T value) {
        this.value = value;
    }

    T get() {
        return value;
    }
}

class Pair<A, B> {
    private A first;
    private B second;

    Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    A getFirst() {
        return first;
    }

    B getSecond() {
        return second;
    }
}

public class GenericContainers {

    static <T> T getFirstElement(T[] array) {
        return array[0];
    }

    public static void main(String[] args) {

        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        System.out.println("Integer Box: " + intBox.get());

        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Java");
        System.out.println("String Box: " + stringBox.get());

        Pair<String, Integer> student = new Pair<>("Riya", 20);

        System.out.println("\nPair Details:");
        System.out.println("Name: " + student.getFirst());
        System.out.println("Age: " + student.getSecond());

        Integer[] numbers = {10, 20, 30, 40};
        String[] names = {"Riya", "Amit", "Neha"};

        System.out.println("\nFirst Integer: " + getFirstElement(numbers));
        System.out.println("First String: " + getFirstElement(names));
    }
}