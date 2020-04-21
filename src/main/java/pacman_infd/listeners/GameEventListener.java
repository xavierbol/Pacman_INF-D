/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.listeners;

/**
 *
 * @author Marinus
 */
public interface GameEventListener {
    /**
     * Decrease life of the player.
     */
    void decreaseLife();

    /**
     * Increase the score by the amount given in parameter.
     *
     * @param amount the amount of points to add into the score of the player.
     */
    void increasePoints(int amount);

    /**
     * Refocus the game.
     */
    void refocus();

    /**
     * Stop the stopwatch.
     */
    void stopStopWatch();

    /**
     * Restart the stopwatch.
     */
    void restartStopWatch();

    /**
     * Send the information that level is won.
     */
    void levelWon();
}
