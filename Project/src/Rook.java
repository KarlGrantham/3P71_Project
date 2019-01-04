import java.util.LinkedList;

public class Rook extends Piece {


    public Rook(boolean t) {
        hasMoved = false;
        team = t;
        if (team) {
            imageName = "wRook";
            name = 'R';
        } else {
            imageName = "bRook";
            name = 'r';
        }
    }


    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        int tempY = y;
        while (true) {//moving down
            if (tempY + 1 <= 7) {//if rook can move down
                if (board[x][tempY + 1] == null) {//if up is an empty space
                    Coordinate curr = new Coordinate(x, tempY + 1);
                    moveset.add(curr);
                } else if (board[x][tempY + 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x, tempY + 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                } else if (board[x][tempY + 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x, tempY + 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                }
            } else {
                break;
            }
            //if got here, the piece could move, increment position and try again
            tempY++;
        }
        tempY = y;//reset tempY
        while (true) {//moving up
            System.out.println(tempY);
            if (tempY - 1 >= 0) {//if rook can move up
                if (board[x][tempY - 1] == null) {//if down is an empty space
                    Coordinate curr = new Coordinate(x, tempY - 1);
                    moveset.add(curr);
                } else if (board[x][tempY - 1].getName() > 96) {//if space is occupied by a black piece
                    System.out.println("occupied by black piece stop move up");
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x, tempY - 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                } else if (board[x][tempY - 1].getName() < 91) {//if space is occupied by a white piece
                    System.out.println("occupied by white piece stop move up");
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x, tempY - 1);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                }
            } else {
                break;
            }
            //if got here, the piece could move, increment position and try again
            tempY--;
        }
        int tempX = x;
        while (true) {//moving left
            if (tempX - 1 >= 0) {//if rook can move left
                if (board[tempX - 1][y] == null) {//if left is an empty space
                    Coordinate curr = new Coordinate(tempX - 1, y);
                    moveset.add(curr);
                } else if (board[tempX - 1][y].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX - 1, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                } else if (board[tempX - 1][y].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(tempX - 1, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                }
            } else {
                break;
            }
            //if got here, the piece could move, increment position and try again
            tempX--;
        }
        tempX = x;//reset tempX
        while (true) {//moving right
            if (tempX + 1 <= 7) {//if rook can move right
                if (board[tempX + 1][y] == null) {//if right is an empty space
                    Coordinate curr = new Coordinate(tempX + 1, y);
                    moveset.add(curr);
                } else if (board[tempX + 1][y].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX + 1, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                } else if (board[tempX + 1][y].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(tempX + 1, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;
                }
            } else {
                break;
            }
            //if got here, the piece could move, increment position and try again
            tempX++;
        }
        return moveset;
    }
}
