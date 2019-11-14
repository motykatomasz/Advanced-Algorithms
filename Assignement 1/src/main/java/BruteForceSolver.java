public class BruteForceSolver implements Solver {

    public static int getOptimalValue(AuctionProblemInstance a, int[] assignment, int item) {
        int max = -1;
        for (int i = 0; i < a.n; i++) {
            assignment[item] = i;
            // if all items assigned, calculate the value, else assign one item and call recursively
            if (item != a.k - 1) {
                max = Math.max(max, getOptimalValue(a, assignment, item + 1));
            } else {
                max = Math.max(max, a.evaluate(assignment));
            }
        }
        return max;
    }

    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a, double epsilon) {
        int[] assignment = new int[a.k];
        long startTime = System.currentTimeMillis();
        AuctionProblemInstance.Solution solution = new AuctionProblemInstance.Solution(getOptimalValue(a, assignment, 0), 0);
        //Measure execution time
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution time for brute force algorithm n=" + a.n + ", k=" + a.k + " = " + elapsedTime + " miliseconds");
        return solution;
    }

    @Override
    public String getName() {
        return "bruteForce";
    }
}
