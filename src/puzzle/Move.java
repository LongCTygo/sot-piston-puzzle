package puzzle;

public class Move {

    public static final int UP = -1;
    public static final int DOWN = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = -2;
    public static final String[] COLUMN = {"A","B","C","D"};
    public final int DIR;
    public final int SLOT;
    public Move(){
        this(0,-1);
    }
    public Move(int dir, int slot){
        DIR = dir;
        SLOT = slot;
    }

    @Override
    public String toString(){
        switch(DIR){
            case UP:
                return COLUMN[SLOT] + " up";
            case DOWN:
                return COLUMN[SLOT] + " down";
            case RIGHT:
                return String.format("%d right", 4-SLOT);
            case LEFT:
                return String.format("%d left", 4-SLOT);
        }
        return "??";
    }
}
