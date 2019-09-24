import java.util.Objects;

/**
 * Data structure for representing tuple of (vi,ai)
 */
public class Tuple {

    /**
     * Bit-vector of items assigned to the bidder (ai)
     */
    int[] assignment;
    /**
     * Total benefit of the bidder (vi)
     */
    int value;

    /**
     * Index of the segment (Necessary for trimming)
     */
    int rangeIndex;

    /**
     * Size of the segment (Necessary for trimming)
     */
    double step;

    public Tuple(int n, double step) {
        assignment = new int[n];
        this.step = step;
    }

    /**
     * Copy constructor.
     * @param tuple
     */
    public Tuple(Tuple tuple) {
        assignment = new int[tuple.assignment.length];
        for (int i = 0; i < tuple.assignment.length; i++) {
            assignment[i] = tuple.assignment[i];
        }
        value = tuple.value;
        rangeIndex = tuple.rangeIndex;
        step = tuple.step;
    }

    public void assignRange() {
        rangeIndex = (int) Math.floor(this.value / step);
    }

    public void assignItem(int bid, int item) {
        assignment[item]=1;
        value+=bid;
        assignRange();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        Tuple tuple = (Tuple) o;
        return rangeIndex == tuple.rangeIndex;
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
