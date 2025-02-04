package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

    private Position position;
    private Facing facing;

    private static final Logger logger = LogManager.getLogger();

    public Player(Position startPosition){
        this.position = startPosition;
        this.facing = Facing.EAST; // default
    }

    public Position getPosition() {
        return position;
    }

    public Facing getFacing() {
        return facing;
    }

    public void turn(Direction direction){
        if (direction == Direction.RIGHT) { // right turn (about the origin): (x,y) -> (y,-x)
            facing = facing.turnRight();
        }
        else if (direction == Direction.LEFT) { // left turn (about the origin): (x,y) -> (-y,x)
            facing = facing.turnLeft();
        }
        logger.trace("Turned " + direction + ", now facing " + facing);
    }

    public void moveForward() {
        int[] facing_vector = facing.getVector();
        position = new Position(position.getX() + facing_vector[0], position.getY() + facing_vector[1]);
        logger.trace("Moved forward to " + position);
    }
}
