import math.Vector2;
import puzzle.Board;
import solver.Node;
import solver.SolutionTree;

public class Main {
    public static void main(String[] args) {
        int[][] b = {
                {1,0,0,1},
                {1,0,0,0},
                {1,0,0,0},
                {2,1,1,1},
        };
        Board board = new Board(b);
        board.goal = new Vector2(3);
        System.out.println(board);
//        Board board = new Board(1494131);
        Node root = new Node(board);
        SolutionTree st = new SolutionTree(root);
        int i = st.solveBFS(20, 10000000, true, true);
    }
}