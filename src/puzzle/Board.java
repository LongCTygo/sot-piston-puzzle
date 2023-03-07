package puzzle;

import math.BaseConversionUtil;
import math.Vector2;

import java.util.Arrays;

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

    public Board(String seed) {
        parseSeed(seed);
    }

    public Board(int seed) {
        if (seed < MIN_SEED || seed > MAX_SEED) {
            throw new IllegalArgumentException("Seed must be in range of 0 and 8388607");
        }
        String s = BaseConversionUtil.binToHex(Integer.toBinaryString(seed));
        parseSeed(s);
    }

    public int[][] getBoard() {
        return board;
    }

    public Vector2 getGoal() {
        return goal;
    }

    public String getSeed() {
        Vector2 go = new Vector2(goal);
        // 1 ignored
        StringBuilder sb = new StringBuilder("0");
        // 1-2 rotation value
        int rotationalValue = getRotationalValue();
        sb.append(getTinyBit(rotationalValue));
        // Rotate player back to III
        int[][] bo = null;
        switch (rotationalValue) {
            case 0:
                // donothingbreak
                bo = copyOfBoard();
                break;
            case 1:
                bo = leftR();
                go.leftRotateBoard();
                break;
            case 2:
                bo = fullR();
                go.fullRotateBoard();
                break;
            case 3:
                bo = rightR();
                go.rightRotateBoard();
                break;
        }
        // 3-4 player location
        int px = -1;
        int py = -1;
        // 5 - 19 board
        StringBuilder b = new StringBuilder();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (bo[x][y] == 2) {
                    px = x;
                    py = y;
                } else {
                    b.append(bo[x][y]);
                }
            }
        }
        // throws ArrayIndexOutOfBoundException if board does not contain player
        sb.append(px - 2);
        sb.append(py);
        sb.append(b.toString());
        b = null;
        // 20 - 23 goal
        sb.append(getTinyBit(go.x));
        sb.append(getTinyBit(go.y));
        //Seed
        String binarySeed = sb.toString();
        String hexSeed = BaseConversionUtil.binToHex(binarySeed);
        while (hexSeed.length() < 6) {
            hexSeed = "0" + hexSeed;
        }
        return hexSeed;
    }


    private String getTinyBit(int value) {
        switch (value) {
            case 0:
                return "00";
            case 1:
                return "01";
            case 2:
                return "10";
            case 3:
                return "11";
        }
        throw new IllegalArgumentException("Cannot convert.");
    }

    /**
     * Returns the first occurence of the player position on the board, in the form
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
     * @return
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
            for (int y = 0; y < 4; y++) {
                newBoard[x][y] = board[x][y];
            }
        }
        return newBoard;
    }

    public void parseSeed(String seed) {
        // Check seed length
        if (seed.length() > 6) {
            throw new IllegalArgumentException("Seed length exceed 6.");
        } else if (seed.length() < 6) {
            do {
                seed = "0" + seed;
            } while (seed.length() < 6);
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
                case 1:
                    rightRotate();
                    break;
                case 2:
                    rotate180();
                    break;
                case 3:
                    leftRotate();
                    break;
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
            int x = (int) i / 4;
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
}
