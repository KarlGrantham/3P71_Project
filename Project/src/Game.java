package project;
import java.util.LinkedList;

public class Game {
    
    private AI ai;
    
    public Game(Board startBoard) {
        AI white = new AI(true);
        AI black = new AI(false);
        Board currBoard = startBoard;
        
        for(int i =0; i<3; i++) {
            currBoard = white.miniMax(currBoard, white.team, 0);
            System.out.println("White Move");
            currBoard.printBoard();
            currBoard = black.miniMax(currBoard, black.team, 0);
            System.out.println("BlackMove");
            currBoard.printBoard();
        }
    }
    
    public Game(boolean currTeam) {
        ai = new AI(currTeam);
    }
    
    public Board nextMove(Board b){
        return ai.miniMax(b, ai.team, 2);
    }

    public class AI {
        boolean team;

        public AI(boolean t) {
            team = t;
        }

        Board miniMax(Board root, boolean max, int depth) {
            root.calculateChildren(team);
            LinkedList<Board> children = (LinkedList<Board>) root.children.clone();
            if (!children.isEmpty()) {//if there are moves that can be made
                if (depth < 3) {//if we are not at max depth yet
                    int maxVal = Integer.MIN_VALUE;
                    int minVal = Integer.MAX_VALUE;
                    Board bestBoard = children.get(0);
                    if (max) {//if calculating white's turn
                        while (!children.isEmpty()) {
                            Board currBoard = miniMax(children.remove(), false, depth + 1);
                            if (currBoard.fitness > maxVal) {
                                maxVal = currBoard.fitness;
                                bestBoard = currBoard;
                            }
                        }
                        return bestBoard;
                    } else {//else, calculating black's turn
                        while (!children.isEmpty()) {
                            Board currBoard = miniMax(children.remove(), true, depth + 1);
                            if (currBoard.fitness < minVal) {
                                minVal = currBoard.fitness;
                                bestBoard = currBoard;
                            }
                        }
                        return bestBoard;
                    }

                } else {//else, max depth reached, return root
                    return root;
                }
            } else {//else, there are no more moves that can be made, return root
                return root;
            }
        }
    }
}