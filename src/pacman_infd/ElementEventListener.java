/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.util.EventListener;
import pacman_infd.Elements.Ghost;
import pacman_infd.Elements.MovingGameElement;
import pacman_infd.Elements.Pacman;

/**
 *
 * @author Marinus
 */
public interface ElementEventListener extends EventListener{
    
    public void pacmanActionPerformed(Pacman p);
    public void ghostActionPerformed(Ghost g);
    public void eatableElementEaten(Eatable e);
    public void makeGhostsVulnerable();
    public void killPacman();

}
