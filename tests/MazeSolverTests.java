package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.*;

public class MazeSolverTests {

    private MazeTile[][] maze;
    private MazeRunner runner;
    private MazeSolver solver;

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

        runner = new MazeRunner(maze, entry, exit); // Starts facing EAST
        solver = new MazeSolver(runner);
    }

    @Test
    void testSolverFindsStraightPath() {
        // Expected path: F F (straight to exit)
        String path = solver.MazeRunnerAlgorithm();
        assertEquals("FF", path);
        assertTrue(runner.isAtFinish());
    }

    @Test
    void testPathLengthIsCorrect() {
        String path = solver.MazeRunnerAlgorithm();
        assertEquals(2, path.length());
    }

    @Test
    void testPathOnlyUsesFRL() {
        String path = solver.MazeRunnerAlgorithm();
        assertTrue(path.matches("[FRL]+"));
    }

    @Test
    void testSolverReturnsEmptyIfAlreadyAtFinish() {
        MazeTile[][] singleTile = {
            {MazeTile.PATH}
        };
        MazeRunner miniRunner = new MazeRunner(singleTile, new int[]{0, 0}, new int[]{0, 0});
        MazeSolver miniSolver = new MazeSolver(miniRunner);
        assertEquals("", miniSolver.MazeRunnerAlgorithm());
    }
}
