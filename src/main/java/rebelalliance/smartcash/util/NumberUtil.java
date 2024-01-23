package rebelalliance.smartcash.util;

import java.text.DecimalFormat;

/**
 * Utility class for numbers.
 */
public class NumberUtil {
    private static final DecimalFormat DF = new DecimalFormat("$#,##0.00;-$#");

    /**
     * Format a number as a currency.
     *
     * @param number The number to format
     *
     * @return The formatted number with a dollar sign and two decimal places.
     */
    public static String formatAsAmount(double number) {
        return DF.format(number);
    }
}
