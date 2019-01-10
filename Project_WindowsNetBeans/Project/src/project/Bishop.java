package project;

import java.util.LinkedList;

public class Bishop extends Piece {

    public Bishop(boolean t) {
        hasMoved = false;
        team = t;
        value = 3;
        if (team) {
            name = 'B';
            imageName = "wBishop";
        } else {
            name = 'b';
            imageName = "bBishop";
        }
    }

    @Override
    public Piece deepCopy () {
        boolean tempTeam = team;
        Bishop temp = new Bishop(tempTeam);
        return temp;
    }
    
    @Override
    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        int tempX = x;
        int tempY = y;
        while (true) {//moving up-left
            int up = tempY - 1;
            int left = tempX - 1;
            if ((left >= 0) && (up >= 0)) {//if bishop can move up-left
                if (board[left][up] == null) {//if up-left is an empty space
                    Coordinate curr = new Coordinate(left, up);
                    moveset.add(curr);
                } else if (board[left][up].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(left, up);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                } else if (board[left][up].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(left, up);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                }
            } else {//else, there is no room left on board
                break;//moving piece can no longer move
            }
            //if got here, the piece could move, increment position and try again
            tempX--;
            tempY--;
        }
        tempX = x;//reset tempX
        tempY = y;//reset tempY
        while (true) {//moving up-right
            int up = tempY - 1;
            int right = tempX + 1;
            if ((right <= 7) && (up >= 0)) {//if bishop can move up-right
                if (board[right][up] == null) {//if up-right is an empty space
                    Coordinate curr = new Coordinate(right, up);
                    moveset.add(curr);
                } else if (board[right][up].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(right, up);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                } else if (board[right][up].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is white
                        Coordinate curr = new Coordinate(right, up);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                }
            } else {//else, there is no room left on board
                break;//moving piece can no longer move
            }
            //if got here, the piece could move, increment position and try again
            tempX++;
            tempY--;
        }
        tempX = x;//reset tempX
        tempY = y;//reset tempY
        while (true) {//moving down-left
            int down = tempY + 1;
            int left = tempX - 1;
            if ((left >= 0) && (down <= 7)) {//if bishop can move down-left
                if (board[left][down] == null) {//if down-left is an empty space
                    Coordinate curr = new Coordinate(left, down);
                    moveset.add(curr);
                } else if (board[left][down].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(left, down);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                } else if (board[left][down].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(left, down);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                }
            } else {//else, there is no room left on board
                break;//moving piece can no longer move
            }
            //if got here, the piece could move, increment position and try again
            tempX--;
            tempY++;
        }
        tempX = x;//reset tempX
        tempY = y;//reset tempY
        while (true) {//moving down-right
            int down = tempY + 1;
            int right = tempX + 1;
            if ((right <= 7) && (down<= 7)) {//if bishop can move down-right
                if (board[right][down] == null) {//if down-right is an empty space
                    Coordinate curr = new Coordinate(right, down);
                    moveset.add(curr);
                } else if (board[right][down].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(right, down);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                } else if (board[right][down].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(right, down);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                    break;//moving piece can no longer move
                }
            } else {//else, there is no room left on board
                break;//moving piece can no longer move
            }
            //if got here, the piece could move, increment position and try again
            tempX++;
            tempY++;
        }
        return moveset;
    }
}
