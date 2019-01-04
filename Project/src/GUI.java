

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

    public GUI(Board b) {

        this.b = b;
        initializeBoard();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeBoard() {
        panel = new JPanel(new GridLayout(0, 8));
        panel.setBackground(Color.WHITE);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton button = new JButton();
                String path = "C:\\Users\\karlg\\Desktop\\IMAGES\\";
                if (b.getBoard()[x][y] != null && b.getBoard()[x][y].team == true) {
                    button.setIcon(new ImageIcon(path + b.getBoard()[x][y].imageName + ".png"));
                    button.setDisabledIcon(new ImageIcon(path + b.getBoard()[x][y].imageName + ".png"));

                } else if (b.getBoard()[x][y] != null && b.getBoard()[x][y].team == false) {
                    button.setIcon(new ImageIcon(path + b.getBoard()[x][y].imageName + ".png"));
                    button.setDisabledIcon(new ImageIcon(path + b.getBoard()[x][y].imageName + ".png"));
                    //button.setEnabled(false);
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
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    if ((x % 2 != 1 && y % 2 != 1) || (x % 2 != 0 && y % 2 != 0)) {
                        buttonList[x][y].setBackground(Color.GRAY);
                    } else {
                        buttonList[x][y].setBackground(Color.WHITE);
                    }
                }
            }
            int x = (int) btn.getClientProperty("x");
            int y = (int) btn.getClientProperty("y");
            System.out.println(x + " " + y);
            LinkedList<Coordinate> possibleMoves = b.getBoard()[x][y].moves(x, y, b.getBoard());
            btn.setBackground(Color.RED);
            System.out.println(b.getBoard()[x][y].getClass().getName());
            while (!possibleMoves.isEmpty()) {
                System.out.println(possibleMoves.get(0).x + " " + possibleMoves.get(0).y);

                buttonList[possibleMoves.get(0).x][possibleMoves.get(0).y].setBackground(Color.BLUE);
                buttonList[possibleMoves.get(0).x][possibleMoves.get(0).y].setEnabled(true);
                possibleMoves.remove(0);
            }
            
        }
    }

    public JComponent getPanel() {
        return panel;
    }

}
