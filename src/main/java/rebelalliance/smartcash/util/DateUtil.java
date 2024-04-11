package rebelalliance.smartcash.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    /**
     * Gets the part of the day.
     *
     * @return The part of the day either "Morning", "Afternoon", or "Evening".
     */
    public static String getPartOfDay() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        if(hour >= 6 && hour < 12) {
            return "Morning";
        }else if(hour >= 12 && hour < 18) {
            return "Afternoon";
        }else {
            return "Evening";
        }
    }
}
