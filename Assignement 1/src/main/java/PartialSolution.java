import java.util.*;

public class PartialSolution {
    int revenue;

    int[] assignment;

    List<Subset> subsetList;
    List<Integer> originalSet;

    public PartialSolution() {
        List<Subset> subsetList = new ArrayList<>();
        subsetList.add(new Subset());
    }

    public PartialSolution(int revenue, int[] assignment) {
        this.revenue = revenue;
        this.assignment = assignment;
        List<Subset> subsetList = new ArrayList<>();
    }

    public PartialSolution(int revenue, int[] assignment, Map<List<Integer>, Integer> setToIdDict, List<Integer> set) {
        this.revenue = revenue;
        this.assignment = assignment;
        this.originalSet = set;
        List<List<Integer>> subsets = generateSubsets(set);
        subsetList = new ArrayList<>();
        for (List<Integer> subsetElem : subsets) {
            subsetList.add(new Subset(setToIdDict.get(subsetElem), subsetElem));
        }
    }

    public List<List<Integer>> generateSubsets(List<Integer> set) {
        Set<List<Integer>> subsets = new HashSet<>();
        double counter = Math.pow(2, set.size());

        for (int i = 0; i < counter; i++) {
            List<Integer> candidateSet = new ArrayList<>();

            String str = String.format("%" + set.size() + "s", Integer.toBinaryString(i)).replaceAll(" ", "0");

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

    public int getRevenue() {
        return revenue;
    }
}
