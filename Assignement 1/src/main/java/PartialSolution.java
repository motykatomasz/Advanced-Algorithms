import java.util.*;

public class PartialSolution {
    int revenue;

    int[] assignment;

    List<Subset> subsetList;
    List<Integer> originalSet;
    int originalSetId;

    public PartialSolution() {
    }

    public PartialSolution(int revenue, int[] assignment, List<Integer> set, int originalSetId, List<Subset> subsets) {
        this.revenue = revenue;
        this.assignment = assignment;
        this.originalSet = set;
        this.originalSetId = originalSetId;
        this.subsetList = subsets;
    }


    public int getRevenue() {
        return revenue;
    }
}
