import java.util.Scanner;

record Vehicle(String number, String type) {
}

public class TollBooth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int total = 0;
        int bikes = 0;
        int cars = 0;
        int trucks = 0;

        while (true) {
            System.out.print("Enter vehicle number (done to stop): ");
            String number = sc.nextLine();

            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike/car/truck): ");
            String type = sc.nextLine().toLowerCase();

            Vehicle vehicle = new Vehicle(number, type);

            // Calculate toll according to vehicle type
            int toll = switch (vehicle.type()) {
                case "bike" -> 20;
                case "car" -> 50;
                case "truck" -> 150;
                default -> 0;
            };

            if (toll == 0) {
                System.out.println("Invalid vehicle type");
                continue;
            }

            total = total + toll;

            // Count the number of each vehicle
            switch (vehicle.type()) {
                case "bike" -> bikes++;
                case "car" -> cars++;
                case "truck" -> trucks++;
            }

            System.out.println("Toll: " + toll);
        }

        System.out.println("Total toll: " + total);

        // Find the vehicle type with the highest count
        if (bikes >= cars && bikes >= trucks) {
            System.out.println("Most frequent: bike");
        } else if (cars >= bikes && cars >= trucks) {
            System.out.println("Most frequent: car");
        } else {
            System.out.println("Most frequent: truck");
        }

        sc.close();
    }
}