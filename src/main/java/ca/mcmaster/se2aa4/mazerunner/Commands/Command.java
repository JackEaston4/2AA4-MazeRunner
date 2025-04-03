package ca.mcmaster.se2aa4.mazerunner.Commands;

public interface Command {
    boolean execute(); // returns true if action was successful or condition is met

    // to be replaced by observer
    char getSymbol();  // returns 'F', 'L', 'R', or '_' (for non-recorded commands like look)
}
