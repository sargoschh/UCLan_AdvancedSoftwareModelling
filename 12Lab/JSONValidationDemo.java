import java.util.regex.*;

public class JSONValidationDemo {
    public static void main(String[] args) {
        // Inline JSON string (as an example)
        String jsonInput = """
                {
                    "username": "user_01",
                    "age": "30",
                    "isAdmin": "false",
                    "email": "alice@example.com"
                }
                """;
        System.out.println("Original JSON input:");
        System.out.println(jsonInput);
        // Regex patterns for each field
        String usernamePattern = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        String agePattern = "^[0-9]+$";
        String booleanPattern = "^(true|false)$";
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        // Extract fields manually using regex
        String username = extractValue(jsonInput, "username");
        String age = extractValue(jsonInput, "age");
        String isAdmin = extractValue(jsonInput, "isAdmin");
        String email = extractValue(jsonInput, "email");
        // Validate each field
        validateField("username", username, usernamePattern);
        validateField("age", age, agePattern);
        validateField("isAdmin", isAdmin, booleanPattern);
        validateField("email", email, emailPattern);
    }

    // Utility method to extract value from JSON-like string
    private static String extractValue(String json, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        } else {
            return ""; // empty if not found
        }
    }

    // Utility method to validate a field using regex
    private static void validateField(String fieldName, String value, String pattern) {
        if (Pattern.matches(pattern, value)) {
            System.out.println(fieldName + " is valid: " + value);
        } else {
            System.out.println(fieldName + " is INVALID: " + value);
        }
    }
}
