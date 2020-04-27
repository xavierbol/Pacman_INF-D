/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import pacman_infd.games.Cell;
import pacman_infd.listeners.ElementEventListener;

/**
 *
 * @author Marinus
 */
public abstract class MovingGameElement extends GameElement{
    protected final Cell startCell;
    private Timer timer;
    protected int speed;

    /**
     * Constructor of this class.
     *
     * @param cell the initial position of the moving game element.
     * @param gameEventListener the game listener
     * @param speed the initial speed of the moving game element.
     */
    public MovingGameElement(Cell cell, ElementEventListener gameEventListener, int speed) {
        this.cell = cell;
        this.elementEventListener = gameEventListener;
        cell.addMovingElement(this);
        startCell = cell;
        this.speed = speed;

        ActionListener moveTimerActionListener = this::moveTimerActionPerformed;
        
        timer = new Timer(speed, moveTimerActionListener);
        timer.start();
    }

    /**
     * Move this moving game element
     */
    protected abstract void move();

    /**
     * This is called each 'tick' of the timer. This is used by the GameWorld to
     *
     * @param e the action event
     */
    public abstract void moveTimerActionPerformed(ActionEvent e);

    /**
     * Reset the moving game element to replace it to its initial position
     */
    public void reset() {
        cell.removeMovingElement(this);
        cell = startCell;
        cell.addMovingElement(this);
    }

    /**
     * Return the initial cell of this element.
     *
     * @return The initial cell of this element.
     */
    protected Cell getStartCell() {
        return startCell;
    }

    /**
     * Set the speed of this moving game element
     *
     * @param speed the new speed to set.
     */
    public void setSpeed(int speed) {
        timer.setDelay(speed);
    }


    /**
     * Start timer to move this moving game element.
     */
    public void startTimer() {
        timer.start();
    }

    /**
     * Stop the timer.
     */
    public void stopTimer() {
        timer.stop();
    }  
}
