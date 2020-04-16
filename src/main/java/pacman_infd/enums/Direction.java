/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.enums;

import java.awt.event.KeyEvent;

/**
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
     *
     * @return the opposite direction
     */
    public Direction getOpposite() {
        return opposite;
    }

    public static Direction getDirection(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP: {
                return Direction.UP;
            }
            case KeyEvent.VK_DOWN: {
                return Direction.DOWN;
            }
            case KeyEvent.VK_LEFT: {
                return Direction.LEFT;
            }
            case KeyEvent.VK_RIGHT: {
                return Direction.RIGHT;
            }
            default:
                return Direction.UP;
        }
    }
}
