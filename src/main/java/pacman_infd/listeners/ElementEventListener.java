/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.listeners;

import java.util.EventListener;

import pacman_infd.elements.Eatable;
import pacman_infd.elements.MovingGameElement;

/**
 *
 * @author Marinus
 */
public interface ElementEventListener extends EventListener {
    /**
     * Called when a moving game element move.
     *
     * @param e the moving game element which is moved.
     */
    void movingElementActionPerformed(MovingGameElement e);

    /**
     * Called when an eatable element is eaten by Pacman.
     *
     * @param e the eatable element.
     */
    void eatableElementEaten(Eatable e);

    /**
     * Make all alive ghosts vulnerable.
     */
    void makeGhostsVulnerable();

    /**
     * Send the information the ghosts back to normal state.
     */
    void ghostsBackToNormal();

    /**
     * Kill Pacman.
     */
    void killPacman();
}
