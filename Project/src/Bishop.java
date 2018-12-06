public class Bishop extends Piece {

    public Bishop(boolean t) {
        team = t;
        if (team){
            name = 'B';
        }
        else{
            name = 'b';
        }
    }
}
