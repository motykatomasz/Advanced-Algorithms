import java.util.*;

public class ExactSolver implements Solver {

    AuctionProblemInstance a;

    /**
     * Lookup table containing solutions to subproblems.
     */
    PartialSolution[] structure;

    /**
     * Map of subsets to corresponding indexes in lookup table.
     */
    Map<List<Integer>, Integer> setToIdDict;

    /**
     * Implementation of the dynamic programming algorithm
     */
    private int getOptimalValue(AuctionProblemInstance au) {
        a = au;
        // Initialize structures
        List<Integer> initialSet = createInitialSet();
        initializeSetToIdDict(initialSet);
        structure = new PartialSolution[setToIdDict.size()];

        // Fill lookup table for first bidder only
        fillFirstRow();

        // Iteratively update lookup table using solutions from previous stage (for less bidders)
        for (int i = 1; i < a.n; i++) {
            PartialSolution[] newStructure = new PartialSolution[setToIdDict.size()];
            for (PartialSolution partialSolution : structure) {

                PartialSolution maxPartialSolution = new PartialSolution();

                // Check all possible ways to introduce new bidder to previous sub-problem
                for (Subset subsetObj : partialSolution.subsetList) {
                    List<Integer> complementSubset = new ArrayList<>(partialSolution.originalSet.subset);
                    complementSubset.removeAll(subsetObj.subset);
                    PartialSolution previousPartialSolution = structure[subsetObj.subsetId];

                    int[] assignment = Arrays.copyOf(previousPartialSolution.assignment, a.k);
                    for (Integer j : complementSubset) {
                        assignment[j] = i;
                    }
                    PartialSolution ps = new PartialSolution(evaluate(assignment), assignment, partialSolution.originalSet, partialSolution.subsetList);

                    if (maxPartialSolution.getRevenue() <= ps.getRevenue())
                        maxPartialSolution = ps;
                }
                newStructure[partialSolution.originalSet.subsetId] = maxPartialSolution;

            }
            structure = newStructure;
        }

        // Return optimal solution for root problem
        return structure[setToIdDict.get(initialSet)].getRevenue();
    }

    /**
     * Method to create initial set containing all the items of the auction.
     */
    private List<Integer> createInitialSet() {
        List<Integer> initialSet = new ArrayList<>();
        for (int i = 0; i < a.k; i++) {
            initialSet.add(i);
        }
        return initialSet;
    }

    /**
     * Method initializing map containing mapping from subsets to indices in lookup table.
     */
    private void initializeSetToIdDict(List<Integer> initialSet) {
        setToIdDict = new HashMap<>();
        int i = 0;
        for (List<Integer> subset : Tools.generateSubsets(initialSet)) {
            setToIdDict.put(subset, i);
            i += 1;
        }
    }

    /**
     * Method filling lookup table for first bidder only.
     */
    public void fillFirstRow() {
        for (List<Integer> subset : setToIdDict.keySet()) {
            int[] assignment = new int[a.k];
            Arrays.fill(assignment, -1);
            for (Integer i : subset) {
                assignment[i] = 0;
            }
            PartialSolution ps = createInitialSolution(assignment, subset);
            structure[setToIdDict.get(subset)] = ps;
        }
    }

    /**
     * Method creating initial solution for given sub-problem.
     */
    private PartialSolution createInitialSolution(int[] assignment, List<Integer> set) {

        List<List<Integer>> subsets = Tools.generateSubsets(set);
        List<Subset> subsetList = new ArrayList<>();
        for (List<Integer> subsetElem : subsets) {
            subsetList.add(new Subset(setToIdDict.get(subsetElem), subsetElem));
        }
        return new PartialSolution(evaluate(assignment), assignment, new Subset(this.setToIdDict.get(set), set), subsetList);

    }

    /**
     * Adaptation of evaluate method to be able to work with -1 assignments, which correspond to assigning certain
     * item to none of the bidders.
     */
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
