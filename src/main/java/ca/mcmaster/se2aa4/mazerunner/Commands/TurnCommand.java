package ca.mcmaster.se2aa4.mazerunner.Commands;

import ca.mcmaster.se2aa4.mazerunner.MazeRunner;
import ca.mcmaster.se2aa4.mazerunner.Direction;

public class TurnCommand implements Command {
    private final MazeRunner runner;
    private final Direction direction;

    public TurnCommand(MazeRunner runner, Direction direction) {
        this.runner = runner;
        this.direction = direction;
    }

    @Override
    public boolean execute() {
        runner.turnDirection(direction);
        return true;
    }
}