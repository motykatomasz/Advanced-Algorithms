public class Tuple {
    int[] assignment;
    int value = 0;

    public Tuple(int n) {
        assignment = new int[n];
    }

    public Tuple(int n, int value) {
        assignment = new int[n];
        this.value = value;
    }
}
