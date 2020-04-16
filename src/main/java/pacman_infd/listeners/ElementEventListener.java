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
public interface ElementEventListener extends EventListener{
    void movingElementActionPerformed(MovingGameElement e);
    void eatableElementEaten(Eatable e);
    void makeGhostsVulnerable();
    void ghostsBackToNormal();
    void killPacman();
}
