import java.util.Arrays;

import org.junit.*;

import static org.junit.Assert.*;

public class TupleComparingTest {

    @Test
    public void testComparingTupleArrays() {
        double step = 6;
        int n = 2;
        int k = 3;

        Tuple t11 = new Tuple(n, step);
        Tuple t12 = new Tuple(n, step);
        Tuple t21 = new Tuple(n, step);
        Tuple t22 = new Tuple(n, step);
        Tuple t31 = new Tuple(n, step);
        Tuple t32 = new Tuple(n, step);

        t11.setAssignment(new int[] {1,1,0});
        t11.setValue(3);
        t11.assignRange();

        t12.setAssignment(new int[] {0,0,0});
        t12.setValue(3);
        t12.assignRange();

        t21.setAssignment(new int[] {1,0,0});
        t21.setValue(5);
        t21.assignRange();

        t22.setAssignment(new int[] {0,1,0});
        t22.setValue(2);
        t22.assignRange();

        t31.setAssignment(new int[] {0,1,0});
        t31.setValue(7);
        t31.assignRange();

        t32.setAssignment(new int[] {1,0,0});
        t32.setValue(4);
        t32.assignRange();

        Tuple[] tuples1 = new Tuple[] {t11, t12};
        Tuple[] tuples2 = new Tuple[] {t21, t22};
        Tuple[] tuples3 = new Tuple[] {t31, t32};

        assertTrue(t11.equals(t21));
        assertTrue(t12.equals(t22));
        assertFalse(t11.equals(t31));

        assertTrue(Arrays.equals(tuples1, tuples2));
        assertFalse(Arrays.equals(tuples1, tuples3));
        assertFalse(Arrays.equals(tuples2, tuples3));





    }
}
