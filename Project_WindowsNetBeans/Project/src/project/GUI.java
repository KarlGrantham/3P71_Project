package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class GUI {

    Board b;
    JToolBar toolBar = new JToolBar();
    JPanel panel;
    JFrame frame;
    JButton[][] buttonList = new JButton[8][8];
    boolean currTeam = true;
    LinkedList<Coordinate> currentMoveSet = new LinkedList<>();
    Coordinate lastPieceClicked, specialMoveCoor;
    JButton nextTurn = new JButton("Next Turn");
    JButton enPassant = new JButton("enPassant");
    JButton castling = new JButton("Castle");
    JButton castleRight = new JButton("Caslte Right");
    JButton castleLeft = new JButton("Castle Left");
    JButton resetGame = new JButton("Reset Game");
    JButton close = new JButton("Close");

    // String path = "D:\\Desktop_HDD\\IMAGES\\";
    String path = "/images/";
    Game game;

    public GUI(Board b, Game game) {
        this.game = game;
        this.b = b;
        initializeBoard(true);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        toolBar.add(nextTurn);
        toolBar.add(enPassant);
        toolBar.add(castling);
        toolBar.add(castleRight);
        toolBar.add(castleLeft);
        toolBar.add(resetGame);
        toolBar.add(close);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        nextTurn.addActionListener(new NextTurn());
        nextTurn.putClientProperty("var", 0);
        enPassant.addActionListener(new NextTurn());
        enPassant.putClientProperty("var", 1);
        castling.addActionListener(new NextTurn());
        castling.putClientProperty("var", 2);
        castleRight.addActionListener(new NextTurn());
        castleRight.putClientProperty("var", 3);
        castleLeft.addActionListener(new NextTurn());
        castleLeft.putClientProperty("var", 4);
        resetGame.addActionListener(new NextTurn());
        resetGame.putClientProperty("var", 5);
        close.addActionListener(new NextTurn());
        close.putClientProperty("var", 6);
    }

    private void initializeBoard(boolean team) {
        panel = new JPanel(new GridLayout(0, 8));
        panel.setBackground(Color.WHITE);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton button = new JButton();
                if (b.getBoard()[x][y] != null && b.getBoard()[x][y].team == team) {
                    button.setIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[x][y].imageName + ".png")));
                    button.setDisabledIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[x][y].imageName + ".png")));
                } else if (b.getBoard()[x][y] != null && b.getBoard()[x][y].team != team) {
                    button.setIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[x][y].imageName + ".png")));
                    button.setDisabledIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[x][y].imageName + ".png")));
                    button.setEnabled(false);
                } else {
                    button.setEnabled(false);
                }
                if ((x % 2 != 1 && y % 2 != 1) || (x % 2 != 0 && y % 2 != 0)) {
                    button.setBackground(Color.GRAY);
                } else {
                    button.setBackground(Color.WHITE);
                }
                panel.add(button);
                button.putClientProperty("x", x);
                button.putClientProperty("y", y);
                button.addActionListener(new ButtonClicked());
                buttonList[x][y] = button;
            }
        }
        if (b.checkEnPassant(currTeam) != null) {
            enPassant.setEnabled(true);
        } else {
            enPassant.setEnabled(false);
        }
        LinkedList<Board> l = b.checkCastling(currTeam);
        if (l.isEmpty()) {
            castling.setEnabled(false);
            castleLeft.setEnabled(false);
            castleRight.setEnabled(false);
        }
        if (l.size() == 1) {
            castling.setEnabled(true);
        }
        if (l.size() == 2) {
            castleLeft.setEnabled(true);
            castleRight.setEnabled(true);
        }
    }

    private class NextTurn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Board parent = new Board(b.deepCopy(b.getBoard()));
            JButton btn = (JButton) e.getSource();
            int var = (int) btn.getClientProperty("var");
            switch (var) {
                case 0:
                    b = game.nextMove(b, currTeam);
                    break;
                case 1:
                    b = b.checkEnPassant(currTeam);
                    break;
                case 2:
                    b = b.checkCastling(currTeam).remove();
                    break;
                case 3:
                    b = b.checkCastling(currTeam).get(1);
                    break;
                case 4:
                    b = b.checkCastling(currTeam).remove();
                    break;
                case 5:
                    frame.dispose();
                    GUI gui = new GUI(new Board(), game);
                    break;
                case 6:
                    frame.dispose();
                    game.closeLog();
                    System.exit(0);
                    break;
                default:
                    break;
            }
            b.parent = parent;
            currTeam = !currTeam;
            for (int yCoor = 0; yCoor < 8; yCoor++) {
                for (int xCoor = 0; xCoor < 8; xCoor++) {
                    if (b.getBoard()[xCoor][yCoor] != null && b.getBoard()[xCoor][yCoor].team == currTeam) {
                        buttonList[xCoor][yCoor].setIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[xCoor][yCoor].imageName + ".png")));
                        buttonList[xCoor][yCoor].setDisabledIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[xCoor][yCoor].imageName + ".png")));
                        buttonList[xCoor][yCoor].setEnabled(true);
                    } else if (b.getBoard()[xCoor][yCoor] != null && b.getBoard()[xCoor][yCoor].team != currTeam) {
                        buttonList[xCoor][yCoor].setIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[xCoor][yCoor].imageName + ".png")));
                        buttonList[xCoor][yCoor].setDisabledIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[xCoor][yCoor].imageName + ".png")));
                        buttonList[xCoor][yCoor].setEnabled(false);
                    } else {
                        buttonList[xCoor][yCoor].setIcon(null);
                        buttonList[xCoor][yCoor].setEnabled(false);
                    }
                    if ((xCoor % 2 != 1 && yCoor % 2 != 1) || (xCoor % 2 != 0 && yCoor % 2 != 0)) {
                        buttonList[xCoor][yCoor].setBackground(Color.GRAY);
                    } else {
                        buttonList[xCoor][yCoor].setBackground(Color.WHITE);
                    }
                }
            }
            if (b.checkEnPassant(currTeam) != null) {
                enPassant.setEnabled(true);
            } else {
                enPassant.setEnabled(false);
            }
            LinkedList<Board> l = b.checkCastling(currTeam);
            if (l.isEmpty()) {
                castling.setEnabled(false);
                castleLeft.setEnabled(false);
                castleRight.setEnabled(false);
            }
            if (l.size() == 1) {
                castling.setEnabled(true);
            }
            if (l.size() == 2) {
                castleLeft.setEnabled(true);
                castleRight.setEnabled(true);
            }
        }
    }

    private class ButtonClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            int x = (int) btn.getClientProperty("x");
            int y = (int) btn.getClientProperty("y");
            boolean moveSelected = false;

            if (b.checkEnPassant(currTeam) != null) {
                enPassant.setEnabled(true);
            } else {
                enPassant.setEnabled(false);
            }
            LinkedList<Board> l = b.checkCastling(currTeam);
            if (l.isEmpty()) {
                castling.setEnabled(false);
                castleLeft.setEnabled(false);
                castleRight.setEnabled(false);
            }
            if (l.size() == 1) {
                castling.setEnabled(true);
            }
            if (l.size() == 2) {
                castleLeft.setEnabled(true);
                castleRight.setEnabled(true);
            }

            for (int i = 0; i < currentMoveSet.size(); i++) {
                if (currentMoveSet.get(i).x == x && currentMoveSet.get(i).y == y) {
                    moveSelected = true;
                    break;
                } else {
                    moveSelected = false;
                }
            }
            if (moveSelected) {// if the button clicked was a possible move
                currTeam = !currTeam;
                if (b.checkEnPassant(currTeam) != null) {
                    enPassant.setEnabled(true);
                } else {
                    enPassant.setEnabled(false);
                }
                LinkedList<Board> h = b.checkCastling(currTeam);
                if (h.isEmpty()) {
                    castling.setEnabled(false);
                    castleLeft.setEnabled(false);
                    castleRight.setEnabled(false);
                }
                if (h.size() == 1) {
                    castling.setEnabled(true);
                }
                if (h.size() == 2) {
                    castleLeft.setEnabled(true);
                    castleRight.setEnabled(true);
                }
                Board parent = new Board(b.deepCopy(b.getBoard()));
                parent.parent = b.parent;
                b = b.move(lastPieceClicked, new Coordinate(x, y));
                b.parent = parent;
                if (b.getBoard()[x][y] != null && b.getBoard()[x][y].getClass().getName().equals("project.Pawn")) {
                    int yCoor = !currTeam ? 7 : 0;
                    if (y == yCoor) {
                        String[] options = new String[]{"Queen", "Bishop", "Knight", "Rook"};
                        int selection = JOptionPane.showOptionDialog(null, "Promote Pawn", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                        switch (selection) {
                            case 0:
                                b.getBoard()[x][y] = new Queen(!currTeam);
                                break;
                            case 1:
                                b.getBoard()[x][y] = new Bishop(!currTeam);
                                break;
                            case 2:
                                b.getBoard()[x][y] = new Knight(!currTeam);
                                break;
                            case 3:
                                b.getBoard()[x][y] = new Rook(!currTeam);
                                break;
                        }

                    }
                }
                for (int yCoor = 0; yCoor < 8; yCoor++) {
                    for (int xCoor = 0; xCoor < 8; xCoor++) {
                        if (b.getBoard()[xCoor][yCoor] != null && b.getBoard()[xCoor][yCoor].team == currTeam) {
                            buttonList[xCoor][yCoor].setIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[xCoor][yCoor].imageName + ".png")));
                            buttonList[xCoor][yCoor].setDisabledIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[xCoor][yCoor].imageName + ".png")));
                            buttonList[xCoor][yCoor].setEnabled(true);
                        } else if (b.getBoard()[xCoor][yCoor] != null && b.getBoard()[xCoor][yCoor].team != currTeam) {
                            buttonList[xCoor][yCoor].setIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[xCoor][yCoor].imageName + ".png")));
                            buttonList[xCoor][yCoor].setDisabledIcon(new ImageIcon(getClass().getResource(path + b.getBoard()[xCoor][yCoor].imageName + ".png")));
                            buttonList[xCoor][yCoor].setEnabled(false);
                        } else {
                            buttonList[xCoor][yCoor].setIcon(null);
                            buttonList[xCoor][yCoor].setEnabled(false);
                        }
                        if ((xCoor % 2 != 1 && yCoor % 2 != 1) || (xCoor % 2 != 0 && yCoor % 2 != 0)) {
                            buttonList[xCoor][yCoor].setBackground(Color.GRAY);
                        } else {
                            buttonList[xCoor][yCoor].setBackground(Color.WHITE);
                        }
                    }
                }
                currentMoveSet.clear();
                game.writeLog(b.parent, b, !currTeam);
                if (b.checkCheckmate(currTeam)) {
                    String team = !currTeam ? "White" : "Black";
                    JOptionPane.showMessageDialog(new JFrame(),"Checkmate, " + team + " wins!");
                    game.closeLogCheckMate(team);
                    frame.dispose();
                    System.exit(0);
                }
            } else { // button clicked was a piece
                btn.setBackground(Color.RED);
                lastPieceClicked = new Coordinate(x, y);
                for (int yCoor = 0; yCoor < 8; yCoor++) {
                    for (int xCoor = 0; xCoor < 8; xCoor++) {
                        if (b.getBoard()[xCoor][yCoor] != null) {
                            if (b.getBoard()[xCoor][yCoor].team == currTeam) {
                                buttonList[xCoor][yCoor].setEnabled(true);
                            } else if (b.getBoard()[xCoor][yCoor].team != currTeam) {
                                buttonList[xCoor][yCoor].setEnabled(false);
                            }
                        } else {
                            buttonList[xCoor][yCoor].setEnabled(false);
                        }
                        if ((xCoor % 2 != 1 && yCoor % 2 != 1) || (xCoor % 2 != 0 && yCoor % 2 != 0)) {
                            buttonList[xCoor][yCoor].setBackground(Color.GRAY);
                        } else {
                            buttonList[xCoor][yCoor].setBackground(Color.WHITE);
                        }
                    }
                }
                LinkedList<Coordinate> moves = b.getBoard()[x][y].moves(x, y, b.getBoard());
                LinkedList<Coordinate> possibleMoves = new LinkedList<>();
                moves.stream().filter((coor) -> (!b.move(lastPieceClicked, coor).checkCheck(currTeam))).forEachOrdered((coor) -> {
                    possibleMoves.add(coor);
                });
                while (!possibleMoves.isEmpty()) {
                    currentMoveSet.add(possibleMoves.get(0));
                    buttonList[possibleMoves.get(0).x][possibleMoves.get(0).y].setBackground(Color.BLUE);
                    buttonList[possibleMoves.get(0).x][possibleMoves.get(0).y].setEnabled(true);
                    possibleMoves.remove(0);
                }

            }

        }
    }

    public JComponent getPanel() {
        return panel;
    }

}
