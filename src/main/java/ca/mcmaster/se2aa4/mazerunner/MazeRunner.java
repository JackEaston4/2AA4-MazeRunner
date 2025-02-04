package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeRunner{
    
    private MazeTile[][] maze;
    private final Player player;
    private final Position finish;

    private static final Logger logger = LogManager.getLogger();

    public MazeRunner(MazeTile[][] maze, int[] entryPoint, int[] finish){
        this.maze = maze;
        this.player = new Player(new Position(entryPoint[0], entryPoint[1]));
        this.finish = new Position(finish[0], finish[1]);
    }

    public Position getPlayerPosition() {
        return player.getPosition();
    }


    public boolean isAtFinish(){
        return getPlayerPosition().getX() == finish.getX() && getPlayerPosition().getY() == finish.getY();
    }

    public boolean isWall(Position position){
        boolean status = maze[position.getX()][position.getY()] == MazeTile.WALL;
        logger.trace("in isWall: returning check status: " + status);
        return status;
    }

    
    public boolean checkForWall(Direction direction){
        Facing facing = player.getFacing();
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
        Position look_at = new Position(getPlayerPosition().getX()+check_facing_vector[0], getPlayerPosition().getY()+check_facing_vector[1]);

        logger.trace("in checkForWall: checking " + look_at + ", " + check_facing + " from " + getPlayerPosition());
        return isWall(look_at);
       
    }

    public void turnDirection(Direction direction){
        player.turn(direction);
    }


    public void movePlayer() {
        player.moveForward();
    }

}