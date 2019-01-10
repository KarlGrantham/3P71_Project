import javax.swing.*;
import java.util.LinkedList;

public class Main {

    private static long seed = 234895623;
    public static void main(String args[]) {
//    Board b = new Board();
//    b.printBoard();
//    Game g = new Game(b);
//    boolean team = true;
//    Board wat = g.nextMove(b,team);
//    while (wat != null){
//        team = !team;
//        wat = g.nextMove(wat,team);
//        wat.printBoard();
//    }




        Game thisGame = new Game(seed);
        Board b = new Board();
        b.parent = new Board();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI(b, thisGame);
            }
        });
    }
}