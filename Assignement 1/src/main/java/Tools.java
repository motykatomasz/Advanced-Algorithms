import java.util.*;
import java.util.stream.Collectors;

public class Tools {
    public static int powi(int base, int exp) {
        return (int) Math.pow(base, exp);
    }

    static int maxInArray(int[] array) {
        return Arrays.stream(array).max().getAsInt();
    }

    static List<IntermediateSolution> trimWithMap(List<IntermediateSolution> intermediateSolutions) {
        Map<Integer, List<IntermediateSolution>> mapped = intermediateSolutions.stream().collect(Collectors.groupingBy(IntermediateSolution::hashTuples));
        List<IntermediateSolution> newIntermediateSolutions = new ArrayList<>();
        for (Map.Entry<Integer, List<IntermediateSolution>> entry : mapped.entrySet()) {
            List<IntermediateSolution> is = entry.getValue();
            trim(is);
            newIntermediateSolutions.addAll(is);
        }
        return newIntermediateSolutions;
    }

    private static void trim(List<IntermediateSolution> intermediateSolutions) {
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
    }

    static List<IntermediateSolution> trimWithIterators(List<IntermediateSolution> intermediateSolutions) {
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
