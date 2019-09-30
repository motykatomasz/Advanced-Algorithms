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

    static List<IntermediateSolution> trimWithIterators(List<IntermediateSolution> intermediateSolutions) {
        //TODO  Sort intermediate solutions descending by total revenue

        intermediateSolutions.sort(Comparator.comparing(IntermediateSolution::getTotalRevenue));
        ListIterator<IntermediateSolution> iteratorI = intermediateSolutions.listIterator();

        while(iteratorI.hasNext()) {
            IntermediateSolution elemI = iteratorI.next();
            ListIterator<IntermediateSolution> iteratorJ = intermediateSolutions.listIterator(iteratorI.nextIndex());
            while (iteratorJ.hasNext()) {
                IntermediateSolution elemJ = iteratorJ.next();
                if (Arrays.equals(elemI.getTuples(), elemJ.getTuples())) {
                    if (elemI.compareTotalRevenue(elemJ.getTotalRevenue())) {
                        iteratorJ.remove();
                    } else {
                        iteratorI.remove();
                        break;
                    }
                }
            }
        }

        return intermediateSolutions;
    }
}
