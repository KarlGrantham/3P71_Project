
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {

    Board b;
    JPanel panel;
    JFrame frame;
    JButton[][] buttonList = new JButton[8][8];
    boolean currTeam = true;
    LinkedList<Coordinate> currentMoveSet = new LinkedList<>();
    Coordinate lastPieceClicked;

    public GUI(Board b) {
        this.b = b;
        initializeBoard(true);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeBoard(boolean team) {
        panel = new JPanel(new GridLayout(0, 8));
        panel.setBackground(Color.WHITE);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton button = new JButton();
                String path = "C:\\IMAGES\\";
                if (b.getBoard()[x][y] != null && b.getBoard()[x][y].team == team) {
                    button.setIcon(new ImageIcon(path + b.getBoard()[x][y].imageName + ".png"));
                    button.setDisabledIcon(new ImageIcon(path + b.getBoard()[x][y].imageName + ".png"));
                } else if (b.getBoard()[x][y] != null && b.getBoard()[x][y].team != team) {
                    button.setIcon(new ImageIcon(path + b.getBoard()[x][y].imageName + ".png"));
                    button.setDisabledIcon(new ImageIcon(path + b.getBoard()[x][y].imageName + ".png"));
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
                button.addActionListener(
                        new ButtonClicked()
                );
                buttonList[x][y] = button;
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
                b = b.move(lastPieceClicked, new Coordinate(x,y));
                String path = "C:\\IMAGES\\";
                for (int yCoor = 0; yCoor < 8; yCoor++) {
                    for (int xCoor = 0; xCoor < 8; xCoor++) {
                        if (b.getBoard()[xCoor][yCoor] != null && b.getBoard()[xCoor][yCoor].team == currTeam) {
                            buttonList[xCoor][yCoor].setIcon(new ImageIcon(path + b.getBoard()[xCoor][yCoor].imageName + ".png"));
                            buttonList[xCoor][yCoor].setDisabledIcon(new ImageIcon(path + b.getBoard()[xCoor][yCoor].imageName + ".png"));
                            buttonList[xCoor][yCoor].setEnabled(true);
                        } else if (b.getBoard()[xCoor][yCoor] != null && b.getBoard()[xCoor][yCoor].team != currTeam) {
                            buttonList[xCoor][yCoor].setIcon(new ImageIcon(path + b.getBoard()[xCoor][yCoor].imageName + ".png"));
                            buttonList[xCoor][yCoor].setDisabledIcon(new ImageIcon(path + b.getBoard()[xCoor][yCoor].imageName + ".png"));
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
            } else { // button clicked was a piece
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
                LinkedList<Coordinate> possibleMoves = b.getBoard()[x][y].moves(x, y, b.getBoard());
                btn.setBackground(Color.RED);
                while (!possibleMoves.isEmpty()) {
                    currentMoveSet.add(possibleMoves.get(0));
                    //System.out.println(possibleMoves.get(0).x + " " + possibleMoves.get(0).y);
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