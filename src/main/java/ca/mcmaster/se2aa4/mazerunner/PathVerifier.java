package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.Commands.*;

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

        // creating commands
        Command moveForward = new MoveCommand(runner);
        Command turnRight = new TurnCommand(runner, Direction.RIGHT);
        Command turnLeft = new TurnCommand(runner, Direction.LEFT);
        Command lookForward = new LookCommand(runner, Direction.FORWARD);

        int index = 0;
        while(index < path.length()) {
            logger.trace("looping: index is " + index);

            int repeat = 1; // repeat defaults to 1 (if no number is given)
            if (Character.isDigit(path.charAt(index))) {
                repeat = extractNumber(path, index); // extract the number (may more than 1 digit)
                index += String.valueOf(repeat).length(); // increase index (to stay in sync) if multiple digits were read
                
                logger.trace("extracted number: repeat count is: '" + repeat + "'   index is now: " + index);
            }

            if (index >= path.length()) {  // checking that the path does not end in a number
                logger.info("Invalid path: cannot end with a number");
                return false;
            }

            
            // now process the next move character
            char move = path.charAt(index);

            if (!isValidInstruction(move)) {
                logger.info("Invalid path: invalid instruction");
                return false;
            }

            logger.trace("Performing action " + move + " " + repeat + " times"); // repeat defaults to 1 each time through the loop
            for (int i = 0; i < repeat; i++) { // perform the move 'repeat' times
                if (move == 'F') {
                    try {
                        if (!lookForward.execute()) { // if there is not a path forwards
                            logger.info("Invalid path: hit a wall");
                            return false;
                        }
                        moveForward.execute(); // move forward
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        logger.error("moveForward or checkForWall has gone out of bounds of the array");
                        return false;
                    }
                } // end of if move == 'F'

                else if (move == 'R') {
                    turnRight.execute(); // turn right
                }

                else if (move == 'L') {
                    turnLeft.execute(); // turn right
                }

            } // end of action-repetition for loop

            // move has been processed fully
            index++;
            logger.trace("end of while loop execution: index is now " + index + " - looping...");
        } 
        
        // at the end of the inputted path exection, return if the player is at the finish
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