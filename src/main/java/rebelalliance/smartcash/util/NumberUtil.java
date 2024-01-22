package rebelalliance.smartcash.util;

import java.text.DecimalFormat;

public class NumberUtil {
    private static final DecimalFormat DF = new DecimalFormat("#0.00");

    public static String format(double number) {
        return DF.format(number);
    }
}
