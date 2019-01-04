import java.util.LinkedList;

public class Piece {
    boolean team;//white true
    char name;
    boolean hasMoved;//for castling and pawns, only used for rooks, pawns and kings
    String imageName;

    public void isThreating(int x, int y) {

    }
    boolean getTeam (){
        return team;
    }
    char getName(){
        return name;
    }
    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        return null;
    }

}