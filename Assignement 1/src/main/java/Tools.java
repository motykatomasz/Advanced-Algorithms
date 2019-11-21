import java.util.*;

public class Tools {

    static int maxInArray(int[] array) {
        return Arrays.stream(array).max().getAsInt();
    }
    public static List<List<Integer>> generateSubsets(List<Integer> set) {
        Set<List<Integer>> subsets = new HashSet<>();
        double counter = Math.pow(2, set.size());
        int size = set.size();
        if (size == 0) {
            size = 1;
        }
        for (int i = 0; i < counter; i++) {
            List<Integer> candidateSet = new ArrayList<>();

            String str = String.format("%" + size + "s", Integer.toBinaryString(i)).replaceAll(" ", "0");

            for (int j = 0; j < str.length(); j++) {
                char letter = str.charAt(j);
                if (letter == '1') {
                    candidateSet.add(set.get(j));
                }
            }
            subsets.add(candidateSet);
        }

        return new ArrayList<>(subsets);
    }
}
