package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeRunner{
    
    private Position position;
    private final Position finish;

    private MazeTile[][] maze;
    private Facing facing = Facing.EAST; // starting always on W side of maze facing E

    private static final Logger logger = LogManager.getLogger();

    public MazeRunner(MazeTile[][] maze, int[] entryPoint, int[] finish){
        this.position = new Position(entryPoint[0], entryPoint[1]);
        this.finish = new Position(finish[0], finish[1]);
        this.maze = maze;
    }

    public Position getPosition() {
        return position;
    }


    public boolean isAtFinish(){
        return position.getX() == finish.getX() && position.getY() == finish.getY();
    }

    public boolean isWall(Position position){
        boolean status = maze[position.getX()][position.getY()] == MazeTile.WALL;

        logger.trace("in isWall: returning check status: " + status);
        return status;
    }

    
    public boolean checkForWall(Direction direction){
        Facing check_facing;

        if (direction == Direction.FORWARD) { // use normal facing vectors for forwards
            check_facing = facing;
        }
        else if (direction == Direction.RIGHT) {
            check_facing = facing.turnRight(); // use vector that is right turn from current facing
        }
        else if (direction == Direction.LEFT) { // use vector that is left turn from current facing
            check_facing = facing.turnLeft();
        }
        else {
            logger.trace("throwing error for invalid direction in checkWall");
            throw new IllegalArgumentException("Must specify 'F' 'R' or 'L' for direction");
        }

        int[] check_facing_vector = check_facing.getVector();
        Position look_at = new Position(position.getX()+check_facing_vector[0], position.getY()+check_facing_vector[1]);

        logger.trace("in checkForWall: checking " + look_at + ", " + check_facing + " from " + position);
        return isWall(look_at);
       
    }

    public void turnDirection(Direction direction){
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
    }

}