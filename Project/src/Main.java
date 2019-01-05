package project;

import javax.swing.*;
import java.util.*;

public class Main {

    private static long seed = 123456;
    public static void main (String args []){
        Board thisBoard = new Board();
        //Game thisGame = new Game(thisBoard);
        Board b = new Board();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI(b);
            }
        });
    }
}
