package ca.mcmaster.se2aa4.mazerunner;

public class Position {
    private final int x, y;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Polymorphism for the win!
    public Position(int[] position) {
        this.x = position[0];
        this.y = position[1];
    }
    
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position other = (Position) o;
        return this.x == other.x && this.y == other.y;
    }
}
