import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class ListToMapConversion {

    @Test
    public void testConvertingListToHashMap() {
        int[] d = new int[] {10,20};
        double step = 6;
        int n = 2;
        int k = 3;
        IntermediateSolution is1 = new IntermediateSolution(n, k, step, d);
        IntermediateSolution is2 = new IntermediateSolution(n, k, step, d);
        IntermediateSolution is3 = new IntermediateSolution(n, k, step, d);
        IntermediateSolution is4 = new IntermediateSolution(n, k, step, d);

        Tuple t11 = new Tuple(n, step, 10);
        Tuple t12 = new Tuple(n, step, 20);
        Tuple t21 = new Tuple(n, step, 10);
        Tuple t22 = new Tuple(n, step, 20);
        Tuple t31 = new Tuple(n, step, 10);
        Tuple t32 = new Tuple(n, step, 20);
        Tuple t41 = new Tuple(n, step, 10);
        Tuple t42 = new Tuple(n, step, 20);

        t11.setAssignment(new int[] {1,1,0});
        t11.setValue(7);
        t11.assignRange();

        t12.setAssignment(new int[] {0,0,0});
        t12.setValue(0);
        t12.assignRange();

        t21.setAssignment(new int[] {1,0,0});
        t21.setValue(2);
        t21.assignRange();

        t22.setAssignment(new int[] {0,1,0});
        t22.setValue(3);
        t22.assignRange();

        t31.setAssignment(new int[] {0,1,0});
        t31.setValue(5);
        t31.assignRange();

        t32.setAssignment(new int[] {1,0,0});
        t32.setValue(5);
        t32.assignRange();

        t41.setAssignment(new int[] {0,0,0});
        t41.setValue(0);
        t41.assignRange();

        t42.setAssignment(new int[] {1,1,0});
        t42.setValue(8);
        t42.assignRange();

        is1.setTuples(new Tuple[] {t11,t12});
        is1.setTotalRevenue(7);

        is2.setTuples(new Tuple[] {t21,t22});
        is2.setTotalRevenue(5);

        is3.setTuples(new Tuple[] {t31,t32});
        is3.setTotalRevenue(10);

        is4.setTuples(new Tuple[] {t41,t42});
        is4.setTotalRevenue(8);

        List<IntermediateSolution> intermediateSolutions = new ArrayList<>(Arrays.asList(is1, is2, is3, is4));

        Map<Integer, List<IntermediateSolution>> mapped = intermediateSolutions.stream().collect(Collectors.groupingBy(IntermediateSolution::hashTuples));

        assertTrue(mapped.size() == 3);
    }
}
