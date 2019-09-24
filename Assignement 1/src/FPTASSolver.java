import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FPTASSolver implements Solver {

    public static int getAproxximateValue(AuctionProblemInstance a, int[] assignment, int item, double epsilon) {
        int gamma = Tools.maxInArray(a.d);
        double step = gamma * epsilon / a.k;
        Set<IntermediateSolution> S = new HashSet<>();
        IntermediateSolution firstSolution = new IntermediateSolution(a.n, step);
        S.add(firstSolution);

        for (int j = 0; j < a.k; j++) {
            Set<IntermediateSolution> newS = new HashSet<>();
            for (IntermediateSolution is : S) {
                for (int i = 0; i < a.n; i++) {
                    IntermediateSolution newIS = new IntermediateSolution(is);
                    newIS.assignItem(a.b[i][j],j,i);
                    newS.add(newIS);
                }
            }
            S = newS;
        }


        return 0;
    }

    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a, double epsilon) {
        int[] assignment = new int[a.k];
        return new AuctionProblemInstance.Solution(getAproxximateValue(a, assignment, 0, epsilon), 0);
    }

    @Override
    public String getName() {
        return "bruteForce";
    }
}