import java.util.*;

public class Board {
    private static Piece[][] boardState = new Piece[8][8];

    public Board(double rate, long seed) {//board randomizer
        Random randy = new Random(seed);
        LinkedList<Piece> remainingPieces = new LinkedList<>();
        boolean team = true;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 8; i++) {
                remainingPieces.add(new Pawn(team));
            }
            for (int i = 0; i < 2; i++) {
                remainingPieces.add(new Knight(team));
                remainingPieces.add(new Bishop(team));
                remainingPieces.add(new Rook(team));
            }
            remainingPieces.add(new Queen(team));
            remainingPieces.add(new King(team));
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
                boardState[j][i * 5 + 1] = new Pawn(team);
            }
            boardState[0][i*7] = new Rook(team);
            boardState[0][i*7] = new Rook(team);

            team = false;
        }
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState[i][j] != null) {
                    System.out.print(boardState[i][j].getName());
                } else {
                    System.out.print('╳');
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
