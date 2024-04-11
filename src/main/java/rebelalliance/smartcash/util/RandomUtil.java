package rebelalliance.smartcash.util;

public class RandomUtil {
    public static String randomOf(String[] array) {
        return array[(int) (Math.random() * array.length)];
    }
}
