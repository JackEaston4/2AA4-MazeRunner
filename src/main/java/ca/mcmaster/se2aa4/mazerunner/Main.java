package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");


        Options options = new Options();
        options.addOption("i", true, "input filepath of maze text file");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args); // parse the command line arguments

            if (cmd.hasOption("i")) {
                String filepath = cmd.getOptionValue("i");

                MazeLoader mazeLoader = new MazeLoader();
                MazeTile[][] maze = mazeLoader.loadMaze(filepath);
                int[][] entry_exit_points = mazeLoader.findEntryExitPoints(maze);
                
                logger.info("*** Creating MazeRunner");
                MazeRunner mazeRunner = new MazeRunner(maze, entry_exit_points[0], entry_exit_points[1]);
                
                logger.info("**** Computing path");
                String canonical_path = mazeRunner.MazeRunnerAlgorithm();

                PathFactorizer pathFactorizer = new PathFactorizer();
                String path = pathFactorizer.factorizePath(canonical_path);

                System.out.println(path);
            }
            else {
                logger.error("no -i flag :(");
            }
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
        
        logger.info("** End of MazeRunner");
    }
}
