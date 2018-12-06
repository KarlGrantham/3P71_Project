public class Pawn extends Piece {

    public Pawn(boolean t) {
        team = t;
        if (team){
            name = 'P';
        }
        else{
            name = 'p';
        }
    }
    public void moves(int x, int y, Piece[][] board){

    }
}