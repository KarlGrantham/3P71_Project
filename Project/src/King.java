public class King extends Piece {

    public King(boolean t) {
        team = t;
        if (team){
            name = 'K';
        }
        else{
            name = 'k';
        }
    }
}
