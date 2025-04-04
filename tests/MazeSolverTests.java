package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.*;
import ca.mcmaster.se2aa4.mazerunner.Observer.ActionLoggerObserver;


public class MazeSolverTests {

    private MazeTile[][] maze;
    private MazeRunner runner;
    private MazeSolver solver;
    private ActionLoggerObserver observer;

    @BeforeEach
    void setup() {
        // Create a 3x3 simple maze:
        // WALL   WALL   WALL
        // PATH   PATH   PATH
        // WALL   WALL   WALL
        maze = new MazeTile[][] {
            {MazeTile.WALL, MazeTile.PATH, MazeTile.WALL},
            {MazeTile.WALL, MazeTile.PATH, MazeTile.WALL},
            {MazeTile.WALL, MazeTile.PATH, MazeTile.WALL}
        };

        int[] entry = {0, 1}; // entry on the left side
        int[] exit = {2, 1};  // exit on the right side

        Player player = new Player(new Position(entry));
        observer = new ActionLoggerObserver(player); // Attach observer

        runner = new MazeRunner(maze, player, exit); // Starts facing EAST
        solver = new MazeSolver(runner);
    }

    @Test
    void testSolverFindsStraightPath() {
        // Expected path: F F (straight to exit)
        solver.MazeRunnerAlgorithm();
        String path = observer.getPath();

        assertEquals("FF", path);
        assertTrue(runner.isAtFinish());
    }

    @Test
    void testPathOnlyUsesFRL() {
        solver.MazeRunnerAlgorithm();
        String path = observer.getPath();
        assertTrue(path.matches("[FRL]+"));
    }

    @Test
    void testSolverReturnsEmptyIfAlreadyAtFinish() {
        MazeTile[][] singleTile = {
            {MazeTile.PATH}
        };

        Player miniPlayer = new Player(new Position(0, 0));
        ActionLoggerObserver miniObserver = new ActionLoggerObserver(miniPlayer);

        MazeRunner miniRunner = new MazeRunner(singleTile, miniPlayer, new int[]{0, 0});
        MazeSolver miniSolver = new MazeSolver(miniRunner);

        miniSolver.MazeRunnerAlgorithm();
        assertEquals("", miniObserver.getPath());
    }
}
