package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.Observer.ActionLoggerObserver;

import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // Set-up for command line parsing
        Options options = new Options();
        options.addOption("i", true, "input filepath of maze text file");
        options.addOption(Option.builder("p").hasArgs().valueSeparator(' ').desc("path to be verified by path validation algorithm").build());

        CommandLineParser parser = new DefaultParser();

        try { // try-catch for all functions
            logger.info("*** Parsing command line arguments");
            CommandLine cmd = parser.parse(options, args); // parse the command line arguments

            if (cmd.hasOption("i")) { // filepath given for maze (-i flag)
                logger.info("*** Maze input file provided");

                logger.info("*** Getting Maze input file");
                String filepath = cmd.getOptionValue("i");

                logger.info("*** Loading Maze from file: " + filepath);
                MazeLoader mazeLoader = new MazeLoader();
                MazeTile[][] maze = mazeLoader.loadMaze(filepath);
                int[][] entry_exit_points = mazeLoader.findEntryExitPoints(maze); // extract start and finish coordinates
    
                logger.info("*** Creating Player and hooking on ActionLoggerObserver");
                Player player = new Player(new Position(entry_exit_points[0])); // could be a Singleton
                ActionLoggerObserver observer = new ActionLoggerObserver(player);
                
                logger.info("*** Creating MazeRunner");
                MazeRunner mazeRunner = new MazeRunner(maze, player, entry_exit_points[1]);

                if (cmd.hasOption("p")) { // path verification (-p flag)
                    logger.info("*** Path verification mode");

                    logger.info("*** Getting Path Args");
                    String[] path_args = cmd.getOptionValues("p");
                    String pathToVerify = String.join(" ", path_args);
                    
                    logger.info("*** Creating PathVerifier");
                    PathVerifier pathVerifier = new PathVerifier(mazeRunner);
                    
                    logger.info("*** Verifying provided path: " + pathToVerify);                    
                    if (pathVerifier.verifyPath(pathToVerify)) System.out.println("correct path");
                    else System.out.println("incorrect path");

                    logger.info("**** Path verification complete");
                } // end of if ('-p') (path verification)

                else { // (no -p flag) - solve the maze
                    logger.info("*** Solving maze");

                    logger.info("*** Creating MazeSolver");
                    MazeSolver mazeSolver = new MazeSolver(mazeRunner);

                    logger.info("**** Computing path");
                    mazeSolver.MazeRunnerAlgorithm();
                    String canonical_path = observer.getPath(); // from ActionLoggerObserver

                    
                    logger.info("**** Factorizing path");
                    PathFactorizer pathFactorizer = new PathFactorizer();
                    String path = pathFactorizer.factorizePath(canonical_path);

                    logger.info("**** Printing final path");
                    System.out.println(path);

                    logger.info("**** Maze solving complete");
                } // end of else (no -p flag)
                
            } // end of if ('-i')

            else { // no -i flag
                logger.error("no -i flag :(");
            }
        } catch(Exception e) { // all of main is in try-catch
            logger.error("/!\\ An error has occured /!\\");
            e.printStackTrace();
        }
        
        // end of main
        logger.info("** End of MazeRunner");
    }
}
