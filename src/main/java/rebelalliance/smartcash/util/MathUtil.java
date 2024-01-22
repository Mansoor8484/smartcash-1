package rebelalliance.smartcash.util;

public class MathUtil {
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
