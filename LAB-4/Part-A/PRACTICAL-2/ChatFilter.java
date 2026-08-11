public class ChatFilter {

    public static void filter(String[] logs, String keyword) {

        int count = 0;
        StringBuilder sb = new StringBuilder();

        for (String line : logs) {

            // Split into time, user and message
            String[] parts = line.split(" ", 3);

            // Skip malformed lines
            if (parts.length < 3) {
                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            // Case-insensitive search
            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                count++;
                sb.append(time)
                  .append(" ")
                  .append(user)
                  .append(": ")
                  .append(message)
                  .append("\n");
            }
        }

        System.out.println("Matches: " + count);
        System.out.println(sb);
    }
}