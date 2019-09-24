import java.util.Objects;

public class Tuple {
    int[] assignment;
    int value;
    int rangeIndex;
    double step;

    public Tuple(int n, double step) {
        assignment = new int[n];
        this.step = step;
    }

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
