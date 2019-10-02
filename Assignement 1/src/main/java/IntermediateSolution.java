import java.util.Arrays;

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

    int getTotalRevenue() {
        return totalRevenue;
    }

    void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    Tuple[] getTuples() {
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
    IntermediateSolution(int n, int k, double step, int[] d) {
        tuples = new Tuple[n];
        for (int i = 0; i < n; i++) {
            tuples[i] = new Tuple(k, step, d[i]);
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
     * Assigns item to bidder
     * @param bid Amount that the bidder bids
     * @param item Index of item
     * @param bidder Index of bidder
     */
    void assignItem(int bid, int item, int bidder) {
        tuples[bidder].assignItem(bid,item);
        calculateTotalRevenue();
    }

    /**
     * Calculates total revenue
     */
    void calculateTotalRevenue() {
        this.totalRevenue = 0;
        for (int i = 0; i < tuples.length; i++) {
            this.totalRevenue += tuples[i].getValue();
        }
    }

    /**
     * Compares revenue of 2 intermediate solutions
     * @param totalRevenue Total revenue of other intermediate solution
     */
    boolean compareTotalRevenue(int totalRevenue) {
        return this.totalRevenue > totalRevenue;
    }

    /**
     * Hashes the tuples, based on the tuples values (see hashCode() for Tuple). Used to calculate the key for HashMap.
     * @return hash of tuples
     */
    int hashTuples() {
        return Arrays.hashCode(getTuples());
    }
}
