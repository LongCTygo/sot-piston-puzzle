package puzzle;

import math.BaseConversionUtil;
import math.Vector2;

import java.util.Arrays;

import static math.BaseConversionUtil.getTinyBit;

public class Board {
    public static final int MIN_SEED = 0;
    public static final int MAX_SEED = 8388607;

    public static final int MAX_DEFAULT_SEED = 2097151;
    public int[][] board = new int[4][4];
    public Move lastMove = null;
    public Vector2 goal;

    public Board() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                board[x][y] = 0;
            }
        }
        this.goal = new Vector2();
    }

    public Board(int[][] board) {
        this.board = board;
    }

    public Board(int[][] board, Vector2 goal) {
        this(board);
        this.goal = goal;
    }

    public Board(int[][] board, Vector2 goal, int dir, int slot) {
        this(board,goal);
        this.lastMove = new Move(dir, slot);
    }

    @Deprecated
    public Board(String seed) {
        parseSeed(seed);
    }

    public Board(int seed) {
        parseSeed(seed);
    }

    public int[][] getBoard() {
        return board;
    }

    public Vector2 getGoal() {
        return goal;
    }

    public int getSeed(){
        Vector2 go = new Vector2(goal);
        int seed = 0;
        // 0 ignored
        // 1-2 rotation value
        int rotationalValue = getRotationalValue();
        seed += (int) Math.pow(2,21) * rotationalValue;
        // Rotate player back to III
        int[][] bo = null;
        switch (rotationalValue) {
            case 0 -> bo = copyOfBoard();
            case 1 -> {
                bo = leftR();
                go.leftRotateBoard();
            }
            case 2 -> {
                bo = fullR();
                go.fullRotateBoard();
            }
            case 3 -> {
                bo = rightR();
                go.rightRotateBoard();
            }
        }
        // 3-4 player location
        int px = -1;
        int py = -1;
        // 5 - 19 board
        int k = 18;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (bo[x][y] == 2) {
                    px = x;
                    py = y;
                } else {
                    seed += (int) Math.pow(2,k) * bo[x][y];
                    k--;
                }
            }
        }
        // throws IllegalStateException if board does not contain player, or multiple players
        if (px == -1 && py == -1){
            throw new IllegalStateException("The board does not have a player.\n" + this);
        }
        if (k != 3){
            throw new IllegalStateException("The board has more than one player.\n" + this);
        }
        seed += (int) Math.pow(2,20) * (px -2)
                + (int) Math.pow(2,19) * (py);
        // 20 - 23 goal
        seed += 4 * go.x;
        seed += 1 * go.y;
        //Seed
        return seed;
    }


    /**
     * Returns the first occurrence of the player position on the board, in the form
     * of Vector2. Returns null if no players are found.
     *
     * @return player position
     */
    public Vector2 getPlayerPosition() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 2) {
                    return new Vector2(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Attempt to move down at the position y.
     *
     * @param y Position of the push.
     * @return the new Board
     */
    public Board moveDown(int y) {
        // Useless push
        if (board[0][y] == 0) {
            return null;
        }
        int x = 1;
        int t = -1;
        for (; x < 4; x++) {
            if (board[x][y] == 0) {
                t = x;
                break;
            }
        }
        // Can't push
        if (t == -1) {
            return null;
        }
        // Copy
        int[][] copyOfBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            copyOfBoard[i] = Arrays.copyOf(board[i], 4);
        }
        // Swap
        for (; t > 0; t--) {
            copyOfBoard[t][y] = copyOfBoard[t - 1][y];
        }
        copyOfBoard[0][y] = 0;
        Board newBoard = new Board(copyOfBoard, goal, Move.DOWN, y);
        return newBoard;
    }

    public Board moveUp(int y) {
        // Useless push
        if (board[3][y] == 0) {
            return null;
        }
        int x = 2;
        int t = -1;
        for (; x >= 0; x--) {
            if (board[x][y] == 0) {
                t = x;
                break;
            }
        }
        // Can't push
        if (t == -1) {
            return null;
        }
        // Copy
        int[][] copyOfBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            copyOfBoard[i] = Arrays.copyOf(board[i], 4);
        }
        // Swap
        for (; t < 3; t++) {
            copyOfBoard[t][y] = copyOfBoard[t + 1][y];
        }
        copyOfBoard[3][y] = 0;
        Board newBoard = new Board(copyOfBoard, goal, Move.UP, y);
        return newBoard;
    }

    public Board moveRight(int x) {
        // Useless push
        if (board[x][0] == 0) {
            return null;
        }
        int y = 1;
        int t = -1;
        for (; y < 4; y++) {
            if (board[x][y] == 0) {
                t = y;
                break;
            }
        }
        // Can't push
        if (t == -1) {
            return null;
        }
        // Copy
        int[][] copyOfBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            copyOfBoard[i] = Arrays.copyOf(board[i], 4);
        }
        // Swap
        for (; t > 0; t--) {
            copyOfBoard[x][t] = copyOfBoard[x][t - 1];
        }
        copyOfBoard[x][0] = 0;
        Board newBoard = new Board(copyOfBoard, goal, Move.RIGHT, x);
        return newBoard;
    }

    public Board moveLeft(int x) {
        // Useless push
        if (board[x][3] == 0) {
            return null;
        }
        int y = 2;
        int t = -1;
        for (; y >= 0; y--) {
            if (board[x][y] == 0) {
                t = y;
                break;
            }
        }
        // Can't push
        if (t == -1) {
            return null;
        }
        // Copy
        int[][] copyOfBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            copyOfBoard[i] = Arrays.copyOf(board[i], 4);
        }
        // Swap
        for (; t < 3; t++) {
            copyOfBoard[x][t] = copyOfBoard[x][t + 1];
        }
        copyOfBoard[x][3] = 0;
        Board newBoard = new Board(copyOfBoard, goal, Move.LEFT, x);
        return newBoard;
    }

    public double getDistance() {
        Vector2 player = getPlayerPosition();
        return player.getDistance(goal);
    }

    public void rotate180() {
        board = fullR();
        goal = new Vector2(3 - goal.x, 3 - goal.y);
    }

    public void rightRotate() {
        board = rightR();
        goal.rightRotateBoard();
    }

    public void leftRotate() {
        board = leftR();
        goal.leftRotateBoard();
    }

    private int[][] rightR() {
        int[][] newBoard = new int[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                newBoard[x][y] = board[3 - y][x];
            }
        }
        return newBoard;
    }

    private int[][] leftR() {
        int[][] newBoard = new int[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                newBoard[x][y] = board[y][3 - x];
            }
        }
        return newBoard;
    }

    private int[][] fullR() {
        int[][] newBoard = new int[4][4];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                newBoard[x][y] = board[3 - x][3 - y];
            }
        }
        return newBoard;
    }

    private int[][] copyOfBoard(){
        int[][] newBoard = new int[4][4];
        for (int x = 0; x < 4; x++) {
            System.arraycopy(board[x], 0, newBoard[x], 0, 4);
        }
        return newBoard;
    }

    private void parseSeed(int seed){
        int[] bitArray = BaseConversionUtil.toBinaryArray(seed);
        //first 9 bits are ignored
        // 9-10 Rotation value
        int rotationValue = bitArray[9] * 2 + bitArray[10] * 2;
        // 11-12 Player Position
        int px = bitArray[11] + 2;
        int py = bitArray[12];
        board[px][py] = 2;
        // 13 - 27 Board
        int k = 13;
        for (int x = 0; x < 4; x++) {
            boolean rowOfPlayer = (px == x);
            for (int y = 0; y < 4; y++) {
                if (rowOfPlayer && py == y) {
                    continue;
                }
                board[x][y] = bitArray[k];
                k++;
            }
        }
        //28-31 Goal
        int gx = bitArray[28] * 2 + bitArray[29];
        int gy = bitArray[30] * 2 + bitArray[31];
        goal = new Vector2(gx, gy);
        // Rotate
        switch (rotationValue) {
            case 1 -> rightRotate();
            case 2 -> rotate180();
            case 3 -> leftRotate();
        }
    }

    @Deprecated
    private void parseSeed(String seed) {
        // Check seed length
        if (seed.length() > 6) {
            throw new IllegalArgumentException("Seed length exceed 6.");
        } else if (seed.length() < 6) {
            StringBuilder seedBuilder = new StringBuilder(seed);
            do {
                seedBuilder.insert(0, "0");
            } while (seedBuilder.length() < 6);
            seed = seedBuilder.toString();

        }
        // Convert to binary
        String binarySeed = BaseConversionUtil.hexToBin(seed);
        char[] tokens = binarySeed.toCharArray();
        try {
            // 0 ignored
            // 1-2 rotation value
            int rotationValue = Integer.parseInt(String.valueOf(tokens[1])) * 2
                    + Integer.parseInt(String.valueOf(tokens[2]));
            // 3-4 player position at III quarter
            int px = Integer.parseInt(String.valueOf(tokens[3])) + 2;
            int py = Integer.parseInt(String.valueOf(tokens[4]));
            board[px][py] = 2;
            // 5 - 19 board
            int k = 5;
            for (int x = 0; x < 4; x++) {
                boolean rowOfPlayer = (px == x);
                for (int y = 0; y < 4; y++) {
                    if (rowOfPlayer && py == y) {
                        continue;
                    }
                    board[x][y] = Integer.parseInt(String.valueOf(tokens[k]));
                    k++;
                }
            }
            // Debugging
            if (k != 20) {
                throw new IllegalStateException("Did not traverse through entire bit. k = " + k);
            }
            // 20 - 23 goal
            int gx = Integer.parseInt(String.valueOf(tokens[20])) * 2 + Integer.parseInt(String.valueOf(tokens[21]));
            int gy = Integer.parseInt(String.valueOf(tokens[22])) * 2 + Integer.parseInt(String.valueOf(tokens[23]));
            goal = new Vector2(gx, gy);
            // Rotate
            switch (rotationValue) {
                case 1 -> rightRotate();
                case 2 -> rotate180();
                case 3 -> leftRotate();
            }
            // Done
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public int getRotationalValue() {
        Vector2 player = getPlayerPosition();
        if (player.x < 2) {
            if (player.y < 2) {
                return 1;
            }
            return 2;
        } else if (player.y < 2) {
            return 0;
        }
        return 3;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int x = i / 4;
            int y = i % 4;
            // If goal
            if (goal.isMatching(i)) {
                if (board[x][y] == 0) {
                    // unoccupied
                    sb.append("x ");
                } else {
                    // Occupied
                    sb.append("# ");
                }
            } else if (board[x][y] == 0) {
                sb.append(". ");
            } else if (board[x][y] == 1) {
                sb.append("o ");
            } else if (board[x][y] == 2) {
                sb.append("@ ");
            } else {
                sb.append("? ");
            }
            if (y == 3) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public boolean isGoalCovered(){
        return board[goal.x][goal.y] != 0;
    }

    public int getCoalCount(){
        int coal = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (board[i][j] == 1){
                    coal++;
                }
            }
        }
        return coal;
    }

    public static class BoardBuilder{
        private int[][] board = new int[4][4];
        private Vector2 goal;

        public BoardBuilder(){}
        public void setGoal(int x, int y){
            goal = new Vector2(x,y);
        }

        public void setRow(int row, int[] args){
            int r = 4 - row;
            for (int i = 0; i < 4; i++){
                board[r][i] = args[i];
            }
        }

        public void setBoard(int[][] board){
            if (board.length != 4){
                throw new IllegalArgumentException("The argument for the board must be exactly 4x4");
            }
            for (int[] row : board){
                if (row.length != 4){
                    throw new IllegalArgumentException("The argument for the board must be exactly 4x4");
                }
            }
            this.board = board.clone();
        }

        public Board build(){
            return new Board(board,goal);
        }
    }
}
