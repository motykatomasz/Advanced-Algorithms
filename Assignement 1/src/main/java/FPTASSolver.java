import java.util.*;

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
                    IntermediateSolution newIs = new IntermediateSolution(is);
                    newIs.assignItem(a.b[i][j], j, i);
                    Integer newIsHash = newIs.hashTuples();

                    if (!map.containsKey(newIsHash))
                        map.put(newIsHash, newIs);
                    else {
                        IntermediateSolution maxElem = map.get(newIsHash);
                        if (!maxElem.compareTotalRevenue(newIs.getTotalRevenue())) {
                            map.put(newIsHash, newIs);
                        }
                    }
                }
            }
            S = new ArrayList<>(map.values());
        }

        //Measure execution time
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution time for n=" + a.n + ", k=" + a.k + ", d=" + gamma + ", eps=" + epsilon + " = " + elapsedTime + " miliseconds");

        // Get the solution with largest total benefit
        IntermediateSolution finalSolution =  S.stream().max(Comparator.comparing(IntermediateSolution::getTotalRevenue)).orElse(null);

        // Calculate item assignemnt for the obtained solution (for debugging purposes)
        Tuple[] finalTuples =  finalSolution.getTuples();
        for (int i=0; i < finalTuples.length; i++) {
            int[] ass = finalTuples[i].getAssignment();
            for (int j=0; j < ass.length; j++) {
                if (ass[j] == 1) {
                    assignment[j] = i;
                    System.out.println(i);
                }
            }
        }

        // Return total benefit oh the solution
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