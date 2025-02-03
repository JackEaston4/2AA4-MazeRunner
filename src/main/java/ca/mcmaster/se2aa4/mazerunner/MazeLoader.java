package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MazeLoader {

    private static final Logger logger = LogManager.getLogger();

    public MazeLoader(){
        // constructor
    }

    public MazeTile[][] loadMaze(String filepath) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            logger.info("**** Reading the maze from file " + filepath + "\n");

            String line;           
            StringBuilder logLine = new StringBuilder();


            // determine the dimensions of the maze (to create array)
            int rowcount = 0;
            int columncount = 0;
            while ((line = reader.readLine()) != null) {
                rowcount++;
                columncount = Math.max(columncount, line.length());
            }
            reader.close();  // Close after dimension calculation

            logger.trace("Number of rows in maze is: " + rowcount);
            logger.trace("Number of columns in maze is: " + columncount);

            MazeTile[][] maze = new MazeTile[rowcount][columncount];

            // populate array with maze tiles
            int row = 0;
            reader = new BufferedReader(new FileReader(filepath));
            while ((line = reader.readLine()) != null) { // while there is still things to read
                for (int index = 0; index < line.length(); index++) {
                    if (line.charAt(index) == '#') {
                        maze[row][index] = MazeTile.WALL;
                        logLine.append("WALL "); // append each tile of maze string
            
                    } else if (line.charAt(index) == ' ') {
                        maze[row][index] = MazeTile.PATH;
                        logLine.append("PASS "); // append each tile of maze to string
                    }
                }
                row++;

                logger.trace(logLine.toString()); // Log entire line at once
                logLine.setLength(0); // clear StringBuilder (for next line)
            }

            reader.close(); // close BufferedReader(FileReader)
            return maze;
        } 
        catch(Exception e) {
            logger.error("Invalid file path");
            return null;
        }

    }


    public int[][] findEntryExitPoints(MazeTile[][] maze) {
        int[] entry_point = null;
        int[] exit_point = null;

        int number_of_rows = maze.length;
        int number_of_columns = (maze[0]).length;

        for (int row = 0; row < number_of_rows; row++) {
            if (maze[row][0] == MazeTile.PATH) { // left side of maze
                entry_point = new int[] {row,0}; 
            }
            if (maze[row][number_of_columns-1] == MazeTile.PATH) { // right side of maze
                exit_point = new int[] {row,number_of_columns-1}; 
            }
        }
        int[][] points = {entry_point, exit_point};
        return points;
    }
}
