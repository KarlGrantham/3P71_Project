import java.util.LinkedList;

public class Knight extends Piece {

    public Knight(boolean t) {
        team = t;
        if (team) {
            name = 'N';
            imageName = "wKnight";
        } else {
            name = 'n';
            imageName = "bKnight";
        }
    }

    @Override
    public Piece deepCopy () {
        boolean tempTeam = team;
        Knight temp = new Knight(tempTeam);
        return temp;
    }

    @Override
    public LinkedList<Coordinate> moves(int x, int y, Piece [][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        if (y + 1 <= 7) {//if down moves are possible
            if ((x + 1 <= 7)) {//if right-down moves are possible
                if (x + 2 <= 7) {
                    if (CheckAvail(x + 2, y + 1, board)) {
                        Coordinate curr = new Coordinate(x + 2, y + 1);
                        moveset.add(curr);
                    }
                }
                if (y + 2 <= 7) {
                    if (CheckAvail(x + 1, y + 2, board)) {
                        Coordinate curr = new Coordinate(x + 1, y + 2);
                        moveset.add(curr);
                    }
                }
            }
            if ((x - 1 >= 0)) {//if left-down moves are possible
                if (x - 2 >= 0) {
                    if (CheckAvail(x - 2, y + 1, board)) {
                        Coordinate curr = new Coordinate(x - 2, y + 1);
                        moveset.add(curr);
                    }
                }
                if (y + 2 <= 7) {
                    if (CheckAvail(x - 1, y + 2, board)) {
                        Coordinate curr = new Coordinate(x - 1, y + 2);
                        moveset.add(curr);
                    }
                }
            }
        }
        if (y - 1 >= 0) {//if up moves are possible
            if ((x - 1 >= 0)) {//if left-up moves are possible
                if (x - 2 >= 0) {
                    if (CheckAvail(x - 2, y - 1, board)) {
                        Coordinate curr = new Coordinate(x - 2, y - 1);
                        moveset.add(curr);
                    }
                }
                if (y - 2 >= 0) {
                    if (CheckAvail(x - 1, y - 2, board)) {
                        Coordinate curr = new Coordinate(x - 1, y - 2);
                        moveset.add(curr);
                    }
                }
            }
            if ((x + 1 <= 7)) {//if right-up moves are possible
                if (x + 2 <= 7) {
                    if (CheckAvail(x + 2, y - 1, board)) {
                        Coordinate curr = new Coordinate(x + 2, y - 1);
                        moveset.add(curr);
                    }
                }
                if (y - 2 >= 0) {
                    if (CheckAvail(x + 1, y - 2, board)) {
                        Coordinate curr = new Coordinate(x + 1, y - 2);
                        moveset.add(curr);
                    }
                }
            }
        }
        return moveset;
    }
    boolean CheckAvail(int x, int y, Piece [][] board) {
        if (board[x][y] == null){//if the space is empty
            return true;
        }
        if (team){//if the moving piece is white
            if (board[x][y].getName() > 96){//if the position contains a black piece(lower case letter)
                return true;
            }
        }
        else{//else, moving piece is black
            if (board[x][y].getName() < 91){//if the position contains a white piece(upper case letter)
                return true;
            }
        }
        return false;//if all of these conditions fail, space is occupied by friendly piece, therefore cannot move there
    }
}