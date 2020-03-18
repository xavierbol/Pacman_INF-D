/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.Enums;

import java.awt.event.KeyEvent;

/**
 *
 * @author Marinus
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    private Direction opposite;
    private Direction clockwise;
    private Direction oppositeClockwise;

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;

        UP.clockwise = RIGHT;
        DOWN.clockwise = LEFT;
        LEFT.clockwise = UP;
        RIGHT.clockwise = DOWN;

        UP.oppositeClockwise = LEFT;
        DOWN.oppositeClockwise = RIGHT;
        LEFT.oppositeClockwise = DOWN;
        RIGHT.oppositeClockwise = UP;
    }

    /**
     * Get the opposite direction
     * @return the opposite direction
     */
    public Direction getOpposite() {
        return opposite;
    }

    /**
     * Get the clockwise direction
     * @return the clockwise direction
     */
    public Direction getClockwise() {
        return clockwise;
    }

    /**
     * Get the opposite clockwise direction
     * @return the opposite clockwise direction
     */
    public Direction getOppositeClockwise() {
        return oppositeClockwise;
    }

    public static Direction getDirection(KeyEvent keyEvent, Direction currentDirection) {
        Direction d = currentDirection;

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                d = UP; break;
            case KeyEvent.VK_DOWN:
                d = DOWN; break;
            case KeyEvent.VK_LEFT:
                d = LEFT; break;
            case KeyEvent.VK_RIGHT:
                d = RIGHT; break;
        }

        return d;
    }
}
