import java.util.Objects;

/**
 * Data structure for representing tuple of (vi,ai)
 */
public class Tuple {

    /**
     * Bit-vector of items assigned to the bidder (ai)
     */
    private int[] assignment;

    /**
     * Total benefit of the bidder (vi)
     */
    private int value;

    /**
     * Index of the segment (Necessary for trimming)
     */
    private int rangeIndex;

    /**
     * Size of the segment (Necessary for trimming)
     */
    private double step;

    /**
     * Budget limit for a bidder.
     */
    private int maxValue;

    int[] getAssignment() {
        return assignment;
    }

    void setAssignment(int[] assignment) {
        this.assignment = assignment;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }

    int getRangeIndex() {
        return rangeIndex;
    }

    Tuple(int n, double step, int maxValue) {
        assignment = new int[n];
        this.step = step;
        this.maxValue = maxValue;
    }

    /**
     * Copy constructor.
     *
     * @param tuple Tuple to copy from
     */
    Tuple(Tuple tuple) {
        assignment = new int[tuple.assignment.length];
        System.arraycopy(tuple.assignment, 0, this.assignment, 0, tuple.assignment.length - 1);
        value = tuple.value;
        rangeIndex = tuple.rangeIndex;
        step = tuple.step;
        maxValue = tuple.maxValue;
    }

    /**
     * Assigns item to a bidder
     * @param bid amount that the bidder bids for the item
     * @param item index of the item
     */
    void assignItem(int bid, int item) {
        assignment[item] = 1;
        value = Math.min(maxValue, value + bid);
        assignRange();
    }

    /**
     * Calculate index of the interval, the tuple fits in.
     */
    void assignRange() {
        rangeIndex = (int) Math.floor(value / step);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        Tuple tuple = (Tuple) o;
        return rangeIndex == tuple.getRangeIndex();
    }

    /**
     * Used for comparing Tuples based on the intervals they are in
     * @return Hash of the index of the interval
     */
    @Override
    public int hashCode() {
        return Objects.hash(rangeIndex);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
