import java.util.*;

public class PartialSolution {
    int revenue;

    int[] assignment;

    List<Subset> subsetList;
    List<Integer> originalSet;
    int originalSetId;

    public PartialSolution() {
        List<Subset> subsetList = new ArrayList<>();
        subsetList.add(new Subset());
    }

    public PartialSolution(List<Integer> originalSet, int i) {
        this.originalSet = originalSet;
        this.originalSetId = i;
    }


    public PartialSolution(int revenue, int[] assignment, Map<List<Integer>, Integer> setToIdDict, List<Integer> set) {
        this.revenue = revenue;
        this.assignment = assignment;
        this.originalSet = set;
        originalSetId = setToIdDict.get(set);
        List<List<Integer>> subsets = generateSubsets(set);
        subsetList = new ArrayList<>();
        for (List<Integer> subsetElem : subsets) {
            subsetList.add(new Subset(setToIdDict.get(subsetElem), subsetElem));
        }
    }

    public PartialSolution(int revenue, int[] assignment, List<Integer> set, int originalSetId, List<Subset> subsets) {
        this.revenue = revenue;
        this.assignment = assignment;
        this.originalSet = set;
        this.originalSetId = originalSetId;

        subsetList = subsets;
    }

    public List<List<Integer>> generateSubsets(List<Integer> set) {
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

    public int getRevenue() {
        return revenue;
    }
}
