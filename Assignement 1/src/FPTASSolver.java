import java.util.ArrayList;
import java.util.List;

public class FPTASSolver implements Solver {

    public static int getAproxximateValue(AuctionProblemInstance a, int[] assignment, int item, double epsilon) {
        int gamma = Tools.maxInArray(a.d);
        double step = gamma * epsilon / a.k;
        List<IntermediateSolution> S = new ArrayList<>();
        IntermediateSolution firstSolution = new IntermediateSolution(a.n, step);
        S.add(firstSolution);

        for (int j = 0; j < a.k; j++) {
            List<IntermediateSolution> newS = new ArrayList<>();
            for (IntermediateSolution is : S) {
                for (int i = 0; i < a.n; i++) {
                    IntermediateSolution newIS = new IntermediateSolution(is);
                    newIS.assignItem(a.b[i][j], j, i);
                    newS.add(newIS);
                }
            }
            newS = trim(newS);
            S = newS;
        }


        return 0;
    }

    public static List<IntermediateSolution> trim(List<IntermediateSolution> intermediateSolutions) {
        //TODO: unsure if it will be a deep copy
        List<IntermediateSolution> copy = intermediateSolutions;

        for (int i = 0; i < intermediateSolutions.size(); i++) {
            IntermediateSolution elemI = intermediateSolutions.get(i);
            for (int j = i; j < intermediateSolutions.size(); j++) {
                IntermediateSolution elemJ = intermediateSolutions.get(j);

                if (elemI.isInRange(elemJ)) {
                    if (elemI.compareTotalRevenue(elemJ)) {
                        copy.remove(elemJ);
                    } else {
                        copy.remove(elemI);
                    }
                }
            }
        }
        return copy;
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