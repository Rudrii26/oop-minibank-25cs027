interface Switchable {
    void on();
    void off();

    default void toggle(boolean isOn) {
        if (isOn)
            off();
        else
            on();
    }
}

class Fan implements Switchable {
    public void on() {
        System.out.println("Fan turned ON");
    }

    public void off() {
        System.out.println("Fan turned OFF");
    }
}

class Light implements Switchable {
    public void on() {
        System.out.println("Light turned ON");
    }

    public void off() {
        System.out.println("Light turned OFF");
    }
}

@FunctionalInterface
interface SwitchPolicy {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {

        Switchable[] devices = {
            new Fan(),
            new Light()
        };

        System.out.println("Toggling devices:");

        for (Switchable device : devices) {
            device.toggle(false);
        }

        System.out.println("\nUsing anonymous class:");

        SwitchPolicy policy1 = new SwitchPolicy() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        System.out.println("Fan at 10 AM: " +
                policy1.maySwitchOn(devices[0], 10));

        System.out.println("Light at 11 PM: " +
                policy1.maySwitchOn(devices[1], 23));

        System.out.println("\nUsing lambda:");

        SwitchPolicy policy2 = (device, hour) ->
                hour >= 6 && hour <= 22;

        System.out.println("Fan at 8 AM: " +
                policy2.maySwitchOn(devices[0], 8));

        System.out.println("Light at 11 PM: " +
                policy2.maySwitchOn(devices[1], 23));
    }
}