import javax.swing.*;
import java.util.LinkedList;

public class Main {

    private static long seed = 234895623;

    public static void main(String args[]) {
        Game thisGame = new Game(seed);
        Board b = new Board();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI(b, thisGame);
            }
        });
    }
}