package rebelalliance.smartcash.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for dates.
 */
public class DateUtil {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Formats a date as a string in <code>yyyy-MM-dd</code> format.
     *
     * @param date The date to format.
     *
     * @return The formatted date.
     */
    public static String format(LocalDate date) {
        return date.format(FORMAT);
    }
}
