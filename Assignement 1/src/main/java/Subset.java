import java.util.List;

/**
 * Class representing subset of items with its index in our lookup table.
 */
public class Subset {

    /**
     * Index in the lookup table.
     */
    public int subsetId;

    /**
     * Subset of items.
     */
    public List<Integer> subset;

    public Subset(int subsetId, List<Integer> subset) {
        this.subsetId = subsetId;
        this.subset = subset;
    }
}
