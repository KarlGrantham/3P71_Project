import java.util.LinkedList;

public abstract class Piece {
    boolean team;//white true
    char name;

    public void isThreating(int x, int y) {

    }
    boolean getTeam (){
        return team;
    }
    char getName(){
        return name;
    }
    abstract LinkedList<Coordinate> moves(int x, int y, Piece[][] board);

}