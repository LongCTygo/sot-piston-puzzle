package net.longct.pistonsolver;

import net.longct.pistonsolver.puzzle.Board;
import net.longct.pistonsolver.solver.Node;
import net.longct.pistonsolver.solver.SolutionTree;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0){
            throw new RuntimeException("No arguments were supplied. At least 1 argument must be given.");
        }
        //Loop Mode
        if (args[0].equals("-loop")){
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("Input your list of seeds here: ");
                String input = scanner.nextLine();
                if (input.isEmpty()){
                    System.out.println("No input received. Stopping...");
                    return;
                }
                String[] seeds = input.split(" ");
                for (String seed : seeds){
                    solve(seed);
                }
            }
        } else { //Normal Mode
            for (String seed : args){
                solve(seed);
            }
        }
    }

    public static void solve(String seed){
        long startTime = System.nanoTime();
        System.out.printf("------------------- Seed '%s' -------------------\n", seed);
        try {
            int s = Integer.parseInt(seed);
            SolutionTree st = new SolutionTree(new Node(new Board(s)));
            st.solveBFS(20,10000000,true,true);
            System.out.println();
        } catch (Exception ex){
            System.err.printf("Exception Caught for argument %s: %s\n",seed, ex.getClass().getName());
            System.err.println("Exception Message: " + ex.getMessage());
            System.out.println();
        }
        long endTime = System.nanoTime();
        System.out.printf("Finished in %s miliseconds.\n",(endTime-startTime) / 1000000L);
    }
}