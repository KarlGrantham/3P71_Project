import javax.swing.*;
import java.util.*;

public class Main {

    private static long seed = 123456;
    public static void main (String args []){
        //Board disBoard = new Board(0.5, seed);
        //disBoard.printBoard();
//        Board board1 = new Board();
//        Board board2 = new Board();
//        board2.deepCopy(board1.getBoard());
//        Piece curr = board2.getBoard()[0][0];
//        board2.getBoard()[4][0] = curr;
//        System.out.println("board1");
//        board1.printBoard();
//        System.out.println("board2");
//        board2.printBoard();
        Board b = new Board(1,1);
        b.printBoard();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI(b);
            }
        });
    }
}
