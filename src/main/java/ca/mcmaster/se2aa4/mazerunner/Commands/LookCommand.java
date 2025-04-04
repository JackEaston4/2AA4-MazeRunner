package ca.mcmaster.se2aa4.mazerunner.Commands;

import ca.mcmaster.se2aa4.mazerunner.MazeRunner;
import ca.mcmaster.se2aa4.mazerunner.Direction;

public class LookCommand implements Command {
    private final MazeRunner runner;
    private final Direction direction;

    public LookCommand(MazeRunner runner, Direction direction) {
        this.runner = runner;
        this.direction = direction;
    }

    @Override
    public boolean execute() {
        return !runner.checkForWall(direction); // returns true if path is clear
    }
}