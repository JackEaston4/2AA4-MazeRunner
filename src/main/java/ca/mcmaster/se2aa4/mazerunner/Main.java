package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.cli.*;

import ca.mcmaster.se2aa4.mazerunner.MazeLoader;

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
                int[][] maze = mazeLoader.loadMaze(filepath);


                /*
                BufferedReader reader = new BufferedReader(new FileReader(filepath));
                logger.info("**** Reading the maze from file " + filepath + "\n");

                String line;
                StringBuilder logLine = new StringBuilder();

                while ((line = reader.readLine()) != null) { // while there is still things to read
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            logLine.append("WALL "); // append each tile of maze string
                
                        } else if (line.charAt(idx) == ' ') {
                            logLine.append("PASS "); // append each tile of maze to string
                        }
                    }

                    logger.trace(logLine.toString()); // Log entire line at once
                    logLine.setLength(0); // clear StringBuilder (for next line)
                }
                reader.close(); // close BufferedReader(FileReader)
                */
            }
            else {
                logger.error("no -i flag :(");
            }
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
