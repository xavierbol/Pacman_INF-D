/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman_infd.Elements.Pacman;

/**
 *
 * @author Marinus
 */
public class KeyManager implements KeyListener {
    
    private boolean keyPressed = false;
    private Pacman pacman;
    private GameWorld gameWorld;
    private GameView gameView;
    
    public KeyManager(GameWorld gameWorld, GameView gameView)
    {
        this.gameWorld = gameWorld;
        this.gameView = gameView;
        
        pacman = gameWorld.getPacman();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //not implemented.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!keyPressed) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    pacman.move(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    pacman.move(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    pacman.move(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    pacman.move(Direction.RIGHT);
                    break;
            }
            gameView.drawGameWorld();
            gameView.requestFocus();
            keyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
    }


    
}
