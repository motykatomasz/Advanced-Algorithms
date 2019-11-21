import java.util.*;

public class ExactSolver implements Solver {

    AuctionProblemInstance a;
    List<PartialSolution> structure;
    Map<List<Integer>, Integer> setToIdDict;

    private int getOptimalValue(AuctionProblemInstance au) {
        a = au;
        //Initialize structures
        List<Integer> initialSet = createInitialSet();
        initializeSetToIdDict(initialSet);
        structure = new ArrayList<>();

        fillFirstRow();

        for (int i = 1; i < a.n; i++) {
            List<PartialSolution> newStructure = new ArrayList<>(structure);
            for (PartialSolution partialSolution : structure) {

                PartialSolution maxPartialSolution = new PartialSolution();
                for (Subset subsetObj : partialSolution.subsetList) {
                    Set<Integer> complementSubset = new HashSet(partialSolution.originalSet);
                    complementSubset.removeAll(subsetObj.subset);
                    PartialSolution previousPartialSolution = structure.get(subsetObj.subsetId);

                    int[] assignment = Arrays.copyOf(previousPartialSolution.assignment, a.k);
                    for (Integer j : complementSubset) {
                        assignment[j] = i;
                    }
                    PartialSolution ps = new PartialSolution(evaluate(assignment), assignment, partialSolution.originalSet, partialSolution.originalSetId, partialSolution.subsetList);

                    if (maxPartialSolution.getRevenue() <= ps.getRevenue())
                        maxPartialSolution = ps;
                }
                //Get max form maxPartialSolution and add to structure
                newStructure.set(partialSolution.originalSetId, maxPartialSolution);

            }
            structure = newStructure;
        }
        return structure.get(setToIdDict.get(initialSet)).getRevenue();
    }

    private List<Integer> createInitialSet() {
        List<Integer> initialSet = new ArrayList<>();
        for (int i = 0; i < a.k; i++) {
            initialSet.add(i);
        }
        return initialSet;
    }

    private void initializeSetToIdDict(List<Integer> initialSet) {
        setToIdDict = new HashMap<>();
        int i = 0;
        for (List<Integer> subset : Tools.generateSubsets(initialSet)) {
            setToIdDict.put(subset, i);
            i += 1;
        }
    }

    private PartialSolution createInitialSolution(int[] assignment, List<Integer> set) {

        List<List<Integer>> subsets = Tools.generateSubsets(set);
        List<Subset> subsetList = new ArrayList<>();
        for (List<Integer> subsetElem : subsets) {
            subsetList.add(new Subset(setToIdDict.get(subsetElem), subsetElem));
        }
        return new PartialSolution(evaluate(assignment), assignment, set, this.setToIdDict.get(set), subsetList);

    }

    public void fillFirstRow() {
        for (List<Integer> subset : setToIdDict.keySet()) {
            int[] assignment = new int[a.k];
            Arrays.fill(assignment, -1);
            for (Integer i : subset) {
                assignment[i] = 0;
            }
            PartialSolution ps = createInitialSolution(assignment, subset);
            structure.add(setToIdDict.get(subset), ps);
        }
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
