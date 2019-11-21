import java.util.*;

/**
 * Class representing solution for subproblem in our lookup table.
 */
public class PartialSolution {

    /**
     * Revenue of solution.
     */
    int revenue;

    /**
     * Assignment of items in solution.
     */
    int[] assignment;

    /**
     * Set of items in subproblem.
     */
    Subset originalSet;

    /**
     * List of all possible subsets of items in subproblem.
     */
    List<Subset> subsetList;


    public PartialSolution() {
    }

    public PartialSolution(int revenue, int[] assignment, Subset subset, List<Subset> subsets) {
        this.revenue = revenue;
        this.assignment = assignment;
        this.originalSet = subset;
        this.subsetList = subsets;
    }

    public int getRevenue() {
        return revenue;
    }
}
