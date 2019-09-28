import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(new FPTASSolver().solve(AuctionProblemInstance.IO.read(args[0]), Double.parseDouble(args[1])).value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
