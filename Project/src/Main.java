package project;

import javax.swing.*;

public class Main {

    private static long seed = 123456;
    public static void main (String args []){
        Game thisGame = new Game(seed);
        Board b = new Board(1,123);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI(b, thisGame);
            }
        });
    }
}
