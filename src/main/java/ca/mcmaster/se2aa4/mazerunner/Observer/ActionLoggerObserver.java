package ca.mcmaster.se2aa4.mazerunner.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionLoggerObserver extends Observer {

    private static final Logger logger = LogManager.getLogger();
    private final StringBuilder path = new StringBuilder();

    public ActionLoggerObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update(char c) {
        path.append(c);
        logger.info("Action logged: " + c + " → Current path: " + path);
    }
    
    public String getPath() {
        return path.toString();
    }

}