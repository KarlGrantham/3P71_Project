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
    boolean whiteCheck;//true if white is in check, false otherwise
    boolean blackCheck;//true if black is in check, false otherwise

    /**
     * Constructs the board randomly
     *
     * @param rate the decimal rate of pieces added
     * @param seed the seed for the randomizer
     */
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
        this.whiteCheck = checkCheck(true);
        this.blackCheck = checkCheck(false);
    }

    /**
     * Constructs a duplicate of the specified board
     *
     * @param p the baord to copy
     */
    public Board(Piece[][] p) {//regular board
        boardState = deepCopy(p);
        fitness = calcFitness();
        this.whiteCheck = checkCheck(true);
        this.blackCheck = checkCheck(false);
    }

    /**
     * Constructs the default starting chess board
     */
    public Board() {//initial chess board
        boolean team = true;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[j][i * 5 + 1] = new Pawn(team);
            }
            boardState[0][i * 7] = new Rook(team);
            boardState[1][i * 7] = new Knight(team);
            boardState[2][i * 7] = new Bishop(team);
            boardState[5][i * 7] = new Bishop(team);
            boardState[6][i * 7] = new Knight(team);
            boardState[7][i * 7] = new Rook(team);
            team = false;
        }
        boardState[3][0] = new Queen(true);
        boardState[4][0] = new King(true);
        boardState[4][7] = new Queen(false);
        boardState[3][7] = new King(false);
        fitness = calcFitness();
    }

    /**
     * Creates all boards that are possible as a result of a legal move on this board
     *
     * @param team the team currently moving
     */
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
                //check check
                child.whiteCheck = child.checkCheck(true);
                child.blackCheck = child.checkCheck(false);
                if (team) {//if white its white's turn to move
                    if (!child.whiteCheck) {//if this turn will not result in white being in check
                        tempChildren.add(child);//add this alteration to the list of children
                    }
                } else {//else, it's black's turn to move
                    if (!child.blackCheck) {//if this turn will not result in black being in check
                        tempChildren.add(child);//add this alteration to the list of children
                    }
                }
            }
            Board child = checkEnPassant(team);
            if (child !=null) {
                child.whiteCheck = child.checkCheck(true);
                child.blackCheck = child.checkCheck(false);
                if (team) {//if white its white's turn to move
                    if (!child.whiteCheck) {//if this turn will not result in white being in check
                        tempChildren.add(child);//add this alteration to the list of children
                    }
                } else {//else, it's black's turn to move
                    if (!child.blackCheck) {//if this turn will not result in black being in check
                        tempChildren.add(child);//add this alteration to the list of children
                    }
                }
            }
        }
        children = tempChildren;//set child list to new child list
    }

    public boolean checkCheckmate(boolean team) {
        boolean checkmate = false;
        if (checkCheck(team)) {//if the team is currently in check
            this.calculateChildren(team);
            if (this.children.size() == 0) {//if there are no legal moves that this team can make
                checkmate = true;
            }
        }
        return checkmate;
    }

    public boolean checkStalemate() {
        boolean stalemate = false;
        Board tempBoard = this;
        int counter = 0;
        while (true) {
            if(tempBoard.parent == null || tempBoard.parent.parent == null){//if ever there are insufficient parents
                    break;
            }
            if (counter >= 3){//if we've reached 3 turns of this
                stalemate = true;
                break;
            }
            if (checkBoardEquality(tempBoard.boardState, tempBoard.parent.parent.boardState)){//if this board is the same as its grandparent's
                tempBoard = tempBoard.parent.parent;//increment tempBoard
                counter++;//increment counter
            }
            else{//else, they're not equal
                break;
            }
        }
        counter = 0;
        tempBoard = this;
        while (true) {
            if(tempBoard.parent == null || tempBoard.parent.parent == null){//if ever there are insufficient parents
                    break;
            }
            if (counter >= 50){//if we've reached 50 turns of this
                stalemate = true;
                break;
            }
            if (tempBoard.fitness == tempBoard.parent.fitness){//if there has been no capture
                tempBoard = tempBoard.parent.parent;//increment tempBoard
                counter++;//increment counter
            }
            else{//else, they're not equal
                break;
            }
        }
        return stalemate;
    }

    /**checks two Piece arrays for equality
     *
     * @param p1 the first board
     * @param p2 the second board
     * @return true if the boards are equal, false otherwise
     */
    public boolean checkBoardEquality(Piece[][] p1, Piece[][] p2) {
        for (int y = 0; y < 8; y++) {//for each spot on the board
            for (int x = 0; x < 8; x++) {
                if (p1[x][y] == null){//if this spot on p1 is empty
                    if (p2[x][y] != null){//if this spot on p2 is not also empty
                        return false;//not equal
                    }
                }
                else if (p2[x][y] == null){//if this spot on p2 is empty
                    if (p1[x][y] != null){//if this spot on p1 is not also empty
                        return false;//not equal
                    }
                }
                else{//else, this space is full on both boards
                    if (!(p1[x][y].getClass().getName().equals(p2[x][y].getClass().getName()))){//if they're not the same type of piece
                        return false;//not equal
                    }
                    if (p1[x][y].getTeam() != p2[x][y].getTeam()){//if they're not on the same team
                        return false;//not equal
                    }
                }
            }
        }
        return true;//if got here, boards are equal
    }

    /**
     * Checks if a team is in check. Useful for blackCheck, whiteCheck
     *
     * @param team the team currently being checked
     * @return true if in check, false otherwise
     */


    public boolean checkCheck(boolean team) {
        boolean check = false;
        Coordinate kingPos = new Coordinate(-1, -1);//initialize to impossible position
        for (int y = 0; y < 8; y++) {//for each spot on the board
            for (int x = 0; x < 8; x++) {
                if (boardState[x][y] != null && boardState[x][y].team == team) {//if there is a piece belonging to the specified team at this location
                    if (boardState[x][y].getClass().getName().equals("King")) {//if this piece is the king
                        kingPos = new Coordinate(x, y);
                    }
                }
            }
        }
        LinkedList<Coordinate> threats = getThreats(team);
        while (!threats.isEmpty()) {
            Coordinate threat = threats.remove();
            if (threat.x == kingPos.x && threat.y == kingPos.y) {//if the threat coordinate is the king's position
                check = true;
            }
        }
        return check;
    }

    /**
     * Checks if there are any en passant moves possible and returns the board on which the move has been made
     *
     * @param team the team currently moving
     * @return the Board with the en passant move made, null if not possible
     */
    public Board checkEnPassant(boolean team) {
        //Problem: boardState is altered
        LinkedList<Coordinate> possiblePawnMoves = new LinkedList<>();
        for (int y = 0; y < 8; y++) {//for each spot on the board
            for (int x = 0; x < 8; x++) {
                if (boardState[x][y] != null && boardState[x][y].team == team) {//if there is a piece belonging to the specified team at this location
                    if (boardState[x][y].getClass().getName().equals("Pawn")) {//if that piece is a pawn
                        if (team && y == 4) {//if its a white piece in position
                            if (x - 1 >= 0) {
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
                            }
                            if (x + 1 <= 7) {
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
                        }
                        if (!team && y == 3) {//if its a black piece in position
                            if (x - 1 >= 0) {
                                if ((boardState[x - 1][y] != null) && (boardState[x - 1][y].getClass().getName().equals("Pawn")) && (boardState[x - 1][y].team)) {
                                    //if there is a white pawn to the left
                                    if (parent.getBoard()[x - 1][y] == null) {//if there was not a pawn there last board KKKKKKKKKKKKKKKKK
                                        Board found = new Board(boardState);//create duplicate board;
                                        Piece attPiece = found.getBoard()[x][y].deepCopy();//get duplicate of attacking piece
                                        found.getBoard()[x - 1][y] = null;//capture piece
                                        found.getBoard()[x - 1][y - 1] = attPiece;//move attacking piece
                                        found.getBoard()[x][y] = null;
                                        return found;//there can only be one en passant move
                                    }
                                }
                            }
                            if (x + 1 <= 7) {
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
        }
        return null;//if got here, there are no en passant moves
    }

    /**
     * Checks if there are any castling moves which can be made, returns a linked list with the Boards which have those moves, if possible, empty otherwise
     *
     * @param team the team currently moving
     * @return A linked list of boards of all possible castling moves, empty if not possible
     */
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

    /**
     * this method gets all coordinates which the enemy can reach in their next move. Useful for castling and check
     *
     * @param team the team which these threats are a threat to
     * @return A linked list of threatened coordinates
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

    /**
     * Duplicates a Board
     *
     * @param p the Piece array of the board being copied
     * @return the duplicated board
     */
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

    /**
     * returns the Piece array held by a board
     *
     * @return
     */
    public Piece[][] getBoard() {
        return boardState;
    }

    /**
     * calculates fitness of a board based on a primitive heuristic
     *
     * @return the number representing the fitness of the board
     */
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

    /**
     * Allows for moves from the player
     *
     * @param lastPieceClicked the piece most recently clicked by the player
     * @param coordinate       the place to which the player wishes to move this piece
     * @return the Board which represents this move
     */
    public Board move(Coordinate lastPieceClicked, Coordinate coordinate) {
        Piece[][] newBoard = deepCopy(boardState);
        Piece temp = newBoard[lastPieceClicked.x][lastPieceClicked.y].deepCopy();
        newBoard[lastPieceClicked.x][lastPieceClicked.y] = null;
        newBoard[coordinate.x][coordinate.y] = temp;
        return new Board(newBoard);
    }

    /**
     * prints the state of the current board to console
     */
    public void printBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (boardState[x][y] != null) {
                    if (boardState[x][y].getClass().getName().equals("Pawn")){
                        if (boardState[x][y].team){
                            System.out.print("1");
                        }
                        else{
                            System.out.print("0");
                        }
                    }
                    else {
                        System.out.print(boardState[x][y].getName());
                    }
                } else {
                    System.out.print('-');
                }
            }
            System.out.println();
        }
    }


}