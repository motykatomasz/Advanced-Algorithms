public class IntermediateSolution {
    Tuple[] tuples;
    int totalRevenue = 0;

    public IntermediateSolution(int n) {
        tuples = new Tuple[n];
        for (int i = 0; i < n; i++) {
            tuples[i] = new Tuple(n);
        }
    }

    public IntermediateSolution(int n, int totalRevenue) {
        tuples = new Tuple[n];
        for (int i = 0; i < n; i++) {
            tuples[i] = new Tuple(n);
        }
        this.totalRevenue = totalRevenue;
    }
}
