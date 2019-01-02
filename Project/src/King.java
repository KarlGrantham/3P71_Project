import java.util.LinkedList;

public class King extends Piece {

    public King(boolean t) {
        team = t;
        if (team) {
            name = 'K';
        } else {
            name = 'k';
        }
    }

    public LinkedList<Coordinate> moves(int x, int y, Piece[][] board) {
        LinkedList<Coordinate> moveset = new LinkedList<>();
        if (y + 1 <= 7) {//if there is space down
            if (x - 1 >= 0) {//if there is space down-left
                if (board[x - 1][y + 1] == null) {//if this space is free
                    Coordinate curr = new Coordinate(x - 1, y + 1);
                    moveset.add(curr);
                } else if (board[x - 1][y + 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x - 1, y + 1);//moving piece may capture
                        moveset.add(curr);
                    }
                } else if (board[x - 1][y + 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x - 1, y + 1);//moving piece may capture
                        moveset.add(curr);
                    }
                }
            }
            if (board[x][y + 1] == null) {//if the space directly down is free
                Coordinate curr = new Coordinate(x, y + 1);
                moveset.add(curr);
            } else if (board[x][y + 1].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(x, y + 1);//moving piece may capture
                    moveset.add(curr);
                }
            } else if (board[x][y + 1].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is black
                    Coordinate curr = new Coordinate(x, y + 1);//moving piece may capture
                    moveset.add(curr);
                }
            }
            if (x + 1 <= 7) {//if there is space down-right
                if (board[x + 1][y + 1] == null) {//if this space is free
                    Coordinate curr = new Coordinate(x + 1, y + 1);
                    moveset.add(curr);
                } else if (board[x + 1][y + 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x + 1, y + 1);//moving piece may capture
                        moveset.add(curr);
                    }
                } else if (board[x + 1][y + 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x + 1, y + 1);//moving piece may capture
                        moveset.add(curr);
                    }
                }
            }
        }
        if (x - 1 >= 0) {//if there is space directly left
            if (board[x - 1][y] == null) {//if this space is free
                Coordinate curr = new Coordinate(x - 1, y);
                moveset.add(curr);
            } else if (board[x - 1][y].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(x - 1, y);//moving piece may capture
                    moveset.add(curr);
                }
            } else if (board[x - 1][y].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is black
                    Coordinate curr = new Coordinate(x - 1, y);//moving piece may capture
                    moveset.add(curr);
                }
            }
        }
        if (x + 1 <= 7) {//if there is space directly right
            if (board[x + 1][y] == null) {//if this space is free
                Coordinate curr = new Coordinate(x + 1, y);
                moveset.add(curr);
            } else if (board[x + 1][y].getName() > 96) {//if space is occupied by a black piece
                if (team) {//if moving piece is white
                    Coordinate curr = new Coordinate(x + 1, y);//moving piece may capture
                    moveset.add(curr);
                }
            } else if (board[x + 1][y].getName() < 91) {//if space is occupied by a white piece
                if (!team) {//if moving piece is black
                    Coordinate curr = new Coordinate(x + 1, y);//moving piece may capture
                    moveset.add(curr);
                }
            }
        }
        if (y - 1 >= 0) {//if there is space up
            if (x - 1 >= 0) {//if there is space up-left
                if (board[x - 1][y - 1] == null) {//if this space is free
                    Coordinate curr = new Coordinate(x - 1, y - 1);
                    moveset.add(curr);
                } else if (board[x - 1][y - 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x - 1, y - 1);//moving piece may capture
                        moveset.add(curr);
                    }
                } else if (board[x - 1][y - 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x - 1, y - 1);//moving piece may capture
                        moveset.add(curr);
                    }
                }
                if (board[x][y - 1] == null) {//if the space directly up is free
                    Coordinate curr = new Coordinate(x, y - 1);
                    moveset.add(curr);
                } else if (board[x][y - 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x, y - 1);//moving piece may capture
                        moveset.add(curr);
                    }
                } else if (board[x][y - 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x, y - 1);//moving piece may capture
                        moveset.add(curr);
                    }
                }
                if (x + 1 <= 7) {//if there is space up-right
                    if (board[x + 1][y - 1] == null) {//if this space is free
                        Coordinate curr = new Coordinate(x + 1, y - 1);
                        moveset.add(curr);
                    }
                } else if (board[x + 1][y - 1].getName() > 96) {//if space is occupied by a black piece
                    if (team) {//if moving piece is white
                        Coordinate curr = new Coordinate(x + 1, y - 1);//moving piece may capture
                        moveset.add(curr);
                    }
                } else if (board[x + 1][y - 1].getName() < 91) {//if space is occupied by a white piece
                    if (!team) {//if moving piece is black
                        Coordinate curr = new Coordinate(x + 1, y - 1);//moving piece may capture
                        moveset.add(curr);
                    }
                }
            }
        }
        return moveset;
    }
}
