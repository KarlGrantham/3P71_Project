import java.util.*;

public class Board {
    private static Piece[][] boardState = new Piece[8][8];

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
        Piece curr = null;
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

    }

    public Board(Piece[][] p) {//regular board
        deepCopy(p);
    }

    public Board() {
        boolean team = true;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[i * 5 + 1][j] = new Pawn(team);
            }
            boardState[i * 7][0] = new Rook(team);
            boardState[i * 7][1] = new Knight(team);
            boardState[i * 7][2] = new Bishop(team);
            boardState[i * 7][3] = new Queen(team);
            boardState[i * 7][4] = new King(team);
            boardState[i * 7][5] = new Bishop(team);
            boardState[i * 7][6] = new Knight(team);
            boardState[i * 7][7] = new Rook(team);
            team = false;
        }
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState[j][i] != null) {
                    System.out.print(boardState[j][i].getName());
                } else {
                    System.out.print('_');
                }
            }
            System.out.println();
        }
    }

    private static void deepCopy(Piece[][] p) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[i][j] = p[i][j];
            }
        }
    }

    public Piece[][] getBoard() {
        return boardState;
    }
}
