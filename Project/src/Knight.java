public class Knight extends Piece {

    public Knight(boolean t) {
        team = t;
        if (team){
            name = 'N';
        }
        else{
            name = 'n';
        }
    }

    public void moves(int x, int y) {

    }

}
