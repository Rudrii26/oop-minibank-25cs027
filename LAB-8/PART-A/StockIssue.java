import java.util.*;

class OutOfStockException extends Exception {
    int shortfall;

    OutOfStockException(int shortfall) {
        super("Not enough stock available");
        this.shortfall = shortfall;
    }

    int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    HashMap<String, Integer> stock = new HashMap<>();

    Warehouse() {
        stock.put("Laptop", 5);
        stock.put("Mouse", 10);
        stock.put("Keyboard", 3);
    }

    void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero");
        }

        int available = stock.getOrDefault(item, 0);

        if (qty > available) {
            throw new OutOfStockException(qty - available);
        }

        stock.put(item, available - qty);

        System.out.println(qty + " " + item + " issued successfully");
    }
}

public class StockIssue {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        String[] items = {"Laptop", "Mouse", "Keyboard", "Laptop", "Mouse"};
        int[] quantities = {2, 15, 0, 5, 3};

        for (int i = 0; i < items.length; i++) {
            try {
                warehouse.issue(items[i], quantities[i]);

            } catch (OutOfStockException e) {
                System.out.println("Out of stock: " + items[i]);
                System.out.println("Shortfall: " + e.getShortfall());

            } catch (InvalidQuantityException e) {
                System.out.println("Invalid request: " + e.getMessage());
            }

            System.out.println();
        }

        System.out.println("All requests processed.");
    }
}