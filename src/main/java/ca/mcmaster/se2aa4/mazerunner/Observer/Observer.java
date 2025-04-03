package ca.mcmaster.se2aa4.mazerunner.Observer;

public abstract class Observer {
    protected Subject subject;

    public abstract void update(char c);
}
