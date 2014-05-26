/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman_infd.Elements.Ghost;
import pacman_infd.Elements.Pacman;


/**
 *
 * @author Marinus
 */
public class GameController implements GameEventListener, KeyListener {

    private GameWorld gameWorld;
    private View view;
    private ScorePanel scorePanel;
    
    private String level1 = "D:\\Dropbox\\School\\INF-D\\+Project INF-D\\leve1.txt";
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
    
    @Override
    public void pacmanFoundSuperPellet() {
        scorePanel.addScore(50);
        scorePanel.repaint();
    }

    @Override
    public void pacmanEatsGhost(Ghost ghost) {
        scorePanel.addScore(500);
        scorePanel.repaint();
    }
    
    @Override
    public void pacmanChangedState(boolean state){
        
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
        gameWorld = new GameWorld(this, level1);
        scorePanel.initStats();
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
