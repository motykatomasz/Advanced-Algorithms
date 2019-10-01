import java.util.*;
import java.util.stream.Collectors;

public class Tools {
    public static int powi(int base, int exp) {
        return (int) Math.pow(base, exp);
    }

    static int maxInArray(int[] array) {
        return Arrays.stream(array).max().getAsInt();
    }

}
