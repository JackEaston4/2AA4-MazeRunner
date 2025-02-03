package ca.mcmaster.se2aa4.mazerunner;

public enum Facing {
    NORTH(0, -1), 
    EAST(1, 0), 
    SOUTH(0, 1), 
    WEST(-1, 0);

    private final int dx, dy;

    Facing(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int[] getVector() {
        return new int[]{dx, dy};
    }


    public Facing turnRight() {
        return values()[(this.ordinal() + 1) % 4]; // N -> E -> S -> W -> N
    }

    public Facing turnLeft() {
        return values()[(this.ordinal() + 3) % 4]; // N -> W -> S -> E -> N
    }
}