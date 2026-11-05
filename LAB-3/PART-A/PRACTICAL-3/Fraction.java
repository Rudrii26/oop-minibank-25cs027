import java.util.Objects;

public class Fraction {
    private int num;
    private int den;

    public Fraction(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }

        // Keep the denominator positive
        if (den < 0) {
            num = -num;
            den = -den;
        }

        int gcd = findGCD(Math.abs(num), den);

        // Reduce the fraction to its simplest form
        this.num = num / gcd;
        this.den = den / gcd;
    }

    // Find the greatest common divisor
    private int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    // Compare the reduced numerator and denominator
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Fraction f = (Fraction) obj;

        return num == f.num && den == f.den;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}