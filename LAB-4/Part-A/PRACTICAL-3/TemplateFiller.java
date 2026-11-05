import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {

    public static String fill(String template, String[] names, String[] values) {
        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);

        StringBuilder result = new StringBuilder();

        // Find every placeholder in the template
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            String replacement = "[?]";

            // Search for the placeholder in the names array
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(placeholder)) {
                    replacement = values[i];
                    break;
                }
            }

            // Replace the placeholder with its value
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);

        return result.toString();
    }
}