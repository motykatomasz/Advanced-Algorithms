public class FPTASSolver implements Solver {

    public static int getAproxximateValue(AuctionProblemInstance a, int[] assignment, int item) {
        return 0;
    }

    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a) {
        int[] assignment = new int[a.k];
        return new AuctionProblemInstance.Solution(getAproxximateValue(a, assignment, 0), 0);
    }

    @Override
    public String getName() {
        return "bruteForce";
    }
}