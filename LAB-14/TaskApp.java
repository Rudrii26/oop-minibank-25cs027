import java.util.ArrayList;
import java.util.Scanner;

class Task {
    private String title;

    Task(String title) {
        this.title = title;
    }

    String getTitle() {
        return title;
    }
}

class TaskService {
    private ArrayList<Task> tasks = new ArrayList<>();

    void addTask(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        tasks.add(new Task(title));
    }

    int getTaskCount() {
        return tasks.size();
    }

    ArrayList<Task> getTasks() {
        return tasks;
    }

    void performAction(String action) {
        if (action.equals("add") || action.equals("list")) {
            System.out.println("Valid action");
        } else {
            throw new IllegalArgumentException("Invalid action");
        }
    }
}

public class TaskApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskService service = new TaskService();

        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter task name: ");
                        String title = sc.nextLine();

                        service.addTask(title);
                        System.out.println("Task added successfully");
                        break;

                    case 2:
                        System.out.println("Task List:");

                        for (Task task : service.getTasks()) {
                            System.out.println(task.getTitle());
                        }

                        System.out.println("Total Tasks: " + service.getTaskCount());
                        break;

                    case 3:
                        System.out.println("Exiting application");
                        sc.close();
                        return;

                    default:
                        service.performAction("invalid");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}