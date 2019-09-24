import java.util.Arrays;

public class IntermediateSolution {
    Tuple[] tuples;
    int totalRevenue = 0;

    public IntermediateSolution(int n, double step) {
        tuples = new Tuple[n];
        for (int i = 0; i < n; i++) {
            tuples[i] = new Tuple(n, step);
        }
    }

    public IntermediateSolution(IntermediateSolution is) {
        tuples = new Tuple[is.tuples.length];
        for (int i = 0; i < is.tuples.length; i++) {
            tuples[i] = new Tuple(is.tuples[i]);
        }
        totalRevenue = is.totalRevenue;
    }

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
