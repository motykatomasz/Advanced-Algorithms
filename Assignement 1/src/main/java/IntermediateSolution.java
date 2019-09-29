
/**
 * Data structure for representing intermediate solution (v1,a1,...vn,an,t)
 */
public class IntermediateSolution {
    /**
     * List of simpler tuples (v1,a1,...vn,an)
     */
    private Tuple[] tuples;

    /**
     * Total revenue of the intermediate solution (t)
     */
    private int totalRevenue;

    /**
     * Getter
     * @return
     */
    int getTotalRevenue() {
        return totalRevenue;
    }

    void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Tuple[] getTuples() {
        return tuples;
    }

    void setTuples(Tuple[] tuples) {
        this.tuples = tuples;
    }

    /**
     * Constructor
     * @param n - Number of bidders
     * @param k - Number of items
     * @param step - Size of segment
     */
    IntermediateSolution(int n, int k, double step) {
        tuples = new Tuple[n];
        for (int i = 0; i < n; i++) {
            tuples[i] = new Tuple(k, step);
        }
    }

    /**
     * Copy constructor
     * @param is - Other IntermediateSolution
     */
    IntermediateSolution(IntermediateSolution is) {
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
    void assignItem(int bid, int item, int bidder) {
        tuples[bidder].assignItem(bid,item);
        totalRevenue += bid;
    }

    boolean compareTotalRevenue(int totalRevenue) {
        return this.totalRevenue > totalRevenue;
    }
}
