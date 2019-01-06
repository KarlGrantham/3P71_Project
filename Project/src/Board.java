/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

public class Board {

    private Piece[][] boardState = new Piece[8][8];
    int fitness;
    LinkedList<Board> children;
    Board parent;
    Coordinate[] lastMove;//the beginning and end Coordinates for the previous move (for en passant)
    boolean whiteCheck;//true if white is in check, false otherwise
    boolean blackCheck;//true if black is in check, false otherwise

    public Board(double rate, long seed) {//board randomizer
        Random randy = new Random(seed);
        LinkedList<Piece> remainingPieces = new LinkedList<>();
        boolean team = true;
        for (int j = 0; j < 2; j++) {//for each team
            for (int i = 0; i < 8; i++) {//add pawns to list
                remainingPieces.add(new Pawn(team));
            }
            for (int i = 0; i < 2; i++) {
                remainingPieces.add(new Knight(team));//add knights to list
                remainingPieces.add(new Bishop(team));//add bishops to list
                remainingPieces.add(new Rook(team));//add rooks to list
            }
            remainingPieces.add(new Queen(team));//add a queen to the list
            remainingPieces.add(new King(team));//add a king ti the list
            team = false;
        }
        Piece curr;
        while (!remainingPieces.isEmpty()) {
            //System.out.println("removing " + curr.getName());
            double chance = randy.nextDouble();
            if (chance <= rate) {
                while (true) {
                    int currX = randy.nextInt(8);
                    int currY = randy.nextInt(8);
                    if (boardState[currX][currY] == null) {//if space empty
                        curr = remainingPieces.remove();
                        boardState[currX][currY] = curr;
                        break;
                    }
                }
            } else {
                remainingPieces.remove();
            }
        }
        //random chance remove and put on the board at random spot, otherwise remove from list and don't put on board
        //if space occupied, try again
        fitness = calcFitness();
    }

    public Board(Piece[][] p) {//regular board
        boardState = deepCopy(p);
        fitness = calcFitness();
    }

