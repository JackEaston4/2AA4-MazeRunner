package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeRunner{
    
    private int[] position = new int[2];
    private int[] finish = new int[2];

    private String[][] mazeArray;
    private int[] facing;

    public MazeRunner(String[][] mazeArray, int[] entryPoint, int[] finish){
        position = entryPoint;
        this.finish = finish;
        this.mazeArray = mazeArray;
    }

    public void MazeRunnerAlgorithm(String[][] mazeArray){
        while(!isFinish(position)){
            // do stuff
        }
    }

    public boolean isWall(int[] position){
        int row = position[0];
        int col = position[1];
        if(mazeArray[row][col].equals("#")){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isFinish(int[] position){
        if (position==finish) {
            return true;
        }
        else {
            return false;
        }
    }


    public boolean checkForWall(int[] position, char direction){
        int[] look_for_wall_at_coordinates = new int[2];

        if (direction == 'F') { // use normal facing vectors for forwards
            look_for_wall_at_coordinates[0] = position[0] += facing[0];
            look_for_wall_at_coordinates[1] = position[1] += facing[1];
        }
        else if (direction == 'R') { // right turn (about the origin): (x,y) -> (y,-x)
            look_for_wall_at_coordinates[0] = position[0] += facing[1];
            look_for_wall_at_coordinates[1] = position[1] -= facing[0];
        }
        else if (direction == 'L') { // left turn (about the origin): (x,y) -> (-y,x)
            look_for_wall_at_coordinates[0] = position[0] -= facing[1];
            look_for_wall_at_coordinates[1] = position[1] += facing[0];
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

}