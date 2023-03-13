package net.longct.pistonsolver.solver;

import net.longct.pistonsolver.puzzle.Board;
import net.longct.pistonsolver.puzzle.Move;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author LongCT_
 */
public class Node {
    final double SMART_LIMIT = 1.5;
    ArrayList<Node> children = new ArrayList<>();
    Board board;
    Node parent = null;

    public Node(){
        board = new Board();
    }

    public Node(Board board){
        this.board = board;
    }

    public Node(Board board, Node parent){
        this.board = board;
        this.parent = parent;
    }

    public boolean addLast(Node p){
        return children.add(p);
    }

    public boolean addTop(Node p){
        children.add(0,p);
        return true;
    }

    public Node peek(){
        return children.get(0);
    }

    public Node getChild(int i){
        return children.get(i);
    }

    public Node pop(){
        return children.remove(0);
    }

    public Node pop(int i){
        return children.remove(i);
    }

    public int getChildCount(){
        return children.size();
    }

    public boolean isEmpty(){
        return children.isEmpty();
    }

    public int depth(){
        int depth = 0;
        Node p = this;
        while (p.parent != null){
            p=p.parent;
            depth++;
        }
        return depth;
    }

    public void createChildren(boolean ignoreBadMoves){
        Board p;
        double distance = board.getDistance();
        for (int i = 0; i < 4; i++){
            //Down
            if (board.lastMove == null || board.lastMove.DIR != Move.UP
                    || board.lastMove.SLOT != i){
                p = board.moveDown(i);
                if (p != null){
                    double pDis = p.getDistance();
                    if (pDis < distance || pDis < SMART_LIMIT){
                        addTop(new Node(p,this));
                    } else if (pDis == distance || ignoreBadMoves){
                        addLast(new Node(p,this));
                    }
                }
            }
            //Up
            if (board.lastMove == null || board.lastMove.DIR != Move.DOWN
                    || board.lastMove.SLOT != i){
                p = board.moveUp(i);
                if (p != null){
                    double pDis = p.getDistance();
                    if (pDis < distance || pDis < SMART_LIMIT){
                        addTop(new Node(p,this));
                    } else if (pDis == distance || ignoreBadMoves){
                        addLast(new Node(p,this));
                    }
                }
            }
            //Right
            if (board.lastMove == null || board.lastMove.DIR != Move.LEFT
                    || board.lastMove.SLOT != i){
                p = board.moveRight(i);
                if (p != null){
                    double pDis = p.getDistance();
                    if (pDis < distance || pDis < SMART_LIMIT){
                        addTop(new Node(p,this));
                    } else if (pDis == distance || ignoreBadMoves){
                        addLast(new Node(p,this));
                    }
                }
            }
            //Left
            if (board.lastMove == null || board.lastMove.DIR != Move.RIGHT
                    || board.lastMove.SLOT != i){
                p = board.moveLeft(i);
                if (p != null){
                    double pDis = p.getDistance();
                    if (pDis < distance || pDis < SMART_LIMIT){
                        addTop(new Node(p,this));
                    } else if (pDis == distance || ignoreBadMoves){
                        addLast(new Node(p,this));
                    }
                }
            }
        }
    }

    public void printSolutions(){
        Stack<Board> stack1 = new Stack<>();
        Stack<Move> stack2 = new Stack<>();
        Node p = this;
        while (p!=null){
            stack1.push(p.board);
            stack2.push(p.board.lastMove);
            p=p.parent;
        }
        int size = stack1.size();
        for (int i = 0; i < size; i++){
            Board b = stack1.pop();
            System.out.format("Step %d:\n", i);
            System.out.println(b);
        }
        System.out.println("[Sandy] Beep boop. My solution:");
        for (int i = 0; i < size; i++){
            Move ms = stack2.pop();
            if (ms == null){
                continue;
            }
            System.out.format("%d. %s   ",i,ms);
        }
    }

    @Deprecated
    public int solveDFS(int maxDepth,boolean ignoreBadMoves){
        int depth = depth();
        //Check
        if (board.getDistance() == 0){
            printSolutions();
            return depth;
        }
        if (depth >= maxDepth){
            return -1;
        }
        createChildren(ignoreBadMoves);
        while (!isEmpty()){
            Node p = getChild(0);
            int depthP = p.solveDFS(maxDepth, ignoreBadMoves);
            if (depthP != -1){
                return depthP;
            }
            p.parent = null;
            pop();
        }
        return -1;
    }

    public int breadthSolve(int maxDepth, int depth, double dist){
        if (depth >= maxDepth){
            return -2; //Quit
        }else if (dist == 0){
            return depth; // Found best
        } else {
            return -1; // Continue
        }
    }
}
