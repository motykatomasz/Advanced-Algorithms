import java.util.*;

public class ExactSolver implements Solver {

    public static List<List<Integer>> generateSubsets(List<Integer> set) {
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

    public static int evaluate(int[] assignment, AuctionProblemInstance a) {
        int[] values = new int[a.n];
        for (int i = 0; i < assignment.length; i++) {
            if (assignment[i] != -1) {
                values[assignment[i]] += a.b[assignment[i]][i];
            }
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = Math.min(values[i], a.d[i]);
        }
        return Arrays.stream(values).sum();
    }

    private int getOptimalValue(AuctionProblemInstance a) {
        //Initialize structures
        List<Integer> initialSet = new ArrayList<>();
        for (int i = 0; i < a.k; i++) {
            initialSet.add(i);
        }
        Map<List<Integer>, List<PartialSolution>> structure = initializeStructure(initialSet);

        //Fill out the first row
        for (Map.Entry<List<Integer>, List<PartialSolution>> entry : structure.entrySet()) {
            int[] assignment = new int[a.k];
            Arrays.fill(assignment, -1);
            for (Integer i : entry.getKey()) {
                assignment[i] = 0;
            }
            PartialSolution ps = new PartialSolution(evaluate(assignment, a), assignment);
            entry.getValue().add(0, ps);
            structure.put(entry.getKey(), entry.getValue());
        }

        for (int i = 1; i < a.n; i++) {
            for (Map.Entry<List<Integer>, List<PartialSolution>> entry : structure.entrySet()) {

                List<PartialSolution> maxPartialSolution = new ArrayList<>();
                for (List<Integer> subset : generateSubsets(entry.getKey())) {
                    Set<Integer> complementSubset = new HashSet(entry.getKey());
                    complementSubset.removeAll(subset);

                    int[] assignment = new int[a.k];
                    Arrays.fill(assignment, -1);
                    for (Integer j : complementSubset) {
                        assignment[j] = i;
                    }
                    PartialSolution ps = new PartialSolution(evaluate(assignment, a), assignment);
                    maxPartialSolution.add(ps.merge(entry.getValue().get(i - 1)));
                }
                //Get max form maxPartialSolution and add to structure
                entry.getValue().add(i, maxPartialSolution.stream().max(Comparator.comparing(PartialSolution::getRevenue)).get());
                structure.put(entry.getKey(), entry.getValue());
            }
        }

        return structure.get(initialSet).get(a.n - 1).getRevenue();
    }

    private Map<List<Integer>, List<PartialSolution>> initializeStructure(List<Integer> initialSet) {
        Map<List<Integer>, List<PartialSolution>> structure = new HashMap<>();

        for (List<Integer> subset : generateSubsets(initialSet)) {
            if (subset.size() != 0) {
                structure.put(subset, new ArrayList<>());
            }
        }

        return structure;
    }

    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a, double epsilon) {
        return new AuctionProblemInstance.Solution(getOptimalValue(a), 0);
    }

    @Override
    public String getName() {
        return "Exact";
    }
}
