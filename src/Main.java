import math.Vector2;
import puzzle.Board;
import solver.Node;
import solver.SolutionTree;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0){
            throw new RuntimeException("No arguments were supplied. At least 1 seed must be given.");
        }
        for (String seed : args){
            long startTime = System.nanoTime();
            System.out.printf("------------------- Seed %s -------------------\n", seed);
            try {
                int s = Integer.parseInt(seed);
                SolutionTree st = new SolutionTree(new Node(new Board(s)));
                st.solveBFS(20,10000000,true,true);
                System.out.println();
            } catch (Exception ex){
                System.err.printf("Exception Caught for argument %s: %s\n",seed, ex.getClass().getName());
                System.err.println("Exception Message: " + ex.getMessage());
            }
            long endTime = System.nanoTime();
            System.out.printf("Finished in %s miliseconds.\n",(endTime-startTime) / 1000000L);
        }
    }
}