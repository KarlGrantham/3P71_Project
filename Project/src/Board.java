/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

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
        boardState = p;
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
        children = tempChildren;//set child list to new child list
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
