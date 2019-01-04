import java.util.LinkedList;

public class Bishop extends Piece {

    public Bishop(boolean t) {
        hasMoved = false;
        team = t;
        if (team) {
            name = 'B';
            imageName = "wBishop";
        } else {
            name = 'b';
            imageName = "bBishop";
        }
    }

    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        int tempX = x;
        int tempY = y;
        while (true) {//moving down-left
            if ((tempX - 1 >= 0) && (tempY + 1 <= 7)) {//if bishop can move up-left
                if (board[tempX - 1][tempY + 1] == null) {//if up-left is an empty space
                    Coordinate curr = new Coordinate(tempX - 1, tempY + 1);
                    moveset.add(curr);
                } else if (board[tempX - 1][tempY + 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX - 1, tempY + 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                } else if (board[tempX - 1][tempY + 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(tempX - 1, tempY + 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                }
            } else {
                break;
            }
            //if got here, the piece could move, increment position and try again
            tempX--;
            tempY++;
        }
        tempX = x;//reset tempX
        tempY = y;//reset tempY
        while (true) {//moving down-right
            if ((tempX + 1 <= 7) && (tempY + 1 <= 7)) {//if bishop can move down-right
                if (board[tempX + 1][tempY + 1] == null) {//if up-right is an empty space
                    Coordinate curr = new Coordinate(tempX + 1, tempY + 1);
                    moveset.add(curr);
                } else if (board[tempX + 1][tempY + 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX + 1, tempY + 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                } else if (board[tempX + 1][tempY + 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(tempX + 1, tempY + 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                }
            } else {
                break;
            }
            //if got here, the piece could move, increment position and try again
            tempX++;
            tempY++;
        }
        tempX = x;//reset tempX
        tempY = y;//reset tempY
        while (true) {//moving up-left
            if ((tempX - 1 >= 0) && (tempY - 1 >= 0)) {//if bishop can move up-left
                if (board[tempX - 1][tempY - 1] == null) {//if down-left is an empty space
                    Coordinate curr = new Coordinate(tempX - 1, tempY - 1);
                    moveset.add(curr);
                } else if (board[tempX - 1][tempY - 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX - 1, tempY - 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                } else if (board[tempX - 1][tempY - 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(tempX - 1, tempY - 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                }
            } else {
                break;
            }
            //if got here, the piece could move, increment position and try again
            tempX--;
            tempY--;
        }
        tempX = x;//reset tempX
        tempY = y;//reset tempY
        while (true) {//moving up-right
            if ((tempX + 1 <= 7) && (tempY - 1 >= 0)) {//if bishop can move up-right
                if (board[tempX + 1][tempY - 1] == null) {//if up-left is an empty space
                    Coordinate curr = new Coordinate(tempX + 1, tempY - 1);
                    moveset.add(curr);
                } else if (board[tempX + 1][tempY - 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX + 1, tempY - 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);

                    }
                    break;
                } else if (board[tempX + 1][tempY - 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX + 1, tempY - 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                }
            } else {
                break;
            }
            //if got here, the piece could move, increment position and try again
            tempX++;
            tempY--;
        }
        return moveset;
    }
}
