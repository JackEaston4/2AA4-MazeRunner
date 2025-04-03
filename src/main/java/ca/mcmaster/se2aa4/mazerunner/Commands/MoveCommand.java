package ca.mcmaster.se2aa4.mazerunner.Commands;

import ca.mcmaster.se2aa4.mazerunner.MazeRunner;

public class MoveCommand implements Command {
    private final MazeRunner runner;

    public MoveCommand(MazeRunner runner) {
        this.runner = runner;
    }

    @Override
    public boolean execute() {
        runner.movePlayer();
        return true;
    }

    // to be replaced with observer
    @Override
    public char getSymbol() {
        return 'F';
    }
}
