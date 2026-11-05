public class Thermostat {
    private String location;
    private int temperature;

    private static final int MIN = 16;
    private static final int MAX = 30;

    private static int activeCount = 0;

    // Constructor with location and starting temperature
    public Thermostat(String location, int startTemp) {
        this.location = location;

        // Use 22 if the given temperature is outside the allowed range
        if (startTemp >= MIN && startTemp <= MAX) {
            temperature = startTemp;
        } else {
            temperature = 22;
        }

        activeCount++;
    }

    // Constructor chaining with the default temperature
    public Thermostat(String location) {
        this(location, 22);
    }

    // Increase temperature by one degree
    public void raise() {
        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("Already at maximum (30)");
        }
    }

    // Decrease temperature by one degree
    public void lower() {
        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("Already at minimum (16)");
        }
    }

    // Getter method without a setter
    public int getTemperature() {
        return temperature;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    public static void main(String[] args) {
        Thermostat t1 = new Thermostat("Bedroom", 22);
        Thermostat t2 = new Thermostat("Hall");

        System.out.println("Raising temperature:");

        // Raise the first thermostat 10 times
        for (int i = 1; i <= 10; i++) {
            t1.raise();
            System.out.println(t1.getTemperature());
        }

        System.out.println("Lowering temperature:");

        // Lower the second thermostat 20 times
        for (int i = 1; i <= 20; i++) {
            t2.lower();
            System.out.println(t2.getTemperature());
        }

        System.out.println("Active thermostats: " + getActiveCount());
    }
}