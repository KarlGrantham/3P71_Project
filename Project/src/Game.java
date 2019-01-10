import java.util.LinkedList;
import java.util.Random;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * Chess notation taken from: https://en.wikipedia.org/wiki/Portable_Game_Notation
 */
public class Game {

    private AI ai;
    private long seed;
    private File outFile;
    private FileWriter filewriter;
    private BufferedWriter writer;
    private char[] numLetters;
    private int turnNum;

    public Game(Board startBoard) {
        numLetters = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        turnNum = 0;
        try {
            outFile = new File("logs.txt");
            outFile.createNewFile();
            filewriter = new FileWriter(outFile);
            writer = new BufferedWriter(filewriter);

            if (writer != null) {
                writer.write("[Event Brock University Finals]");
                writer.newLine();
                writer.write("[Site St. Catharine's, Ontario, CAN]");
                writer.newLine();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                String dateString = ("[Date " + date + "]");
                writer.write(dateString);
                writer.newLine();
                writer.write("[White Doe, John]");
                writer.newLine();
                writer.write("[Black Doe, Jane]");
                writer.newLine();

            }


        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("something messed up");
            System.exit(1);
        }
    }


    public void writeLog(Board parent, Board curr, boolean team) {
        turnNum++;
        try {
            if (parent.checkEnPassant(team) != null && parent.checkBoardEquality(parent.checkEnPassant(team).getBoard(), curr.getBoard())) {//if an en passant move was made
                if (writer != null) {
                    writer.write(turnNum);
                    if (team) {
                        writer.write(". White: captures en passant");
                        writer.newLine();
                    } else {
                        writer.write(". Black: captures en passant");
                        writer.newLine();
                    }
                }
            } else {
                Coordinate[] move = getLastMove(parent, curr, team);
                System.out.println(move[1].y);
                String moveString = (numLetters[move[0].x] + ", " + move[0].y + " to " + numLetters[move[1].x] + ", " + move[1].y);
                if (writer != null) {
                    writer.write(turnNum);
                    if (team) {
                        writer.write(". White: ");
                    } else {
                        writer.write(". Black: ");
                    }
                    writer.write(moveString);
                    writer.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("something messed up");
            System.exit(1);
        }
    }

    public Coordinate[] getLastMove(Board parent, Board child, boolean team) {
        Coordinate[] move = new Coordinate[2];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (parent.getBoard()[x][y] != null && child.getBoard()[x][y] == null) {//the place from which a piece moved
                    move[0] = new Coordinate(x, y);
                }
                if (parent.getBoard()[x][y] == null && child.getBoard()[x][y] != null) {//a movement
                    move[1] = new Coordinate(x, y);
                }
                if ((child.getBoard()[x][y] != null) && (parent.getBoard()[x][y] != null)) {//a capture
                    if (child.getBoard()[x][y].getTeam() != parent.getBoard()[x][y].getTeam()) {
                        move[1] = new Coordinate(x, y);
                    }
                }
            }
        }
        return move;
    }

    public Game(long s) {
        seed = s;
    }

    public Board nextMove(Board b, boolean currTeam) {
        b.calculateChildren(currTeam);
        if (b.children.isEmpty()) {//if this team is unable to move
            try {
                if (b.checkStalemate()) {//if the current board is a stalemate
                    if (writer != null) {
                        writer.write("Stalemate!");
                        writer.close();
                    }
                } else {//else, its not a stalemate
                    if (currTeam) {//if AI is to play white
                        b.whiteCheck = b.checkCheck(true);
                        if (b.whiteCheck) {//checkmate, black wins
                            if (writer != null) {
                                writer.write("Checkmate! Black Wins");
                                writer.close();
                            }
                        }
                    } else {//else, AI is to play black
                        b.blackCheck = b.checkCheck(false);
                        if (b.blackCheck) {//checkmate, white wins
                            if (writer != null) {
                                writer.write("Checkmate! White Wins");
                                writer.close();
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                System.exit(1);
            } catch (IOException e) {
                System.out.println("something messed up");
                System.exit(1);
            }
            return null;
        } else {
            Board curr = b;
            ai = new AI(currTeam);
            //Board aiMove = ai.miniMax(b, currTeam, 0, 2);
            Board aiMove = ai.alphaBeta(b, currTeam, 0, 4, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (aiMove.children.isEmpty()) {//if this team is unable to move
            try {
                if (aiMove.checkStalemate()) {//if the current board is a stalemate
                    if (writer != null) {
                        writer.write("Stalemate!");
                        writer.close();
                    }
                } else {//else, its not a stalemate
                    if (!currTeam) {//if AI is to play white
                        aiMove.whiteCheck = b.checkCheck(true);
                        if (aiMove.whiteCheck) {//checkmate, black wins
                            if (writer != null) {
                                writer.write("Checkmate! Black Wins");
                                writer.close();
                            }
                        }
                    } else {//else, AI is to play black
                        aiMove.blackCheck = b.checkCheck(false);
                        if (aiMove.blackCheck) {//checkmate, white wins
                            if (writer != null) {
                                writer.write("Checkmate! White Wins");
                                writer.close();
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                System.exit(1);
            } catch (IOException e) {
                System.out.println("something messed up");
                System.exit(1);
            }
            return null;
        }
        else {
                writeLog(curr, aiMove, currTeam);
                return aiMove;
            }
        }
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

        Board alphaBeta(Board root, boolean max, int currDepth, int maxDepth, int alpha, int beta) {
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
                        Board currBoard = alphaBeta(children.remove(), false, currDepth + 1, maxDepth, alpha, beta);
                        if (currBoard.fitness > maxVal) {
                            maxVal = currBoard.fitness;
                            if (currDepth == 0) {
                                bestBoard = currBoard;
                            } else {
                                bestBoard = currBoard.parent;
                            }
                        }
                        alpha = Math.max(alpha, maxVal);
                        if (alpha >= beta) {
                            break;
                        }
                    }
                    return bestBoard;
                } else {//else, calculating black's turn
                    while (!children.isEmpty()) {
                        Board currBoard = alphaBeta(children.remove(), true, currDepth + 1, maxDepth, alpha, beta);
                        if (currBoard == null) {
                        }
                        if (currBoard.fitness < minVal) {
                            minVal = currBoard.fitness;
                            if (currDepth == 0) {
                                bestBoard = currBoard;
                            } else {
                                bestBoard = currBoard.parent;
                            }
                        }
                        beta = Math.min(beta, minVal);
                        if (beta <= alpha) {
                            break;
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