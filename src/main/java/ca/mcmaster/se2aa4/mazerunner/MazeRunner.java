package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class MazeRunner{
    
    private int[] position = new int[2];
    private int[] finish = new int[2];

    private int[][] maze;
    private int[] facing = {0,1}; // orginal entry point East side of maze facing West

    private static final Logger logger = LogManager.getLogger();
    StringBuilder canonical_path = new StringBuilder();


    public MazeRunner(int[][] maze, int[] entryPoint, int[] finish){
        position = entryPoint;
        this.finish = finish;
        this.maze = maze;
    }

    public void MazeRunnerAlgorithm(){
        while(!isFinish(position)){
            moveForward();

            recordMove('F');
        }
    }


    public boolean isFinish(int[] position){
        return Arrays.equals(position, finish);
    }

    public boolean isWall(int[] position){
        return maze[position[0]][position[1]] == 1;
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


    public void moveForward() {
        position[0] += facing[0];
        position[1] += facing[1];
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

    
    public void recordMove(char move){
        canonical_path.append(move);
        logger.trace("Recorded move '" + move + "' from " + Arrays.toString(position));
        
    }
}