package rebelalliance.smartcash.util;

public class ArrayUtil {
    public static <T> T[] reverse(T[] array) {
        T[] reversed = array.clone();
        for(int i = 0; i < reversed.length / 2; i++) {
            T temp = reversed[i];
            reversed[i] = reversed[reversed.length - i - 1];
            reversed[reversed.length - i - 1] = temp;
        }
        return reversed;
    }
}
