import java.util.Arrays;

public class Tools {
    public static int powi(int base, int exp) {
        return (int) Math.pow(base, exp);
    }

    public static  int maxInArray(int[] array) {
        return Arrays.stream(array).max().getAsInt();
    }
}
