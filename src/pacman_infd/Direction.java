/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

/**
 *
 * @author Marinus
 */
public enum Direction {

    UP, DOWN, LEFT, RIGHT;

    /**
     *
     * @return
     */
    public Direction getOpposite() {
        Direction d = null;
        switch (this) {
            case UP:
                d = DOWN; break;
            case DOWN:
                d = UP; break;
            case LEFT:
                d = RIGHT; break;
            case RIGHT:
                d = UP; break;

        }
        return d;
    }
}
