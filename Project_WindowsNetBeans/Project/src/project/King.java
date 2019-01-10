package project;
import java.util.LinkedList;

public class King extends Piece {

    public King(boolean t) {
        hasMoved = false;
        team = t;
        value = 0;
        if (team) {
            name = 'K';
            imageName = "wKing";
        } else {
            name = 'k';
            imageName = "bKing";
        }
    }

    @Override
    public Piece deepCopy () {
        boolean tempTeam = team;
        King temp = new King(tempTeam);
        boolean tempMoved = this.hasMoved;
        temp.hasMoved = tempMoved;
        return temp;
    }
    
    @Override
    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        int tempX = x;
        int tempY = y;
        if ((tempX - 1 >= 0) && (tempY + 1 <= 7)) {//if bishop can move up-left
            if (board[tempX - 1][tempY + 1] == null) {//if up-left is an empty space
                Coordinate curr = new Coordinate(tempX - 1, tempY + 1);
                moveset.add(curr);
            } else if (board[tempX - 1][tempY + 1].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(tempX - 1, tempY + 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            } else if (board[tempX - 1][tempY + 1].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is black
                    Coordinate curr = new Coordinate(tempX - 1, tempY + 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            }
        }

        tempX = x;//reset tempX
        tempY = y;//reset tempY
        if ((tempX + 1 <= 7) && (tempY + 1 <= 7)) {//if bishop can move down-right
            if (board[tempX + 1][tempY + 1] == null) {//if up-right is an empty space
                Coordinate curr = new Coordinate(tempX + 1, tempY + 1);
                moveset.add(curr);
            } else if (board[tempX + 1][tempY + 1].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(tempX + 1, tempY + 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            } else if (board[tempX + 1][tempY + 1].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is black
                    Coordinate curr = new Coordinate(tempX + 1, tempY + 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            }
        }

        tempX = x;//reset tempX
        tempY = y;//reset tempY
        if ((tempX - 1 >= 0) && (tempY - 1 >= 0)) {//if bishop can move up-left
            if (board[tempX - 1][tempY - 1] == null) {//if down-left is an empty space
                Coordinate curr = new Coordinate(tempX - 1, tempY - 1);
                moveset.add(curr);
            } else if (board[tempX - 1][tempY - 1].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(tempX - 1, tempY - 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            } else if (board[tempX - 1][tempY - 1].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is black
                    Coordinate curr = new Coordinate(tempX - 1, tempY - 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            }
        }

        tempX = x;//reset tempX
        tempY = y;//reset tempY
        if ((tempX + 1 <= 7) && (tempY - 1 >= 0)) {//if bishop can move up-right
            if (board[tempX + 1][tempY - 1] == null) {//if up-left is an empty space
                Coordinate curr = new Coordinate(tempX + 1, tempY - 1);
                moveset.add(curr);
            } else if (board[tempX + 1][tempY - 1].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(tempX + 1, tempY - 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);

                }
            } else if (board[tempX + 1][tempY - 1].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is white
                    Coordinate curr = new Coordinate(tempX + 1, tempY - 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            }
        }

        tempY = y;
        if (tempY + 1 <= 7) {//if rook can move down
            if (board[x][tempY + 1] == null) {//if up is an empty space
                Coordinate curr = new Coordinate(x, tempY + 1);
                moveset.add(curr);
            } else if (board[x][tempY + 1].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(x, tempY + 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            } else if (board[x][tempY + 1].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is black
                    Coordinate curr = new Coordinate(x, tempY + 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            }
        }

        tempY = y;//reset tempY
        if (tempY - 1 >= 0) {//if rook can move up
            if (board[x][tempY - 1] == null) {//if down is an empty space
                Coordinate curr = new Coordinate(x, tempY - 1);
                moveset.add(curr);
            } else if (board[x][tempY - 1].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(x, tempY - 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            } else if (board[x][tempY - 1].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is black
                    Coordinate curr = new Coordinate(x, tempY - 1);//moving piece may capture, but cannot move further
                    moveset.add(curr);
                }
            }

            tempX = x;
            if (tempX - 1 >= 0) {//if rook can move left
                if (board[tempX - 1][y] == null) {//if left is an empty space
                    Coordinate curr = new Coordinate(tempX - 1, y);
                    moveset.add(curr);
                } else if (board[tempX - 1][y].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX - 1, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                } else if (board[tempX - 1][y].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(tempX - 1, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                }
            }

            tempX = x;//reset tempX
            if (tempX + 1 <= 7) {//if rook can move right
                if (board[tempX + 1][y] == null) {//if right is an empty space
                    Coordinate curr = new Coordinate(tempX + 1, y);
                    moveset.add(curr);
                } else if (board[tempX + 1][y].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(tempX + 1, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                } else if (board[tempX + 1][y].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(tempX + 1, y);//moving piece may capture, but cannot move further
                        moveset.add(curr);
                    }
                }
            }

        }

        return moveset;
    }
}