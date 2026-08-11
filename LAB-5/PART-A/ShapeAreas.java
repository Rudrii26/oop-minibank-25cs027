abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private final double l, b;

    Rectangle(double l, double b) {
        this.l = l;
        this.b = b;
    }

    @Override
    double area() {
        return l * b;
    }
}

class Triangle extends Shape {
    private final double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    double area() {
        return 0.5 * base * height;
    }
}

public class ShapeAreas {
    public static void main(String[] args) {

        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 5.0),
            new Triangle(5.0, 4.0)
        };

        double total = 0;
        double largest = 0;
        Shape largestShape = null;

        for (Shape s : shapes) {
            double a = s.area();

            total = total + a;

            if (a > largest) {
                largest = a;
                largestShape = s;
            }
        }

        System.out.printf("Total area: %.2f%n", total);
        System.out.printf("Largest area: %.2f (%s)%n",largest,largestShape.getClass().getSimpleName());
    }
}
```
