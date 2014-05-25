/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman_infd.Elements.Pacman;


/**
 *
 * @author Marinus
 */
public class GameController implements GameEventListener, KeyListener {

    private GameWorld gameWorld;
    private View view;
    private ScorePanel scorePanel;
    //private KeyManager keyManager;
    
    public GameController(View view, ScorePanel scorePanel) {
        
        this.view = view;
        this.scorePanel = scorePanel;

        view.addKeyListener(this);
    }

    @Override
    public void gameElementPerfomedAction(GameElement e) {
        drawGame();
        view.requestFocus();
    }

    @Override
    public void pacmanMoved() {
        
        drawGame();
        view.requestFocus();
    }
    
    @Override
    public void pacmanFoundPellet() {
        scorePanel.addScore(5);
        scorePanel.repaint();
    }
    
    public void pacmanDied(Pacman pacman) {
        scorePanel.looseLife();
        scorePanel.repaint();
        pacman.resetPacman();
    }
    
    private void drawGame() {

        Graphics g = view.getGameWorldGraphics();

        if (g != null) {
            gameWorld.draw(g);

            view.drawGameWorld();
        }
    }
    
    public View getView() {
        return view;
    }
    
    public void newGame()
    {
        gameWorld = new GameWorld(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("test");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }

    

}
