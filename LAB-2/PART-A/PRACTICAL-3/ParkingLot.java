public class ParkingLot {
    int twoWheelerCapacity;
    int fourWheelerCapacity;

    int twoWheelers = 0;
    int fourWheelers = 0;

    static int revenue = 0;

    ParkingLot(int twoCapacity, int fourCapacity) {
        twoWheelerCapacity = twoCapacity;
        fourWheelerCapacity = fourCapacity;
    }

    // Park a two-wheeler
    void parkTwoWheeler() {
        if (twoWheelers < twoWheelerCapacity) {
            twoWheelers++;
            revenue = revenue + 20;
            System.out.println("Two-wheeler parked");
        } else {
            System.out.println("Two-wheeler parking is full");
        }
    }

    // Park a four-wheeler
    void parkFourWheeler() {
        if (fourWheelers < fourWheelerCapacity) {
            fourWheelers++;
            revenue = revenue + 40;
            System.out.println("Four-wheeler parked");
        } else {
            System.out.println("Four-wheeler parking is full");
        }
    }

    // Remove a two-wheeler
    void leaveTwoWheeler() {
        if (twoWheelers > 0) {
            twoWheelers--;
            System.out.println("Two-wheeler left");
        }
    }

    // Remove a four-wheeler
    void leaveFourWheeler() {
        if (fourWheelers > 0) {
            fourWheelers--;
            System.out.println("Four-wheeler left");
        }
    }

    public static void main(String[] args) {
        ParkingLot p = new ParkingLot(1, 1);

        p.parkTwoWheeler();
        p.parkTwoWheeler();

        p.parkFourWheeler();
        p.parkFourWheeler();

        p.leaveTwoWheeler();

        System.out.println("Two-wheelers: " + p.twoWheelers);
        System.out.println("Four-wheelers: " + p.fourWheelers);
        System.out.println("Total revenue: " + revenue);
    }
}