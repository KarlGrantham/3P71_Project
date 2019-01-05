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

    @Override
    public Piece deepCopy () {
        boolean tempTeam = team;
        Rook temp = new Rook(tempTeam);
        boolean tempMoved = this.hasMoved;
        temp.hasMoved = tempMoved;
        return temp;
    }

    @Override
    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        int tempY = y;
        while (true) {//moving up
            int up = tempY - 1;
            if (up >= 0) {//if rook can move up
                if (board[x][up] == null) {//if up is an empty space
                    Coordinate curr = new Coordinate(x, up);
                    moveset.add(curr);
                } else if (board[x][up].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x, up);//moving piece may capture, but cannot move further
                        moveset.add(curr);//moving piece may capture, but cannot move further
                    }
                    break;//moving piece can no longer move
                } else if (board[x][up].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x, up);//moving piece may capture, but cannot move further
                        moveset.add(curr);//moving piece may capture, but cannot move further
                    }
                    break;//moving piece can no longer move
                }
            } else {//else, there is no room left on board
                break;//moving piece can no longer move
            }
            //if got here, the piece could move, increment position and try again
            tempY--;
        }
        tempY = y;//reset tempY
        while (true) {//moving down
            int down = tempY + 1;
            if (down <= 7) {//if rook can move down
                if (board[x][down] == null) {//if down is an empty space
                    Coordinate curr = new Coordinate(x, down);
                    moveset.add(curr);
                } else if (board[x][down].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x, down);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                } else if (board[x][down].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x, down);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                }
            } else {//else, there is no room left on board
                break;//moving piece can no longer move
            }
            //if got here, the piece could move, increment position and try again
            tempY++;
        }

        int tempX = x;
        while (true) {//moving left
            int left = tempX - 1;
            if (left >= 0) {//if rook can move left
                if (board[left][y] == null) {//if left is an empty space
                    Coordinate curr = new Coordinate(left, y);
                    moveset.add(curr);
                } else if (board[left][y].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(left, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);//moving piece may capture, but cannot move further
                    }
                    break;//moving piece can no longer move
                } else if (board[left][y].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(left, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);//moving piece may capture, but cannot move further
                    }
                    break;//moving piece can no longer move
                }
            } else {//else, there is no room left on board
                break;//moving piece can no longer move
            }
            //if got here, the piece could move, increment position and try again
            tempX--;
        }
        tempX = x;//reset tempX
        while (true) {//moving right
            int right = tempX +1;
            if (right <= 7) {//if rook can move right
                if (board[right][y] == null) {//if right is an empty space
                    Coordinate curr = new Coordinate(right, y);
                    moveset.add(curr);
                } else if (board[right][y].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(right, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);//moving piece may capture, but cannot move further
                    }
                    break;//moving piece can no longer move
                } else if (board[right][y].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(right, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);//moving piece may capture, but cannot move further
                    }
                    break;//moving piece can no longer move
                }
            } else {//else, there is no room left on board
                break;//moving piece can no longer move
            }
            //if got here, the piece could move, increment position and try again
            tempX++;
        }
        return moveset;
    }
}