package project;
import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(boolean t) {
        team = t;
        if (team) {
            imageName = "wPawn";
            name = 'P';
        } else {
            imageName = "bPawn";
            name = 'p';
        }
    }
    
    @Override
    public Piece deepCopy () {
        boolean tempTeam = team;
        Pawn temp = new Pawn(tempTeam);
        return temp;
    }

    //check avail does not apply to pawns, they have specific attack patterns
    @Override
    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        if (team) {//if the moving piece is white
            //moving
            if (y + 1 <= 7) {
                if (board[x][y + 1] == null) {//if the space forward is empty
                    Coordinate curr = new Coordinate(x, y + 1);
                    moveset.add(curr);
                    if (y == 1) {//if the pawn is at the starting position
                        if ((board[x][y + 2] == null)) {//if the space 2 forward is empty
                            Coordinate curr2 = new Coordinate(x, y + 2);
                            moveset.add(curr2);
                        }
                    }
                }
                //attacking
                if (x + 1 <= 7) {//if moving down is possible
                    if (board[x + 1][y + 1] != null) {
                        if (board[x + 1][y + 1].getName() > 96) {//if there's a black piece in that position
                            Coordinate curr = new Coordinate(x + 1, y + 1);
                            moveset.add(curr);
                        }
                    }
                }
                if (x - 1 >= 0) {//if moving up is possible
                    if (board[x - 1][y + 1] != null) {
                        if (board[x - 1][y + 1].getName() > 96) {//if there's a black piece in that position
                            Coordinate curr = new Coordinate(x - 1, y + 1);
                            moveset.add(curr);
                        }
                    }
                }
            }
        } else if (y-1 >= 0) {//else, moving piece is black
            //moving
            if (board[x][y - 1] == null) {//if the space forward is empty
                Coordinate curr = new Coordinate(x, y - 1);
                moveset.add(curr);
                if (y == 6) {//if the pawn is at the starting position
                    if ((board[x][y - 2] == null)) {//if the space 2 forward is empty
                        Coordinate curr2 = new Coordinate(x, y - 2);
                        moveset.add(curr2);
                    }
                }
            }
            //attacking
            if (x + 1 <= 7) {//if moving down is possible
                if (board[x + 1][y - 1] != null) {
                    if (board[x + 1][y - 1].getName() < 91) {//if there's a black piece in that position
                        Coordinate curr = new Coordinate(x + 1, y - 1);
                        moveset.add(curr);
                    }
                }

            }
            if (x - 1 >= 0) {//if moving up is possible
                if (board[x - 1][y - 1] != null) {
                    if (board[x - 1][y - 1].getName() < 91) {//if there's a black piece in that position
                        Coordinate curr = new Coordinate(x - 1, y - 1);
                        moveset.add(curr);
                    }
                }
            }
        }
        return moveset;
    }
}