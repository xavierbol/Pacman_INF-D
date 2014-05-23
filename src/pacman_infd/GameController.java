/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Graphics;
import pacman_infd.Elements.Pellet;

/**
 *
 * @author Marinus
 */
public class GameController implements GameEventListener {

    private GameWorld gameWorld;
    private GameView gameView;
    private KeyManager keyManager;
    
    public GameController(GameView gameview) {
        gameWorld = new GameWorld(this);
        gameView = gameview;

        keyManager = new KeyManager(gameWorld, gameView);
        gameView.addKeyListener(keyManager);
    }

    @Override
    public void GameElementPerfomedAction(GameElement e) {

        drawGame();
    }

    @Override
    public void PacmanFoundPellet(Pellet p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void drawGame() {

        Graphics g = gameView.getGameWorldGraphics();

        if (g != null) {
            gameWorld.draw(g);

            gameView.drawGameWorld();
        }
    }

}
