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
            try {
                int s = Integer.parseInt(seed);
                SolutionTree st = new SolutionTree(new Node(new Board(s)));
                st.solveBFS(20,10000000,true,true);
            } catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        }
    }
}