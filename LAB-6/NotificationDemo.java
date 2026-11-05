interface Notifier {
    void send(String message);
}

interface Urgent {
}

class EmailSender implements Urgent {
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SMSSender {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

public class NotificationDemo {
    public static void main(String[] args) {

        Notifier email = message ->
            System.out.println("Email: " + message);

        Notifier sms = message ->
            System.out.println("SMS: " + message);

        Notifier[] senders = {email, sms};

        String message = "Server maintenance at 10 PM";

        for (Notifier sender : senders) {
            sender.send(message);

            if (sender instanceof Urgent) {
                sender.send(message);
            }
        }
    }
}