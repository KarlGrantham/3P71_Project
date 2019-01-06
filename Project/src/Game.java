import java.util.LinkedList;
import java.util.Random;

public class Game {

    private AI ai;
    private long seed;

    public Game(Board startBoard) {
        AI white = new AI(true);
        AI black = new AI(false);
        Board currBoard = startBoard;


    }

    public Game(long s) {
        seed = s;
    }

    public Board nextMove(Board b, boolean currTeam) {
        ai = new AI(currTeam);
        Board wat = ai.miniMax(b, currTeam, 0, 2);
        System.out.println("AI says");
        wat.printBoard();
        return wat;
    }

    public class AI {

        boolean team;

        public AI(boolean t) {
            team = t;
        }

        Board miniMax(Board root, boolean max, int currDepth, int maxDepth) {
            root.calculateChildren(team);
            LinkedList<Board> children = this.shuffleMoves((LinkedList<Board>) root.children.clone(), seed);
            if (children.isEmpty() || currDepth >= maxDepth) {//if there are moves that can be made
                return root;
            } else {//else, max depth reached, return root
                int maxVal = Integer.MIN_VALUE;
                int minVal = Integer.MAX_VALUE;
                Board bestBoard = children.get(0);
                if (max) {//if calculating white's turn
                    while (!children.isEmpty()) {
                        Board currBoard = miniMax(children.remove(), false, currDepth + 1, maxDepth);
                        if (currBoard.fitness > maxVal) {
                            maxVal = currBoard.fitness;
                            if (currDepth == 0) {
                                bestBoard = currBoard;
                            } else {
                                bestBoard = currBoard.parent;
                            }
                        }
                    }
                    return bestBoard;
                } else {//else, calculating black's turn
                    while (!children.isEmpty()) {
                        Board currBoard = miniMax(children.remove(), true, currDepth + 1, maxDepth);
                        if (currBoard.fitness < minVal) {
                            minVal = currBoard.fitness;
                            if (currDepth == 0) {
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