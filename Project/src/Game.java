import java.util.LinkedList;

public class Game {
    public Game(Board startBoard) {
        AI white = new AI(startBoard, true);
        AI black = new AI(white.getMove(), false);

        Board blackMove = black.getMove();
        System.out.println("Fitness Here: " + blackMove.fitness);
        System.out.println("The piece here is a " + blackMove.getBoard()[3][7]);
        blackMove.calcFitness();
        System.out.println("and now: " + blackMove.fitness);
    }

    public class AI {
        Board startBoard;
        boolean team;

        public AI(Board root, boolean t) {
            team = t;
            startBoard = root;
        }

        Board getMove() {
            startBoard.calculateChildren(team);
            LinkedList<Board> children = (LinkedList<Board>) startBoard.children.clone();
            Board bestChild = children.get(0);//initialize most fit child to the first one
            int bestFitness = Integer.MIN_VALUE;//initialize best fitness value seen to minimum value
            while (!children.isEmpty()) {
                Board child = children.remove();//get the next child
                if (bestFitness < child.fitness) {//if this is the most fit child we've seen
                    bestChild = child;
                    bestFitness = child.fitness;
                }
            }
            return bestChild;
        }
    }
}
