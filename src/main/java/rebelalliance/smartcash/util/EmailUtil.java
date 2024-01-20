package rebelalliance.smartcash.util;

import java.util.regex.Pattern;

/**
 * Utility class for emails.
 */
public class EmailUtil {
    static Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");

    /**
     * Checks if the given email is valid. <br/>
     * <a href="https://html.spec.whatwg.org/multipage/input.html#valid-e-mail-address">Uses the HTML spec.</a>
     *
     * @param email The email to check.
     *
     * @return <code>true</code> if the email is valid, <code>false</code> otherwise.
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
