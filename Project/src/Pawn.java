import java.util.LinkedList;

public class Pawn extends Piece {

    public Pawn(boolean t) {
        team = t;
        if (team) {
            name = 'P';
        } else {
            name = 'p';
        }
    }

    //check avail does not apply to pawns, they have specific attack patterns
    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        if (team) {//if the moving piece is white
            //moving
            if (board[x + 1][y] == null) {//if the space forward is empty
                Coordinate curr = new Coordinate(x + 1, y);
                moveset.add(curr);
                if (x == 1) {//if the pawn is at the starting position
                    if ((board[x + 2][y] == null)) {//if the space 2 forward is empty
                        Coordinate curr2 = new Coordinate(x + 2, y);
                        moveset.add(curr2);
                    }
                }
            }
            //attacking
            if (y + 1 <= 7) {//if moving down is possible
                if (board[x + 1][y + 1].getName() > 96) {//if there's a black piece in that position
                    Coordinate curr = new Coordinate(x + 1, y + 1);
                    moveset.add(curr);
                }
            }
            if (y - 1 >= 0) {//if moving up is possible
                if (board[x + 1][y - 1].getName() > 96) {//if there's a black piece in that position
                    Coordinate curr = new Coordinate(x + 1, y - 1);
                    moveset.add(curr);
                }
            }
        } else {//else, moving piece is black
            //moving
            if (board[x - 1][y] == null) {//if the space forward is empty
                Coordinate curr = new Coordinate(x - 1, y);
                moveset.add(curr);
                if (x == 6) {//if the pawn is at the starting position
                    if ((board[x - 2][y] == null)) {//if the space 2 forward is empty
                        Coordinate curr2 = new Coordinate(x - 2, y);
                        moveset.add(curr2);
                    }
                }
            }
            //attacking
            if (y + 1 <= 7) {//if moving down is possible
                if (board[x - 1][y + 1].getName() < 91) {//if there's an enemy piece in that position
                    Coordinate curr = new Coordinate(x - 1, y + 1);
                    moveset.add(curr);
                }
            }
            if (y - 1 >= 0) {//if moving up is possible
                if (board[x + 1][y - 1].getName() < 91) {//if there's an enemy piece in that position
                    Coordinate curr = new Coordinate(x - 1, y + 1);
                    moveset.add(curr);
                }
            }
        }
        return moveset;
    }
}