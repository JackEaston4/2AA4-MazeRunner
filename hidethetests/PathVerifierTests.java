package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.*;

public class PathVerifierTests {

    private MazeTile[][] maze;
    private MazeRunner runner;
    private PathVerifier verifier;

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
        int[] exit = {2, 1};  // exit on the right

        runner = new MazeRunner(maze, entry, exit);
        verifier = new PathVerifier(runner);
    }

    @Test
    void testValidPath() {
        // Expected path: right, right, up, up (FFRRF)
        String path = "F F";
        assertTrue(verifier.verifyPath(path));
    }

    @Test
    void testPathHitsWall() {
        // Going forward from entry would hit a wall
        String path = "R F";
        assertFalse(verifier.verifyPath(path));
    }

    @Test
    void testPathGoesOutOfBounds() {
        // Turn right twice and go forward twice (off the grid)
        String path = "R R F";
        assertFalse(verifier.verifyPath(path));
    }

    @Test
    void testPathWithInvalidCharacter() {
        String path = "F Z F";
        assertFalse(verifier.verifyPath(path));
    }

    @Test
    void testPathEndingInNumberOnly() {
        String path = "F R 2";
        assertFalse(verifier.verifyPath(path));
    }

    @Test
    void testCompressedPathWithSpin() {
        String path = "2R 2R 2F"; // R R R R F F
        assertTrue(verifier.verifyPath(path));
    }

    @Test
    void testWhitespaceIsIgnored() {
        String path = "         F       F              ";
        assertTrue(verifier.verifyPath(path));
    }
}