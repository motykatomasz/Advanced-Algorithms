import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FPTASSolver implements Solver {

    public static int getAproxximateValue(AuctionProblemInstance a, int[] assignment, int item, double epsilon) {

        long startTime = System.currentTimeMillis();

        // 1. Calculate gamma
        int gamma = Tools.maxInArray(a.d);

        // 2. Calculate segment range
        double step = gamma * epsilon / a.k;

        // 3. Initialize first solution
        List<IntermediateSolution> S = new ArrayList<>();
        IntermediateSolution firstSolution = new IntermediateSolution(a.n, a.k, step);
        S.add(firstSolution);

        // 4. Magic
        for (int j = 0; j < a.k; j++) {
            List<IntermediateSolution> newS = new ArrayList<>();
            for (IntermediateSolution is : S) {
                for (int i = 0; i < a.n; i++) {
                    IntermediateSolution newIS = new IntermediateSolution(is);
                    newIS.assignItem(a.b[i][j], j, i);
                    newS.add(newIS);
                }
            }
            // 5. More magic
            System.out.println("S size for iteration " + j + " before trimming = " + newS.size());
            S = Tools.trim(newS);
            System.out.println("S size for iteration " + j + " after trimming = " + S.size());
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution time = " + elapsedTime + " miliseconds");

        return S.stream().max(Comparator.comparing(IntermediateSolution::getTotalRevenue)).orElse(null).getTotalRevenue();
    }

    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a, double epsilon) {
        int[] assignment = new int[a.k];
        return new AuctionProblemInstance.Solution(getAproxximateValue(a, assignment, 0, epsilon), epsilon);
    }

    @Override
    public String getName() {
        return "FPTAS";
    }
}