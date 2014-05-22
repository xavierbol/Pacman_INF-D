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
public class GameController {
    
    private GameWorld gameWorld;
    private GameFrame gameFrame;
    
    public GameController(GameWorld gameWorld, GameFrame gameFrame)
    {
        this.gameWorld = gameWorld;
        this.gameFrame = gameFrame;
    }
}