    public Board() {//initial chess board
        boolean team = true;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[j][i * 5 + 1] = new Pawn(team);
            }
            boardState[0][i * 7] = new Rook(team);
            boardState[1][i * 7] = new Knight(team);
            boardState[2][i * 7] = new Bishop(team);
            boardState[3][i * 7] = new Queen(team);
            boardState[4][i * 7] = new King(team);
            boardState[5][i * 7] = new Bishop(team);
            boardState[6][i * 7] = new Knight(team);
            boardState[7][i * 7] = new Rook(team);
            team = false;
        }
        fitness = calcFitness();
    }

    public Board(String s) {//for Karl's testing
        boolean team = true;
        for (int i = 0; i < 2; i++) {
            boardState[0][i * 7] = new Rook(team);
            boardState[4][i * 7] = new King(team);
            boardState[7][i * 7] = new Rook(team);
            team = false;
        }
        boardState[0][4] = new Pawn(false);
        boardState[1][4] = new Pawn(true);
        boardState[6][3] = new Pawn(true);
        boardState[7][3] = new Pawn(false);

        fitness = calcFitness();
    }


    public void calculateChildren(boolean team) {
        LinkedList<Board> tempChildren = new LinkedList<>();//update children variable all at once, so that children list will not contain duplicate boards
        LinkedList<Coordinate> pieces = new LinkedList<>();
        for (int y = 0; y < 8; y++) {//for each spot on the board
            for (int x = 0; x < 8; x++) {
                if (boardState[x][y] != null && boardState[x][y].team == team) {//if there is a piece belonging to the specified team at this location
                    pieces.add(new Coordinate(x, y));//add this piece's coordinates to the list
                }
            }
        }
        while (!pieces.isEmpty()) {//while the list of piece coordinates still has elements
            Coordinate piece = pieces.remove(0);//get a piece coordinate
            LinkedList<Coordinate> moves = boardState[piece.x][piece.y].moves(piece.x, piece.y, boardState);//get list of that pieces moves
            while (!moves.isEmpty()) {
                Piece[][] childBoard = deepCopy(boardState);//duplicate the parent board to new array
                Coordinate move = moves.remove(0);//get move out of list
                Piece curr = childBoard[piece.x][piece.y].deepCopy();//get duplicate of piece that is currently moving
                childBoard[piece.x][piece.y] = null;//remove the piece from its origional location
                childBoard[move.x][move.y] = curr;//put the duplicate in the new position
                childBoard[move.x][move.y].hasMoved = true;//this piece has now moved
                Board child = new Board(childBoard);//create new board object with childBoard
                child.parent = this;
                tempChildren.add(child);//add this alteration to the list of children
            }
        }
        Board enPassantMove = checkEnPassant(team);
        LinkedList<Board> castlingMoves = checkCastling(team);
        if (castlingMoves.size() > 0) {
            tempChildren.addAll(castlingMoves);
        }
        if (enPassantMove != null) {
            tempChildren.add(enPassantMove);
        }
        children = tempChildren;//set child list to new child list
    }

    public Board checkEnPassant(boolean team) {
        //Problem: boardState is altered
        LinkedList<Coordinate> possiblePawnMoves = new LinkedList<>();
        for (int y = 0; y < 8; y++) {//for each spot on the board
            for (int x = 0; x < 8; x++) {
                if (boardState[x][y] != null && boardState[x][y].team == team) {//if there is a piece belonging to the specified team at this location
                    if (boardState[x][y].getClass().getName().equals("Pawn")) {//if that piece is a pawn
                        if (team && y == 4) {//if its a white piece in position
                            if ((boardState[x - 1][y] != null) && (boardState[x - 1][y].getClass().getName().equals("Pawn")) && (!boardState[x - 1][y].team)) {
                                //if there is a black pawn to the left
                                if (parent.getBoard()[x - 1][y] == null) {//if there was not a pawn there last board
                                    Board found = new Board(boardState);//create duplicate board;
                                    Piece attPiece = found.getBoard()[x][y].deepCopy();//get duplicate of attacking piece
                                    found.getBoard()[x - 1][y] = null;//capture piece
                                    found.getBoard()[x - 1][y + 1] = attPiece;//move attacking piece
                                    found.getBoard()[x][y] = null;
                                    return found;//there can only be one en passant move
                                }
                            }
                            if ((boardState[x + 1][y] != null) && (boardState[x + 1][y].getClass().getName().equals("Pawn")) && (!boardState[x + 1][y].team)) {
                                //if there is a black pawn to the right
                                if (parent.getBoard()[x + 1][y] == null) {//if there was not a pawn there last board
                                    Board found = new Board(boardState);//create duplicate board;
                                    Piece attPiece = found.getBoard()[x][y].deepCopy();//get duplicate of attacking piece
                                    found.getBoard()[x + 1][y] = null;//capture piece
                                    found.getBoard()[x + 1][y + 1] = attPiece;//move attacking piece
                                    found.getBoard()[x][y] = null;
                                    return found;//there can only be one en passant move
                                }
                            }
                        }
                        if (!team && y == 3) {//if its a black piece in position
                            if ((boardState[x - 1][y] != null) && (boardState[x - 1][y].getClass().getName().equals("Pawn")) && (boardState[x - 1][y].team)) {
                                //if there is a white pawn to the left
                                if (parent.getBoard()[x - 1][y] == null) {//if there was not a pawn there last board
                                    Board found = new Board(boardState);//create duplicate board;
                                    Piece attPiece = found.getBoard()[x][y].deepCopy();//get duplicate of attacking piece
                                    found.getBoard()[x - 1][y] = null;//capture piece
                                    found.getBoard()[x - 1][y - 1] = attPiece;//move attacking piece
                                    found.getBoard()[x][y] = null;
                                    return found;//there can only be one en passant move
                                }
                            }
                            if ((boardState[x + 1][y] != null) && (boardState[x + 1][y].getClass().getName().equals("Pawn")) && (boardState[x + 1][y].team)) {
                                //if there is a white pawn to the right
                                if (parent.getBoard()[x + 1][y] == null) {//if there was not a pawn there last board
                                    Board found = new Board(boardState);//create duplicate board;
                                    Piece attPiece = found.getBoard()[x][y].deepCopy();//get duplicate of attacking piece
                                    found.getBoard()[x + 1][y] = null;//capture piece
                                    found.getBoard()[x + 1][y - 1] = attPiece;//move attacking piece
                                    found.getBoard()[x][y] = null;
                                    return found;//there can only be one en passant move
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;//if got here, there are no en passant moves
    }


    public LinkedList<Board> checkCastling(boolean team) {
        LinkedList<Board> castles = new LinkedList<>();
        Coordinate[] castlingRooks = new Coordinate[2];//castling rook Coordinates
        Coordinate[] kingPositions = new Coordinate[2];//castling king Coordinates
        int index = 0;
        for (int y = 0; y < 8; y++) {//for each spot on the board
            for (int x = 0; x < 8; x++) {
                if (boardState[x][y] != null && boardState[x][y].team == team) {//if there is a piece belonging to the specified team at this location
                    if (boardState[x][y].getClass().getName().equals("Rook") && !boardState[x][y].hasMoved) {//if there is a rook that hasn't moved yet here
                        //left
                        int tempX = x;
                        while (index < 2) {//moving left, if at end of castling rooks index, finished, break
                            int left = tempX - 1;
                            if (left >= 0) {//if rook can move left
                                if (boardState[left][y] == null) {//if left is an empty space

                                } else if (boardState[left][y].getName() == 'k') {//if space is occupied by a black king
                                    if (!team) {//if moving piece is black
                                        if (!boardState[left][y].hasMoved) {//if that king has not moved
                                            kingPositions[index] = new Coordinate(left, y);
                                            castlingRooks[index] = new Coordinate(x, y);
                                            index++;
                                        }
                                    }
                                    break;//moving piece can no longer move
                                } else if (boardState[left][y].getName() == 'K') {//if space is occupied by a white king
                                    if (team) {//if moving piece is white
                                        if (!boardState[left][y].hasMoved) {//if that king has not moved
                                            kingPositions[index] = new Coordinate(left, y);
                                            castlingRooks[index] = new Coordinate(x, y);
                                            index++;
                                        }
                                    }
                                    break;//moving piece can no longer move
                                } else {//else, the path is blocked by some other piece
                                    break;//moving piece can no longer move
                                }
                            } else {//else, there is no room left on board
                                break;//moving piece can no longer move
                            }
                            //if got here, the piece could move, increment position and try again
                            tempX--;
                        }
                        //right
                        tempX = x;
                        while (index < 2) {//moving right, if at end of castling rooks index, finished, break
                            int right = tempX + 1;
                            if (right <= 7) {//if rook can move right
                                if (boardState[right][y] == null) {//if right is an empty space

                                } else if (boardState[right][y].getName() == 'k') {//if space is occupied by a black king
                                    if (!team) {//if moving piece is black
                                        if (!boardState[right][y].hasMoved) {//if that king has not moved
                                            kingPositions[index] = new Coordinate(right, y);
                                            castlingRooks[index] = new Coordinate(x, y);
                                            index++;
                                        }
                                    }
                                    break;//moving piece can no longer move
                                } else if (boardState[right][y].getName() == 'K') {//if space is occupied by a white king
                                    if (team) {//if moving piece is white
                                        if (!boardState[right][y].hasMoved) {//if that king has not moved
                                            kingPositions[index] = new Coordinate(right, y);
                                            castlingRooks[index] = new Coordinate(x, y);
                                            index++;
                                        }
                                    }
                                    break;//moving piece can no longer move
                                } else {//else, the path is blocked by some other piece
                                    break;//moving piece can no longer move
                                }
                            } else {//else, there is no room left on board
                                break;//moving piece can no longer move
                            }
                            //if got here, the piece could move, increment position and try again
                            tempX++;
                        }
                    }
                }
            }
        }
        //ok now
        if (index > 0) {
            LinkedList<Coordinate> threats = getThreats(team);//get all spaces which this team is threatened on
            for (int i = 0; i < index; i++) {
                Coordinate curr = castlingRooks[i];
                Coordinate kingCurr = kingPositions[i];
                //move king 2 spaces towards rook, move rook on other side of king
                //cannot castle if king is currently in check
                //cannot move the king into check
                //cannot if there is a space threatened between rook and king
                int threatsIndex = 0;
                boolean canCastle = true;
                while (threatsIndex < threats.size()) {
                    Coordinate currThreat = threats.get(threatsIndex);
                    if (kingCurr.y == currThreat.y) {//if the enemy can attack this row
                        if ((kingCurr.x == currThreat.x)) {//if king is in check
                            canCastle = false;//king is in check, cannot castle, break
                            break;
                        }
                        if (curr.x < kingCurr.x) {//if castling left
                            if ((currThreat.x > curr.x) && (currThreat.x < kingCurr.x)) {//if there is a threat between the king and rook
                                canCastle = false;//threat between two pieces, cannot castle, break
                                break;
                            }
                        } else {//else, castling right
                            if ((currThreat.x < curr.x) && (currThreat.x > kingCurr.x)) {//if there is a threat between the king and rook
                                canCastle = false;//threat between two pieces, cannot castle, break
                                break;
                            }
                        }
                    }
                    threatsIndex++;
                }
                //and finally
                if (canCastle) {

                    if (curr.x < kingCurr.x) {//if castling left
                        //Problem: somewhere in this if, boardstate is altered
                        Board castleBoard = new Board(boardState);
                        Piece dupKing = castleBoard.getBoard()[kingCurr.x][kingCurr.y].deepCopy();//duplicate the king piece
                        dupKing.hasMoved = true;//update hasMoved
                        castleBoard.getBoard()[kingCurr.x][kingCurr.y] = null;//move king
                        castleBoard.getBoard()[kingCurr.x - 2][kingCurr.y] = dupKing;
                        Piece dupRook = castleBoard.getBoard()[curr.x][curr.y].deepCopy();//duplicate the rook piece
                        dupRook.hasMoved = true;//update hasMoved
                        castleBoard.getBoard()[curr.x][curr.y] = null;//move rook
                        castleBoard.getBoard()[kingCurr.x - 1][kingCurr.y] = dupRook;
                        castles.add(castleBoard);
                    } else {//else, castling right
                        Board castleBoard = new Board(boardState);
                        Piece dupKing = castleBoard.getBoard()[kingCurr.x][kingCurr.y].deepCopy();//duplicate the king piece
                        dupKing.hasMoved = true;//update hasMoved
                        castleBoard.getBoard()[kingCurr.x][kingCurr.y] = null;//move king
                        castleBoard.getBoard()[kingCurr.x + 2][kingCurr.y] = dupKing;
                        Piece dupRook = castleBoard.getBoard()[curr.x][curr.y].deepCopy();//duplicate the rook piece
                        dupRook.hasMoved = true;//update hasMoved
                        castleBoard.getBoard()[curr.x][curr.y] = null;//move rook
                        castleBoard.getBoard()[kingCurr.x + 1][kingCurr.y] = dupRook;
                        castles.add(castleBoard);
                    }
                }
            }
        }
        return castles;
    }

    /*gets threatened locations useful for castling and check

     */
    public LinkedList<Coordinate> getThreats(boolean team) {
        LinkedList<Coordinate> threats = new LinkedList<>();
        LinkedList<Coordinate> enemies = new LinkedList<>();
        for (int y = 0; y < 8; y++) {//for each spot on the board
            for (int x = 0; x < 8; x++) {
                if (boardState[x][y] != null && boardState[x][y].team != team) {//if there is a piece belonging to the specified team at this location
                    enemies.add(new Coordinate(x, y));//add this piece's coordinates to the list
                }
            }
        }
        while (!enemies.isEmpty()) {//while the list of piece coordinates still has elements
            Coordinate enemySpot = enemies.remove(0);//get a piece coordinate
            LinkedList<Coordinate> moves = boardState[enemySpot.x][enemySpot.y].moves(enemySpot.x, enemySpot.y, boardState);//get list of that pieces moves
            threats.addAll(moves);//add all of those moves to the spots that are threatened
        }
        return threats;
    }


    public Piece[][] deepCopy(Piece[][] p) {
        Piece[][] copy = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (p[i][j] != null) {

                    copy[i][j] = p[i][j].deepCopy();
                } else {
                    copy[i][j] = null;
                }
            }
        }
        return copy;
    }

    public Piece[][] getBoard() {
        return boardState;
    }

    public int calcFitness() {
        int pawnValue = 1;
        int rookValue = 3;
        int knightValue = 5;
        int bishopValue = 4;
        int queenValue = 8;
        int kingValue = 0;
        int fitness = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState[i][j] != null) {
                    switch (boardState[i][j].getName()) {
                        case 'p'://black pawn
                            fitness = fitness - pawnValue;
                            break;
                        case 'r'://black rook
                            fitness = fitness - rookValue;
                            break;
                        case 'n'://black knight
                            fitness = fitness - knightValue;
                            break;
                        case 'b'://black bishop
                            fitness = fitness - bishopValue;
                            break;
                        case 'q'://black queen
                            fitness = fitness - queenValue;
                            break;
                        case 'k'://black king
                            fitness = fitness - kingValue;
                            break;
                        case 'P'://white pawn
                            fitness = fitness + pawnValue;
                            break;
                        case 'R'://white rook
                            fitness = fitness + rookValue;
                            break;
                        case 'N'://white knight
                            fitness = fitness + knightValue;
                            break;
                        case 'B'://white bishop
                            fitness = fitness + bishopValue;
                            break;
                        case 'Q'://white queen
                            fitness = fitness + queenValue;
                            break;
                        case 'K'://white king
                            fitness = fitness + kingValue;
                            break;

                    }
                }
            }
        }
        return fitness;
    }

    public Board move(Coordinate lastPieceClicked, Coordinate coordinate) {
        Piece[][] newBoard = deepCopy(boardState);
        Piece temp = newBoard[lastPieceClicked.x][lastPieceClicked.y].deepCopy();
        newBoard[lastPieceClicked.x][lastPieceClicked.y] = null;
        newBoard[coordinate.x][coordinate.y] = temp;
        return new Board(newBoard);
    }

    public void printBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (boardState[x][y] != null) {
                    System.out.print(boardState[x][y].getName());
                } else {
                    System.out.print('-');
                }
            }
            System.out.println();
        }
    }


}