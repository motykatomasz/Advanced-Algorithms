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

    public int[] getAssignment() {
        return assignment;
    }

    void setAssignment(int[] assignment) {
        this.assignment = assignment;
    }

    public int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }

    public int getRangeIndex() {
        return rangeIndex;
    }

    public void setRangeIndex(int rangeIndex) {
        this.rangeIndex = rangeIndex;
    }

    Tuple(int n, double step) {
        assignment = new int[n];
        this.step = step;
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
    }

    void assignRange() {
        rangeIndex = (int) Math.floor(value / step);
    }

    void assignItem(int bid, int item) {
        assignment[item] = 1;
        value += bid;
        assignRange();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        Tuple tuple = (Tuple) o;
        return rangeIndex == tuple.getRangeIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(rangeIndex);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
