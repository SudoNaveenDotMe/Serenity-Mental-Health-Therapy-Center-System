package lk.ijse.the_seranity_mental_health_therapy_center.util;

import java.util.regex.Pattern;

public class RegexUtil {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_PATTERN = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return Pattern.matches(PHONE_PATTERN, phone);
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
