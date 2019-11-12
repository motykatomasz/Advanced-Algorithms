public class PartialSolution {
    int revenue;

    int[] assignment;

    public PartialSolution() {

    }

    public PartialSolution(int revenue, int[] assignment) {
        this.revenue = revenue;
        this.assignment = assignment;
    }

    public int getRevenue() {
        return revenue;
    }

    public PartialSolution merge(PartialSolution other) {
        for (int i = 0; i < this.assignment.length; i++) {

            if (this.assignment[i] == -1 && other.assignment[i] != -1) {
                this.assignment[i] = other.assignment[i];
            }
        }
        this.revenue += other.revenue;
        return this;
    }
}
