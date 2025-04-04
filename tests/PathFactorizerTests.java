package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.PathFactorizer;

public class PathFactorizerTests{
    // Tests PathFactorizer as the name suggests

    private final PathFactorizer factorizer = new PathFactorizer();

    @Test
    void testSingleMove() {
        assertEquals("F", factorizer.factorizePath("F"));
        assertEquals("R", factorizer.factorizePath("R"));
        assertEquals("L", factorizer.factorizePath("L"));
    }

    @Test
    void testRepeatingCharAndSpaces() {
        assertEquals("3F", factorizer.factorizePath("F F F"));
        assertEquals("2R", factorizer.factorizePath("RR"));
        assertEquals("5L", factorizer.factorizePath("LLL LL"));
    }

    @Test
    void testMixedSteps() {
        assertEquals("2F 2R", factorizer.factorizePath("FFRR"));
        assertEquals("F 3R F 2L", factorizer.factorizePath("FRRRFLL"));
    }

    @Test
    void testNoCompressionNeeded() {
        assertEquals("F R F R L", factorizer.factorizePath("FRFRL"));
    }

    @Test
    void testLongMixedPath() {
        String input = "FFFRRLLLFRFRFF";
        String expected = "3F 2R 3L F R F R 2F";
        assertEquals(expected, factorizer.factorizePath(input));
    }
}