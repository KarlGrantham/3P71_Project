package project;

import java.util.LinkedList;

public class Piece {
    boolean team;//white true
//white true
    char name;
    //for castling and pawns, only used for rooks, pawns and kings
    boolean hasMoved;
    String imageName;

    public Piece() {
        this.hasMoved = false;
    }

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

    public Piece deepCopy() {
        return null;
    }

}