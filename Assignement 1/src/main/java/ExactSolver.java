import java.lang.reflect.Array;
import java.util.*;

public class ExactSolver implements Solver {

    private int getOptimalValue(AuctionProblemInstance a) {
        //Initialize structures
        Set<Integer> initialSet = new HashSet<>();
        for (int i = 0; i < a.k; i++) {
            initialSet.add(i);
        }
        Map<Set<Integer>, List<PartialSolution>> structure = initializeStructure(initialSet);

        //Fill out the first row
        for (Map.Entry<Set<Integer>, List<PartialSolution>> entry : structure.entrySet()) {
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
            for (Map.Entry<Set<Integer>, List<PartialSolution>> entry : structure.entrySet()) {

                List<PartialSolution> maxPartialSolution = new ArrayList<>();
                for (Set<Integer> subset : generateSubsets(entry.getKey())) {
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

        return structure.get(initialSet).get(a.n-1).getRevenue();
    }

    private Map<Set<Integer>, List<PartialSolution>> initializeStructure(Set<Integer> initialSet) {
        Map<Set<Integer>, List<PartialSolution>> structure = new HashMap<>();

        for (Set<Integer> subset : generateSubsets(initialSet)) {
            structure.put(subset, new ArrayList<PartialSolution>());
        }

        return structure;
    }

    private Set<Set<Integer>> generateSubsets(Set<Integer> set) {

        return null;
    }

    private int evaluate(int[] assignment, AuctionProblemInstance a) {
        
        return 0;
    }


    public AuctionProblemInstance.Solution solve(AuctionProblemInstance a, double epsilon) {
        return new AuctionProblemInstance.Solution(getOptimalValue(a), 0);
    }

    @Override
    public String getName() {
        return "Exact";
    }
}
