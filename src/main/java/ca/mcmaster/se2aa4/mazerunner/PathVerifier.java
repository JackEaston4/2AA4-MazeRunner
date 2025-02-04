package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PathVerifier{
    
    private MazeRunner runner;

    private static final Logger logger = LogManager.getLogger();
    StringBuilder canonical_path = new StringBuilder();


    public PathVerifier(MazeRunner runner){
        this.runner = runner;
    }

    public boolean verifyPath(String path) {
        path = cleanPath(path);
        logger.trace("verifying cleaned path: " + path);

        int index = 0;
        while(index < path.length()) {
            logger.trace("looping: index is " + index);
            int repeat = 1;
            if (Character.isDigit(path.charAt(index))) {
                repeat = extractNumber(path, index); // extract the number of repeats
                index += String.valueOf(repeat).length(); // skip reading those numbers
                
                logger.trace("extracted number: repeat count is: '" + repeat + "'   index is now: " + index);
            }

            if (index >= path.length()) { 
                logger.info("Invalid path: cannot end with a number");
                return false; // path cannot end in a number
            }

            char move = path.charAt(index);


            if (!isValidInstruction(move)) {
                logger.info("Invalid path: invalid instruction");
                return false;
            }
            logger.trace("Performing action " + move + " " + repeat + " times");
            for (int i = 0; i < repeat; i++) { // perform the move 'repeat' times
                if (move == 'F') {
                    try {
                        if (runner.checkForWall(Direction.FORWARD)) {
                            logger.info("Invalid path: hit a wall");
                            return false;
                        }
                        runner.movePlayer();
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        logger.error("moveForward or checkForWall has gone out of bounds of the array");
                        return false;
                    }
                }
                else if (move == 'R') {
                    runner.turnDirection(Direction.RIGHT);
                }
                else if (move == 'L') {
                    runner.turnDirection(Direction.LEFT);
                }
            } // end of for loop

            // move has been processed fully
            index++; // move index to next char
            logger.trace("end of while loop");
        } 
        
        return runner.isAtFinish();
    }

    private String cleanPath(String path) {
        return path.replaceAll("\\s+",""); 
    }

    private boolean isValidInstruction(char move) {
        return move == 'F' || move == 'R' || move == 'L';
    }

    private int extractNumber(String path, int index) {
        int num = 0;
        while (index < path.length() && Character.isDigit(path.charAt(index))) {
            num = num * 10 + (path.charAt(index) - '0');
            index++;
        }
        return num;
    }


}