import java.io.File;
import java.io.IOException;

public class TestCasesGenerator {
    public static void main(String[] args) {
        measure("./test_d/", 0.5, null);
    }

    static void measure(String pathToDirectory, double epsilon, double[] epsilons) {
        if (epsilons == null)
            measure(pathToDirectory, epsilon);
        else {
            for (double eps : epsilons)
                measure(pathToDirectory, eps);
        }
    }

    static void measure(String pathToDirectory, double epsilon) {
        File dir = new File(pathToDirectory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                try {
                    System.out.println(new FPTASSolver().solve(AuctionProblemInstance.IO.read(child), epsilon).value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void generateForK() {
        int n = 5;
        int d = 400;
        int[] k = {2, 5, 10, 15, 20, 30};

        for (int i = 0; i < k.length; i++) {
            String test_k = "Test_n_" + n + "_d_" + d + "_k_";
            try {
                AuctionProblemInstance.IO.save(AuctionProblemInstance.generate(n, k[i], d),
                        System.getProperty("user.dir") + "\\test_k\\" + test_k + k[i] + ".txt");
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }

    static void generateForN() {
        int[] n = {2, 4, 6, 8, 10, 12};
        int d = 400;
        int k = 10;

        for (int i = 0; i < n.length; i++) {
            String test_n = "Test_k_" + k + "_d_" + d + "_n_";
            try {
                AuctionProblemInstance.IO.save(AuctionProblemInstance.generate(n[i], k, d),
                        System.getProperty("user.dir") + "\\test_n\\" + test_n + n[i] + ".txt");
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }

    static void generateForD() {
        int n = 5;
        int[] d = {100, 200, 400, 800, 1000, 2000};
        int k = 10;

        for (int i = 0; i < d.length; i++) {
            String test_d = "Test_k_" + k + "_n_" + n + "_d_";
            try {
                AuctionProblemInstance.IO.save(AuctionProblemInstance.generate(n, k, d[i]),
                        System.getProperty("user.dir") + "\\test_d\\" + test_d + d[i] + ".txt");
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }

    static void generateForEps() {
        int n = 10;
        int d = 400;
        double[] eps = {0.1, 0.2, 0.3, 0.4, 0.5};
        int k = 10;

        for (int i = 0; i < eps.length; i++) {
            String test_eps = "Test_k_" + k + "_n_" + n + "_d_" + d + "_eps_";
            try {
                AuctionProblemInstance.IO.save(AuctionProblemInstance.generate(n, k, d),
                        System.getProperty("user.dir") + "\\test_eps\\" + test_eps + eps[i] + ".txt");
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }


}
