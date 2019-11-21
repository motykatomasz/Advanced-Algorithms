import java.util.*;

public class ExactSolver implements Solver {

    AuctionProblemInstance a;
    Map<List<Integer>, PartialSolution> structure;
    Map<List<Integer>, List<List<Integer>>> subsetsOfSubsets;

    private int getOptimalValue(AuctionProblemInstance au) {
        a = au;
        //Initialize structures
        List<Integer> initialSet = createInitialSet();
        structure = initializeStructure(initialSet);
        subsetsOfSubsets = initializeSubsetsOfSubsets();

        for (Map.Entry<List<Integer>, PartialSolution> entry : structure.entrySet()) {
            int[] assignment = new int[a.k];
            Arrays.fill(assignment, -1);
            for (Integer i : entry.getKey()) {
                assignment[i] = 0;
            }
            PartialSolution ps = new PartialSolution(evaluate(assignment), assignment);
            structure.put(entry.getKey(), ps);
        }

        for (int i = 1; i < a.n; i++) {
            Map<List<Integer>, PartialSolution> newStructure = new HashMap<>();
            for (Map.Entry<List<Integer>, PartialSolution> entry : structure.entrySet()) {

                PartialSolution maxPartialSolution = new PartialSolution();
                for (List<Integer> subset : subsetsOfSubsets.get(entry.getKey())) {
                    Set<Integer> complementSubset = new HashSet(entry.getKey());
                    complementSubset.removeAll(subset);
                    PartialSolution previousPartialSolution = getSafePartial(subset, i - 1);

                    int[] assignment = Arrays.copyOf(previousPartialSolution.assignment, a.k);
                    for (Integer j : complementSubset) {
                        assignment[j] = i;
                    }
                    PartialSolution ps = new PartialSolution(evaluate(assignment), assignment);
                    if (maxPartialSolution.getRevenue() < ps.getRevenue())
                        maxPartialSolution = ps;
                }
                //Get max form maxPartialSolution and add to structure
                newStructure.put(entry.getKey(), maxPartialSolution);
            }
            structure = newStructure;
        }
        return structure.get(initialSet).getRevenue();
    }

    private List<Integer> createInitialSet() {
        List<Integer> initialSet = new ArrayList<>();
        for (int i = 0; i < a.k; i++) {
            initialSet.add(i);
        }
        return initialSet;
    }

    private Map<List<Integer>, PartialSolution> initializeStructure(List<Integer> initialSet) {
        Map<List<Integer>, PartialSolution> structure = new HashMap<>();

        for (List<Integer> subset : generateSubsets(initialSet)) {
            if (subset.size() != 0) {
                structure.put(subset, new PartialSolution());
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
            return structure.get(subset);
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
