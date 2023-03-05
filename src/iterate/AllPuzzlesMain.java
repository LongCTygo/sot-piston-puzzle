package iterate;

import puzzle.Board;
import solver.Node;
import solver.SolutionTree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class AllPuzzlesMain {
    private final static int LIMIT = 2097152;
    private final static String PATH = "solutions\\";


    public static void main(String[] args) throws IOException {
        solve(LIMIT);
    }


    public static void solve(int limit) throws IOException {
        ArrayList<Integer> hard = new ArrayList<>();
        final int FLOOR = 0;
        BufferedWriter[] puzzleList = new BufferedWriter[22];
        // Create dir
        try {
            Files.createDirectories(Paths.get(PATH + "solutions"));
            Files.createDirectories(Paths.get("logs"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // Init array of BufferedWriter
        for (int i = 0; i < puzzleList.length; i++) {
            String path = PATH + String.format("solutions\\Best_%d_Moves.txt", i-1);
            File f = new File(path);
            if (f.exists()) {
                throw new FileAlreadyExistsException(path);
            }
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            puzzleList[i] = new BufferedWriter(fw);
        }
        // BufferedWriter for log
        File log = new File("logs\\solutions.txt");
        log.createNewFile();
        // Loop through all seed
        for (int i = 0; i < limit; i++) {
            int seedNo = i + FLOOR;
            System.out.printf("%d / %d\n",i + 1, limit);
            Board b = new Board(seedNo);
            // Build tree
            SolutionTree st = new SolutionTree(new Node(b));
            int steps = st.solveBFS(20, 10000000, true, false);
            if (steps >= -1) {
                puzzleList[steps+1].write(String.valueOf(seedNo));
                puzzleList[steps+1].newLine();
            } else if (steps == -2){
                hard.add(steps);
            }
        }
        // Closes and flushes
        for (BufferedWriter bufferedWriter : puzzleList) {
            bufferedWriter.close();
        }
        for (Integer x : hard){
            System.out.println(x);
        }
    }
}