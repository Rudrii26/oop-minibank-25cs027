import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] logs = {
            "10:05 alice Hello there",
            "10:10 bob How are you?",
            "10:15 charlie Have a nice day",
            "10:20 david"
        };

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        // Filter the chat logs and display the report
        String report = ChatFilter.filter(logs, keyword);

        System.out.println(report);

        sc.close();
    }
}