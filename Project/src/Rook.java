public class Rook extends Piece {

    public Rook(boolean t) {
        team = t;
        if (team){
            name = 'R';
        }
        else{
            name = 'r';
        }
    }
}
