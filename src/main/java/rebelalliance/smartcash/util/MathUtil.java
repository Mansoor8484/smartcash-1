package rebelalliance.smartcash.util;

/**
 * Utility class for math.
 */
public class MathUtil {
    /**
     * Rounds a double to the specified number of decimal places. Prevents floating point errors.
     *
     * @param value  The value to round
     * @param places The number of decimal places to round to
     *
     * @return The rounded value.
     */
    public static double round(double value, int places) {
        if(places < 0) {
            throw new IllegalArgumentException();
        }

        double factor = Math.pow(10, places);
        value = value * factor;
        long rounded = Math.round(value);

        return rounded / factor;
    }
}
