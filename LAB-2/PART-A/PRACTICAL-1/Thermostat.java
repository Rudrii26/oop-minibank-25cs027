import java.util.Scanner;

public class Thermostat {

    private String location;
    private int temperature;

    private static final int MIN = 16;
    private static final int MAX = 30;

    private static int activeCount = 0;

    public Thermostat(String location, int startTemp) {
        this.location = location;

        if (startTemp >= MIN && startTemp <= MAX)
            temperature = startTemp;
        else
            temperature = 22;

        activeCount++;
    }

    public Thermostat(String location) {
        this(location, 22);
    }

    public void raise() {
        if (temperature < MAX)
            temperature++;
        else
            System.out.println("Already at maximum (30)");
    }

    public void lower() {
        if (temperature > MIN)
            temperature--;
        else
            System.out.println("Already at minimum (16)");
    }

    public int getTemperature() {
        return temperature;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter location of Thermostat 1: ");
        String loc1 = sc.nextLine();

        System.out.print("Enter starting temperature: ");
        int temp = sc.nextInt();
        sc.nextLine();

        Thermostat t1 = new Thermostat(loc1, temp);

        System.out.print("Enter location of Thermostat 2: ");
        String loc2 = sc.nextLine();

        Thermostat t2 = new Thermostat(loc2);

        System.out.println("\nIncreasing Temperature");

        for (int i = 1; i <= 10; i++) {
            t1.raise();
            System.out.println("Temperature = " + t1.getTemperature());
        }

        System.out.println("\nDecreasing Temperature");

        for (int i = 1; i <= 20; i++) {
            t1.lower();
            System.out.println("Temperature = " + t1.getTemperature());
        }

        System.out.println("\nActive Thermostats = " + Thermostat.getActiveCount());

        sc.close();
    }
}