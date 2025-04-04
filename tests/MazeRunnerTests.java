package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.*;

public class MazeRunnerTests {

    private MazeTile[][] maze;
    private MazeRunner runner;
    private Player player;

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
        player = new Player(new Position(0, 1));
        int[] exit = {2, 1};  // exit on the right

        runner = new MazeRunner(maze, player, exit);
    }

    @Test
    void testInitialPosition() {
        assertEquals(new Position(0, 1), runner.getPlayerPosition());
    }

    @Test
    void testMoveToExit() {
        runner.movePlayer();
        runner.movePlayer();
        assertTrue(runner.isAtFinish());
    }

    @Test
    void testCheckForWallAhead() {
        assertFalse(runner.checkForWall(Direction.FORWARD)); // facing PATH so will return False
        runner.turnDirection(Direction.LEFT); // NORTH
        assertTrue(runner.checkForWall(Direction.FORWARD)); // facing WALL so will return True
    }

    @Test
    void testCheckForWallLeftRight() {
        runner.turnDirection(Direction.LEFT); // NORTH
        assertTrue(runner.checkForWall(Direction.LEFT));  // WEST -> is out of bounds = wall
        assertFalse(runner.checkForWall(Direction.RIGHT)); // EAST -> is PATH
    }

    @Test
    void testInvalidDirectionThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            runner.checkForWall(null); // not 'F', 'R', or 'L'
        });
    }
}