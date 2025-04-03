package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.io.*;

import ca.mcmaster.se2aa4.mazerunner.MazeLoader;
import ca.mcmaster.se2aa4.mazerunner.MazeTile;

public class MazeLoaderTests {

    private File tempMazeFile;

    @BeforeEach
    void setup() throws IOException {
        // Create a temporary maze file
        tempMazeFile = File.createTempFile("test_maze", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempMazeFile))) {
            writer.write("# #\n");
            writer.write(" # \n");
        }
    }

    @AfterEach
    void cleanup() {
        if (tempMazeFile.exists()) {
            tempMazeFile.delete();
        }
    }
    

    @Test
    void testLoadMazeReturnsCorrectDimensions() {
        MazeLoader loader = new MazeLoader();
        MazeTile[][] maze = loader.loadMaze(tempMazeFile.getAbsolutePath());

        assertNotNull(maze);
        assertEquals(3, maze.length);  // 3 columns (longest line)
        assertEquals(2, maze[0].length);  // 2 rows
    }

    @Test
    void testMazeTilesCorrectlyParsed() {
        MazeLoader loader = new MazeLoader();
        MazeTile[][] maze = loader.loadMaze(tempMazeFile.getAbsolutePath());

        assertEquals(MazeTile.WALL, maze[0][0]);
        assertEquals(MazeTile.PATH, maze[1][0]);
        assertEquals(MazeTile.WALL, maze[2][0]);

        assertEquals(MazeTile.PATH, maze[0][1]);
        assertEquals(MazeTile.WALL, maze[1][1]);
        assertEquals(MazeTile.PATH, maze[2][1]);
    }

    @Test
    void testFindEntryExitPoints() {
        MazeLoader loader = new MazeLoader();
        MazeTile[][] maze = loader.loadMaze(tempMazeFile.getAbsolutePath());

        int[][] points = loader.findEntryExitPoints(maze);

        assertNotNull(points);
        assertArrayEquals(new int[]{0, 1}, points[0]); // Entry at left side, row 1
        assertArrayEquals(new int[]{2, 1}, points[1]); // Exit at right side, row 1
    }

    @Test
    void testInvalidPathReturnsNull() {
        MazeLoader loader = new MazeLoader();
        MazeTile[][] maze = loader.loadMaze("nonexistent/path/to/maze.txt");
        assertNull(maze);
    }
}