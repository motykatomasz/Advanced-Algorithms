import java.util.*;

public class Tools {

    static int maxInArray(int[] array) {
        return Arrays.stream(array).max().getAsInt();
    }

}
