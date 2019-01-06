package project;

import java.util.LinkedList;

public class Queen extends Piece {

    public Queen(boolean t) {
        hasMoved = false;
        team = t;
        if (team) {
            name = 'Q';
            imageName = "wQueen";
        } else {
            name = 'q';
            imageName = "bQueen";
        }
    }

    @Override
    public Piece deepCopy () {
        boolean tempTeam = team;
        Queen temp = new Queen(tempTeam);
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
            if ((left >= 0) && (up >= 0)) {//if queen can move up-left
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
            if ((right <= 7) && (up >= 0)) {//if queen can move up-right
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
            if ((left >= 0) && (down <= 7)) {//if queen can move down-left
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
            if ((right <= 7) && (down <= 7)) {//if queen can move down-right
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
        tempY = y;
        while (true) {//moving up
            int up = tempY - 1;
            if (up >= 0) {//if queen can move up
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
            if (down <= 7) {//if queen can move down
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
        tempX = x;
        while (true) {//moving left
            int left = tempX - 1;
            if (left >= 0) {//if queen can move left
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
            int right = tempX + 1;
            if (right <= 7) {//if queen can move right
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
