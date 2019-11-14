import java.util.*;

public class ExactSolver implements Solver {

    AuctionProblemInstance a;
    Map<List<Integer>, List<PartialSolution>> structure;
    Map<List<Integer>, List<List<Integer>>> subsetsOfSubsets;

    private int getOptimalValue(AuctionProblemInstance au) {
        a = au;
        //Initialize structures
        List<Integer> initialSet = createInitialSet();
        structure = initializeStructure(initialSet);
        subsetsOfSubsets = initializeSubsetsOfSubsets();

        //Fill out the first row
        for (Map.Entry<List<Integer>, List<PartialSolution>> entry : structure.entrySet()) {
            int[] assignment = new int[a.k];
            Arrays.fill(assignment, -1);
            for (Integer i : entry.getKey()) {
                assignment[i] = 0;
            }
            PartialSolution ps = new PartialSolution(evaluate(assignment), assignment);
            entry.getValue().add(0, ps);
            structure.put(entry.getKey(), entry.getValue());
        }

        for (int i = 1; i < a.n; i++) {
            for (Map.Entry<List<Integer>, List<PartialSolution>> entry : structure.entrySet()) {

                List<PartialSolution> maxPartialSolution = new ArrayList<>();
                for (List<Integer> subset : subsetsOfSubsets.get(entry.getKey())) {
                    Set<Integer> complementSubset = new HashSet(entry.getKey());
                    complementSubset.removeAll(subset);

                    int[] assignment = new int[a.k];
                    Arrays.fill(assignment, -1);
                    for (Integer j : complementSubset) {
                        assignment[j] = i;
                    }
                    PartialSolution ps = new PartialSolution(evaluate(assignment), assignment);
                    maxPartialSolution.add(ps.merge(getSafePartial(subset, i - 1)));
                }
                //Get max form maxPartialSolution and add to structure
                entry.getValue().add(i, maxPartialSolution.stream().max(Comparator.comparing(PartialSolution::getRevenue)).get());
                structure.put(entry.getKey(), entry.getValue());
            }
        }
        return structure.get(initialSet).get(a.n - 1).getRevenue();
    }

    private List<Integer> createInitialSet() {
        List<Integer> initialSet = new ArrayList<>();
        for (int i = 0; i < a.k; i++) {
            initialSet.add(i);
        }
        return initialSet;
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

    private Map<List<Integer>, List<List<Integer>>> initializeSubsetsOfSubsets() {
        Map<List<Integer>, List<List<Integer>>> subsetsOfSubsets = new HashMap<>();
        for (List<Integer> subset : this.structure.keySet()) {
            subsetsOfSubsets.put(subset, generateSubsets(subset));
        }
        return subsetsOfSubsets;
    }

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

    public int evaluate(int[] assignment) {
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

    private PartialSolution getSafePartial(List<Integer> subset, Integer ind) {
        if (structure.containsKey(subset)) {
            return structure.get(subset).get(ind);
        }
        int[] assignment = new int[a.k];
        Arrays.fill(assignment, -1);
        return new PartialSolution(0, assignment);
    }

    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a, double epsilon) {
        long startTime = System.currentTimeMillis();
        AuctionProblemInstance.Solution solution = new AuctionProblemInstance.Solution(getOptimalValue(a), 0);
        //Measure execution time
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution time for our algorithm n=" + a.n + ", k=" + a.k + " = " + elapsedTime + " miliseconds");
        return solution;
    }

    @Override
    public String getName() {
        return "Exact";
    }
}
