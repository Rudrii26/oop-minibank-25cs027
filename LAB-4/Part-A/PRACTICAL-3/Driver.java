public class Driver {
    public static void main(String[] args) {
        String template = "Dear {name}, order {id} ships {date}.";

        String[] names = {"name", "id"};
        String[] values = {"Riya", "A07"};

        // Fill the template using the supplied values
        String result = TemplateFiller.fill(template, names, values);

        System.out.println(result);
    }
}