package math;

public class Vector2 {
    public int x;
    public int y;
    /**
     * Creates a zero vector.
     */
    public Vector2(){
        x=0;
        y=0;
    }
    /**
     * Creates a vector with pre-determined size.
     * @param x value x
     * @param y value y
     */
    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a vector identical to the parameter.
     * @param v a Vector2
     */
    public Vector2(Vector2 v){
        x = v.x;
        y = v.y;
    }

    public Vector2(int loc) {
        x = loc / 4;
        y = loc % 4;
    }

    /**
     * Returns the length of the Vector2.
     * @return the length of the vector.
     */
    public double length(){
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Returns the length of the Vector2. Functionally identical to length().
     * @return the length of the vector.
     */
    public double getLength(){
        return length();
    }

    /**
     * Returns a Vector2 that is the sum of this and another vector.
     * @param other the 2nd vector
     * @return A vector that is the sum of the two vectors.
     */
    public Vector2 plus(Vector2 other){
        return new Vector2(x+other.x,y+other.y);
    }

    /**
     * Returns a Vector2 that is the difference of this and another vector.
     * Note that the vector returned is this - other.
     * @param other the 2nd vector
     * @return A vector that is the sum of the two vectors.
     */
    public Vector2 minus(Vector2 other){
        return new Vector2(x-other.x,y-other.y);
    }

    /**
     * Return the distance between two point on the board, represented using Vector2.
     * @param other the 2nd vector
     * @return distance.
     */
    public double getDistance(Vector2 other){
        Vector2 v = this.minus(other);
        return v.length();
    }

    /**
     * Return true if location matches the data of the vector, false otherwise.
     * @param location board location
     * @return  Whether the location matches or not.
     */
    public boolean isMatching(int location){
        return (4*x + y) == location;
    }

    /**
     * Return the board location with the coordinate
     * @return location
     */
    public int toLocation(){
        return 4*x+y;
    }

    public void rightRotateBoard() {
        float nx = this.x - 1.5F;
        float ny = this.y - 1.5F;
        this.x = (int) (ny + 1.5F);
        this.y = (int) (-nx + 1.5F);
//    	System.out.println(toString());

    }

    public void leftRotateBoard() {
        float nx = this.x - 1.5F;
        float ny = this.y - 1.5F;
        this.x = (int) (-ny + 1.5F);
        this.y = (int) (nx + 1.5F);
    }

    public void fullRotateBoard() {
        this.x = 3 - this.x;
        this.y = 3 - this.y;
    }

    @Override
    public String toString() {
        return String.format("Vector2(%d,%d)", x,y);
    }
}