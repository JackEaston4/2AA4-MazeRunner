package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class MazeRunner{
    
    private int[] position = new int[2];
    private int[] finish = new int[2];

    private MazeTile[][] maze;
    private Facing facing = Facing.EAST; // starting always on W side of maze facing E

    private static final Logger logger = LogManager.getLogger();
    StringBuilder canonical_path = new StringBuilder();


    public MazeRunner(MazeTile[][] maze, int[] entryPoint, int[] finish){
        this.position = entryPoint.clone();
        this.finish = finish.clone();
        this.maze = maze;
    }

    public String MazeRunnerAlgorithm(){
        while(!isFinish(position)){
            logger.trace("\n-----------------\nLooping through MazeRunnerAlgorithm\n");
            if (!checkForWall(position, Direction.RIGHT)) {
                logger.trace("no wall to right, turning right");
                turnDirection(Direction.RIGHT);
                recordMove('R');

                moveForward(); // to prevent infinte loops from when in a '+' intersection
                recordMove('F');
            }
            else if (!checkForWall(position, Direction.FORWARD)) {
                logger.trace("wall right, no wall forward, moving forward");
                moveForward();
                recordMove('F');
            }
            else {
                logger.trace("wall right and forward, turning left");
                turnDirection(Direction.LEFT);
                recordMove('L');
            }
        }

        logger.info("Finish reached");
        logger.trace("canonical path: " + canonical_path.toString());
        return canonical_path.toString();
    }


    public boolean isFinish(int[] position){
        return Arrays.equals(position, finish);
    }

    public boolean isWall(int[] position){
        logger.trace("in isWall: checking " + position[0] + " " + position[1]);
        boolean status = maze[position[0]][position[1]] == MazeTile.WALL;

        logger.trace("in isWall: returning check status: " + status);
        return status;
    }

    
    public boolean checkForWall(int[] position, Direction direction){
        Facing check_facing;
        logger.trace("starting checkForWall");

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
        int[] look_at = {position[0] + check_facing_vector[0], position[1] + check_facing_vector[1]};

        logger.trace("in checkForWall: checking " + look_at[0] + " " + look_at[1] + ", " + check_facing + " from " + position[0] + " " + position[1]);
        return isWall(look_at);
       
    }

    public void turnDirection(Direction direction){
        if (direction == Direction.RIGHT) { // right turn (about the origin): (x,y) -> (y,-x)
            facing = facing.turnRight();
        }
        else if (direction == Direction.LEFT) { // left turn (about the origin): (x,y) -> (-y,x)
            facing = facing.turnLeft();
        }
    }


    public void moveForward() {
        int[] facing_vector = facing.getVector();
        position[0] += facing_vector[0];
        position[1] += facing_vector[1];
    }


    public void recordMove(char move){
        canonical_path.append(move);
        logger.trace("Recorded move '" + move + "' to " + Arrays.toString(position));
        
    }
}