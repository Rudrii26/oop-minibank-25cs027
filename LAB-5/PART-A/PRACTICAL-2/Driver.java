public class Driver {
    public static void main(String[] args) {

        Employee[] employees = {
            new FullTime("Rudri", 101, 50000),
            new PartTime("Aarav", 102, 80, 500),
            new Intern("Krisha", 103, 15000),
            new FullTime("Meera", 104, 60000)
        };

        double total = 0;

        for (Employee e : employees) {
            e.display();

            double salary = e.monthlySalary();
            System.out.println(", Monthly Salary: " + salary);

            if (e instanceof Intern) {
                System.out.println("Note: This employee is an Intern.");
            }

            total += salary;
        }

        System.out.println("----------------------------");
        System.out.println("Total Monthly Salary: " + total);
    }
}