package project;

public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int XPos, int YPos) {
        x = XPos;
        y = YPos;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Coordinate c = (Coordinate) obj;
        return x == c.x && y == c.y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.x;
        hash = 23 * hash + this.y;
        return hash;
    }

}
