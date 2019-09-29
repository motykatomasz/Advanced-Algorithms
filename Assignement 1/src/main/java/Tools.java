import java.util.*;

public class Tools {
    public static int powi(int base, int exp) {
        return (int) Math.pow(base, exp);
    }

    static int maxInArray(int[] array) {
        return Arrays.stream(array).max().getAsInt();
    }

    static List<IntermediateSolution> trim(List<IntermediateSolution> intermediateSolutions) {

        for (int i = 0; i < intermediateSolutions.size(); i++) {
            IntermediateSolution elemI = intermediateSolutions.get(i);
            for (int j = i + 1; j < intermediateSolutions.size(); j++) {
                IntermediateSolution elemJ = intermediateSolutions.get(j);
                if (Arrays.equals(elemI.getTuples(), elemJ.getTuples())) {
                    if (elemI.compareTotalRevenue(elemJ.getTotalRevenue())) {
                        intermediateSolutions.remove(elemJ);
                        j--;
                    } else {
                        intermediateSolutions.remove(elemI);
                        i--;
                        break;
                    }
                }
            }
        }
        return intermediateSolutions;
    }
}
