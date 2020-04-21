/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.elements;

/**
 *
 * @author Marinus
 */
public interface Eatable {
    void eatMe();

    /**
     * Get value of the element.
     *
     * @return The value of the element.
     */
    int getValue();
}
