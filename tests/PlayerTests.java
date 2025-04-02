package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.mcmaster.se2aa4.mazerunner.Player;
import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.Facing;
import ca.mcmaster.se2aa4.mazerunner.Direction;

public class PlayerTests {
    // Tests Player.java, Direction.java, Position.java, and Facing.java

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player(new Position(2, 2));
    }

    @Test
    void testInitialFacingIsEast() {
        assertEquals(Facing.EAST, player.getFacing());
    }

    @Test
    void testMoveForwardEast() {
        player.moveForward();
        assertEquals(new Position(3, 2), player.getPosition());
    }

    @Test
    void testTurnRightFromEast() {
        player.turn(Direction.RIGHT);
        assertEquals(Facing.SOUTH, player.getFacing());
    }

    @Test
    void testTurnLeftFromEast() {
        player.turn(Direction.LEFT);
        assertEquals(Facing.NORTH, player.getFacing());
    }

    @Test
    void testFullTurn() {
        player.turn(Direction.RIGHT); // EAST -> SOUTH
        player.turn(Direction.RIGHT); // SOUTH -> WEST
        player.turn(Direction.RIGHT); // WEST -> NORTH
        player.turn(Direction.RIGHT); // NORTH -> EAST
        assertEquals(Facing.EAST, player.getFacing());
    }
    
}
