package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class MazeRunner{
    
    private int[] position = new int[2];
    private int[] finish = new int[2];

    private MazeTile[][] maze;
    private int[] facing = {1,0}; // starting always on E side of maze facing W

    private static final Logger logger = LogManager.getLogger();
    StringBuilder canonical_path = new StringBuilder();


    public MazeRunner(MazeTile[][] maze, int[] entryPoint, int[] finish){
        this.position = entryPoint.clone();
        this.finish = finish.clone();
        this.maze = maze;
    }

    public String MazeRunnerAlgorithm(){
        while(!isFinish(position)){
            if (!checkForWall(position, 'R')) {
                turnDirection('R');
                moveForward();

                recordMove('R');
                recordMove('F');
            }
            else if (!checkForWall(position, 'F')) {
                moveForward();
                recordMove('F');
            }
            else {
                turnDirection('L');
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
        return maze[position[0]][position[1]] == MazeTile.WALL;
    }

    
    public boolean checkForWall(int[] position, char direction){
        int[] look_for_wall_at_coordinates = new int[2];

        if (direction == 'F') { // use normal facing vectors for forwards
            look_for_wall_at_coordinates[0] = position[0] + facing[0];
            look_for_wall_at_coordinates[1] = position[1] + facing[1];
        }
        else if (direction == 'R') { // right turn (about the origin): (x,y) -> (y,-x)
            look_for_wall_at_coordinates[0] = position[0] + facing[1];
            look_for_wall_at_coordinates[1] = position[1] - facing[0];
        }
        else if (direction == 'L') { // left turn (about the origin): (x,y) -> (-y,x)
            look_for_wall_at_coordinates[0] = position[0] - facing[1];
            look_for_wall_at_coordinates[1] = position[1] + facing[0];
        }
        else {
            throw new IllegalArgumentException("Must specify 'F' 'R' or 'L' for direction");
        }

        if (isWall(look_for_wall_at_coordinates)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void turnDirection(char direction){
        if (direction == 'R') { // right turn (about the origin): (x,y) -> (y,-x)
            int temp = facing[0];
            facing[0] = facing[1];
            facing[1] = -temp;
        }
        else if (direction == 'L') { // left turn (about the origin): (x,y) -> (-y,x)
            int temp = facing[0];
            facing[0] = -facing[1];
            facing[1] = temp;
        }
    }


    public void moveForward() {
        position[0] += facing[0];
        position[1] += facing[1];
    }


    public void recordMove(char move){
        canonical_path.append(move);
        logger.trace("Recorded move '" + move + "' to " + Arrays.toString(position));
        
    }
}