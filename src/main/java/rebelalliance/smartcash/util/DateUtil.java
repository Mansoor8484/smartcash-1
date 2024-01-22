package rebelalliance.smartcash.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(Date date) {
        return FORMAT.format(date);
    }
}
