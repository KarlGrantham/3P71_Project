import java.util.*;

public class Game {
    long seed;
    public Game(Board startBoard, long s) {
        seed = s;
        AI white = new AI(true, seed);
        AI black = new AI(false, seed);
        Board currBoard = startBoard;
        for (int i = 0; i < 25; i++) {
            currBoard = white.miniMax(currBoard, white.team, 0);
            System.out.println("White Move");
            currBoard.printBoard();
            currBoard = black.miniMax(currBoard, black.team, 0);
            System.out.println("BlackMove");
            currBoard.printBoard();
        }
        System.out.println("it works");


    }

    public class AI {
        boolean team;
        long seed;

        public AI(boolean t, long s) {
            seed = s;
            team = t;
        }

        Board miniMax(Board root, boolean max, int depth) {
            root.calculateChildren(team);
            LinkedList<Board> children = this.shuffleMoves((LinkedList<Board>) root.children.clone(), seed);
            if (children.isEmpty() || depth >= 2) {//if there are moves that can be made
                return root;
            } else {//else, max depth reached, return root
                int maxVal = Integer.MIN_VALUE;
                int minVal = Integer.MAX_VALUE;
                Board bestBoard = children.get(0);
                if (max) {//if calculating white's turn
                    while (!children.isEmpty()) {
                        Board currBoard = miniMax(children.remove(), false, depth + 1);
                        if (currBoard.fitness > maxVal) {
                            maxVal = currBoard.fitness;
                            if (depth == 0) {
                                bestBoard = currBoard;
                            } else {
                                bestBoard = currBoard.parent;
                            }
                        }
                    }
                    return bestBoard;
                } else {//else, calculating black's turn
                    while (!children.isEmpty()) {
                        Board currBoard = miniMax(children.remove(), true, depth + 1);
                        if (currBoard.fitness < minVal) {
                            minVal = currBoard.fitness;
                            if (depth == 0) {
                                bestBoard = currBoard;
                            } else {
                                bestBoard = currBoard.parent;
                            }
                        }
                    }
                    return bestBoard;
                }
            }
        }

        public LinkedList<Board> shuffleMoves(LinkedList<Board> children, long seed) {
            LinkedList<Board> newChildren = (LinkedList<Board>) children.clone();
            LinkedList<Board> copy = new LinkedList<>();
            Random randy = new Random(seed);
            while (!newChildren.isEmpty()) {
                int index = randy.nextInt(newChildren.size());
                copy.add(newChildren.remove(index));
            }
            return copy;
        }
    }
}
