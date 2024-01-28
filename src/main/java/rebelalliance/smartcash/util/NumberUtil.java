package rebelalliance.smartcash.util;

import java.text.DecimalFormat;

/**
 * Utility class for numbers.
 */
public class NumberUtil {
    private static final DecimalFormat DF = new DecimalFormat("$#,##0.00;-$#");
    private static final String AMOUNT_REGEX = "^-?\\d*(.\\d{1,2})?$";

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

    public static boolean stringIsAmount(String amount) {
        return amount.matches(AMOUNT_REGEX);
    }
}
