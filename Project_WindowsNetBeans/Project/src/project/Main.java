package project;

import javax.swing.*;

public class Main {

    private static long seed = 132;
    public static void main (String args []){
        Game thisGame = new Game(seed);
        Board b = new Board();
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI(b, thisGame);
        });
    }
}
