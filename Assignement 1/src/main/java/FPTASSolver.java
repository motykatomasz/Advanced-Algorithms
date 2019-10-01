import java.util.*;
import java.util.stream.Collectors;

public class FPTASSolver implements Solver {

    public static int getAproxximateValue(AuctionProblemInstance a, int[] assignment, int item, double epsilon) {

        long startTime = System.currentTimeMillis();

        // 1. Calculate gamma
        int gamma = Tools.maxInArray(a.d);

        // 2. Calculate segment range
        double step = gamma * epsilon / a.k;

        // 3. Initialize first solution
        List<IntermediateSolution> S = new ArrayList<>();
        IntermediateSolution firstSolution = new IntermediateSolution(a.n, a.k, step, a.d);
        S.add(firstSolution);

        // 4. Magic
        for (int j = 0; j < a.k; j++) {
            Map<Integer, IntermediateSolution> map = new HashMap<>();
            for (IntermediateSolution is : S) {
                for (int i = 0; i < a.n; i++) {
                    IntermediateSolution newIS = new IntermediateSolution(is);
                    newIS.assignItem(a.b[i][j], j, i);

                    if (!map.containsKey(newIS.hashTuples()))
                        map.put(newIS.hashTuples(), newIS);
                    else {
                        IntermediateSolution maxElem = map.get(newIS.hashTuples());
                        if (!maxElem.compareTotalRevenue(newIS.getTotalRevenue())) {
                            map.put(newIS.hashTuples(), newIS);
                        }
                    }
                }
            }
            S = new ArrayList<>(map.values());
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution time = " + elapsedTime + " miliseconds");

        IntermediateSolution finalSolution =  S.stream().max(Comparator.comparing(IntermediateSolution::getTotalRevenue)).orElse(null);

        Tuple[] finalTuples =  finalSolution.getTuples();

        for (int i=0; i < finalTuples.length; i++) {
            int[] ass = finalTuples[i].getAssignment();
            for (int j=0; j < ass.length; j++) {
                if (ass[j] == 1) {
                    assignment[j] = i;
                    // System.out.println(i);
                }
            }
        }

        return finalSolution.getTotalRevenue();
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