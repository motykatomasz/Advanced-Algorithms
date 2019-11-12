import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class SpecialEvaluateTest {
    @Test
    public void testSpecialEvaluate() {
        try {
            AuctionProblemInstance au = AuctionProblemInstance.IO.read("instances/n_10_k_10_dmax_600_4.txt");
            int[] assignment = new int[au.k];
            Arrays.fill(assignment, -1);
            ExactSolver es = new ExactSolver();
            es.a = au;
            int revenuePre = es.evaluate(assignment);
            System.out.println(revenuePre);
            assignment[1] = 1;
            assignment[2] = 1;
            int revenuePost = es.evaluate(assignment);
            System.out.println(revenuePost);
            assertTrue(revenuePost == revenuePre + au.b[1][1] + au.b[1][2]);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
