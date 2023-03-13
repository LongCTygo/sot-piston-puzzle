package net.longct.pistonsolver.solver;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class SolutionTree {

    Node root;

    public SolutionTree(){

    }

    public SolutionTree(Node root){
        this.root = root;
    }

    public boolean isEmpty(){
        return root == null;
    }

    @Deprecated
    public int solveBFS(int maxDepth, int maxSearch, boolean ignoreBadMoves, boolean printSol) {
        ArrayDeque<Node> queue = new ArrayDeque<>();
        ArrayList<Double> bestDist = new ArrayList<>();
//        HashSet<int[][]> solved = new HashSet<>();

        bestDist.add(root.board.getDistance());
        int currentDepth = 0;
        int currentProcess = 0;
        queue.add(root);
        while (!queue.isEmpty()){
            Node p = queue.pop();
//            if (solved.contains(p.board.getBoard())){
//                continue;
//            } else {
//                solved.add(p.board.board);
//            }
            currentProcess++;
            if (currentProcess > maxSearch) {
                return -2; //Special code for interrupted
            }
            int depthP = p.depth();
            double distP = p.board.getDistance();
            if (depthP != currentDepth){
                currentDepth = depthP;
                bestDist.add(distP);
            } else if (distP < bestDist.get(depthP)){
                bestDist.set(depthP, distP);
            }
            int result = p.breadthSolve(maxDepth,depthP,distP);
            if (result == -2){
                break;
            } else if (result == -1){
                p.createChildren(ignoreBadMoves);
                for (Node c : p.children){
                    if (depthP <= 3 || c.board.getDistance() < bestDist.get(depthP - 4)){
                        queue.add(c);
                    }
                }
                //De-referencing children nodes
                p.children.clear();
            } else{
                if (printSol) p.printSolutions();
                return result;
            }
        }
        if (printSol){
            System.out.println(root.board);
            System.out.println("No solutions");
        }
        return -1;
    }

    public Node solveBFS(int maxDepth, int maxSearch, boolean ignoreBadMoves){
        ArrayDeque<Node> queue = new ArrayDeque<>();
        ArrayList<Double> bestDist = new ArrayList<>();

        bestDist.add(root.board.getDistance());
        int currentDepth = 0;
        int currentProcess = 0;
        queue.add(root);
        while (!queue.isEmpty()){
            Node p = queue.pop();
            currentProcess++;
            if (currentProcess > maxSearch) {
                return null;
            }
            int depthP = p.depth();
            double distP = p.board.getDistance();
            if (depthP != currentDepth){
                currentDepth = depthP;
                bestDist.add(distP);
            } else if (distP < bestDist.get(depthP)){
                bestDist.set(depthP, distP);
            }
            int result = p.breadthSolve(maxDepth,depthP,distP);
            if (result == -2){
                break;
            } else if (result == -1){
                p.createChildren(ignoreBadMoves);
                for (Node c : p.children){
                    if (depthP <= 3 || c.board.getDistance() < bestDist.get(depthP - 4)){
                        queue.add(c);
                    }
                }
                //De-referencing children nodes
                p.children.clear();
            } else{
                return p;
            }
        }
        return null;
    }
}
