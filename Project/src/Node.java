import java.util.LinkedList;


public class Node {
    Board state;
    int fitness;
    LinkedList<Node> children;
    Node parent;//the previous board state
    Coordinate[] lastMove;//the beginning and end Coordinates for the previous move (for en passant)
    boolean whiteCheck;//true if white is in check, false otherwise
    boolean blackCheck;//true if black is in check, false otherwise


    public Node(Board s, Node p, boolean whtCheck, boolean blkCheck) {
        state = s;
        parent = p;
        whiteCheck = whtCheck;
        blackCheck = blkCheck;

    }
}
