import java.util.Arrays;

/**
 * Data structure for representing intermediate solution (v1,a1,...vn,an,t)
 */
public class IntermediateSolution {
    /**
     * List of simpler tuples (v1,a1,...vn,an)
     */
    Tuple[] tuples;

    /**
     * Total revenue of the intermediate solution (t)
     */
    int totalRevenue = 0;

    /**
     * Getter
     * @return
     */
    public int getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * Constructor
     * @param n - Number of bidders
     * @param step - Size of segment
     */
    public IntermediateSolution(int n, double step) {
        tuples = new Tuple[n];
        for (int i = 0; i < n; i++) {
            tuples[i] = new Tuple(n, step);
        }
    }

    /**
     * Copy constructor
     * @param is - Other IntermediateSolution
     */
    public IntermediateSolution(IntermediateSolution is) {
        tuples = new Tuple[is.tuples.length];
        for (int i = 0; i < is.tuples.length; i++) {
            tuples[i] = new Tuple(is.tuples[i]);
        }
        totalRevenue = is.totalRevenue;
    }

    /**
     * Method for assigning item to bidder
     * @param bid Amount that the bidder bids
     * @param item Index of item
     * @param bidder Index of bidder
     */
    public void assignItem(int bid, int item, int bidder) {
        Tuple copy = tuples[bidder];
        copy.assignItem(bid, item);
        tuples[bidder] = copy;
        totalRevenue = bid;
    }

    public boolean isInRange(IntermediateSolution is) {
        for (int i = 0; i < is.tuples.length; i++) {
            Tuple elemI = is.tuples[i];
            if (is.tuples[i].rangeIndex == tuples[i].rangeIndex) {
                return true;
            }
        }
        return false;
    }

    public boolean compareTotalRevenue(IntermediateSolution is) {
        return this.totalRevenue > is.totalRevenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntermediateSolution)) return false;
        IntermediateSolution that = (IntermediateSolution) o;
        //TODO: Check if this works
        return Arrays.equals(tuples, that.tuples);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tuples);
    }
}
