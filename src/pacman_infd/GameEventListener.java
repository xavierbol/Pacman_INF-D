/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.util.EventListener;
import pacman_infd.Elements.Pellet;

/**
 *
 * @author Marinus
 */
public interface GameEventListener extends EventListener{
    
    public void GameElementPerfomedAction(GameElement e);
    public void PacmanFoundPellet(Pellet p);
}
