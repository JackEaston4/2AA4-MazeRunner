package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.Observer.ActionLoggerObserver;

import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");


        Options options = new Options();
        options.addOption("i", true, "input filepath of maze text file");
        options.addOption(Option.builder("p").hasArgs().valueSeparator(' ').desc("path to be verified by path validation algorithm").build());

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args); // parse the command line arguments

            if (cmd.hasOption("i")) {
                String filepath = cmd.getOptionValue("i");

                MazeLoader mazeLoader = new MazeLoader();
                MazeTile[][] maze = mazeLoader.loadMaze(filepath);
                int[][] entry_exit_points = mazeLoader.findEntryExitPoints(maze);
                
                logger.info("*** Creating MazeRunner");
                Player player = new Player(new Position(entry_exit_points[0][0], entry_exit_points[0][1]));
                ActionLoggerObserver observer = new ActionLoggerObserver(player);
                MazeRunner mazeRunner = new MazeRunner(maze, player, entry_exit_points[1]);

                if (cmd.hasOption("p")) { //
                    String[] path_args = cmd.getOptionValues("p");
                    String pathToVerify = String.join(" ", path_args);
                    
                    logger.info("*** Creating PathVerifier");
                    PathVerifier pathVerifier = new PathVerifier(mazeRunner);
                    
                    logger.info("*** Verifying provided path: " + pathToVerify);
                    boolean isValid = pathVerifier.verifyPath(pathToVerify);
                    
                    if (isValid) {
                        System.out.println("correct path");
                    }
                    else {
                        System.out.println("incorrect path");
                    }
                }
                else { // not -p flag
                    logger.info("*** Creating MazeSolver");
                    MazeSolver mazeSolver = new MazeSolver(mazeRunner);


                    logger.info("**** Computing path");
                    mazeSolver.MazeRunnerAlgorithm();
                    
                    logger.info("**** Factorizing path");
                    PathFactorizer pathFactorizer = new PathFactorizer();
                    String canonical_path = observer.getPath(); // from ActionLoggerObserver
                    String path = pathFactorizer.factorizePath(canonical_path);

                    System.out.println(path);
                }
                
            }
            else {
                logger.error("no -i flag :(");
            }
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
            e.printStackTrace();
        }
        
        logger.info("** End of MazeRunner");
    }
}
