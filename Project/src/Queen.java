public class Queen extends Piece {

    public Queen(boolean t) {
        team = t;
        if (team){
            name = 'Q';
        }
        else{
            name = 'q';
        }
    }
}
