/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.util.EventListener;
import pacman_infd.Elements.Ghost;
import pacman_infd.Elements.Pacman;

/**
 *
 * @author Marinus
 */
public interface GameEventListener extends EventListener{
    
    public void gameElementPerfomedAction(GameElement e);
    public void pacmanMoved();
//    public void pacmanFoundPellet();
//    public void pacmanFoundSuperPellet();
//    public void pacmanFoundCherry();
    public void pacmanChangedState(boolean state);
    public void gameElementMovedToCell(Cell cell);

}
