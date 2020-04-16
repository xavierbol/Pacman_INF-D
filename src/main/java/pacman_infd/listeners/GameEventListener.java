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
    void decreaseLife();
    void increasePoints(int amount);
    void refocus();
    void stopStopWatch();
    void restartStopWatch();
    void levelWon();
}
