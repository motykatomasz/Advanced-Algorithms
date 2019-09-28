public interface Solver {
    AuctionProblemInstance.Solution solve(AuctionProblemInstance a, double epsilon);

    String getName();
}
