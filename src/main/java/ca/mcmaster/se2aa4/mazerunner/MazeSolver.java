package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.Commands.*;

public class MazeSolver{
    private final MazeRunner runner;
    private static final Logger logger = LogManager.getLogger();
    private final StringBuilder canonical_path = new StringBuilder();

    public MazeSolver(MazeRunner runner){
        this.runner = runner;
    }

    public String MazeRunnerAlgorithm(){
        Command moveForward = new MoveCommand(runner);
        Command turnRight = new TurnCommand(runner, Direction.RIGHT);
        Command turnLeft = new TurnCommand(runner, Direction.LEFT);
        Command lookRight = new LookCommand(runner, Direction.RIGHT);
        Command lookForward = new LookCommand(runner, Direction.FORWARD);

        while(!runner.isAtFinish()){
            logger.trace("\n-----------------\nLooping through MazeRunnerAlgorithm\n");

            if (lookRight.execute()) { // if there is no wall to the right
                logger.trace("no wall to right → turning right & moving forward");
                //turnRight.execute();
                //recordMove('R');
                executeAndRecord(turnRight);

                //moveForward.execute();
                //recordMove('F');
                executeAndRecord(moveForward);
            }

            else if (lookForward.execute()) { // if there is no wall in front
                logger.trace("wall right, no wall forward → moving forward");
                //moveForward.execute();
                //recordMove('F');
                executeAndRecord(moveForward);
            }
            else {
                logger.trace("wall right and forward → turning left");
                //turnLeft.execute();
                //recordMove('L');
                executeAndRecord(turnLeft);
            }
        }

        logger.info("Finish reached");
        logger.trace("canonical path: " + canonical_path.toString());
        return canonical_path.toString();
    }


    // Command pattern implementation
    private void executeAndRecord(Command command) {
        if (command.execute()) { // if command was successful
            char symbol = command.getSymbol();
            if (symbol != '_') { // 
                canonical_path.append(symbol);
                logger.trace("Recorded move '" + symbol + "' at " + runner.getPlayerPosition());
            }
        }
    }
}