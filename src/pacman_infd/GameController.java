/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import pacman_infd.Elements.Ghost;
import pacman_infd.Elements.Pacman;

/**
 *
 * @author Marinus
 */
public class GameController implements GameEventListener {

    private GameWorld gameWorld;
    private View view;
    private ScorePanel scorePanel;
    private boolean cherrySpawned;
    private String level1 = "D:\\Dropbox\\School\\INF-D\\+Project INF-D\\leve1.txt";
    private GameState gameState;
    private SoundManager soundManager;
    private Timer gameTimer;
    private StopWatch stopWatch;

    public GameController(View view, ScorePanel scorePanel) {

        this.view = view;
        this.scorePanel = scorePanel;
        cherrySpawned = false;
        soundManager = new SoundManager();
        gameState = GameState.PREGAME;
        
        ActionListener gameTimerAction = new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameTimerActionPerformed(evt);
            }
        };
        
        gameTimer = new Timer(100, gameTimerAction);
        stopWatch = new StopWatch();

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
        if (!cherrySpawned) {
            if (gameWorld.countPellets() <= gameWorld.getNumberOfPelletsAtStart() / 2) {
                gameWorld.placeCherryOnRandomEmptyCell();
                cherrySpawned = true;
            }
        }
        //soundManager.playWaka();
    }

    @Override
    public void pacmanDied(Pacman pacman) {
        scorePanel.looseLife();
        scorePanel.repaint();
        if (scorePanel.getLives() <= 0) {
            gameOver();
        } else {
            pacman.resetPacman();
            for (Ghost ghost : gameWorld.getGhosts()) {
                ghost.resetGhost();
            }
        }
    }

    @Override
    public void pacmanFoundSuperPellet() {
        scorePanel.addScore(50);
        scorePanel.repaint();
        for (Ghost ghost : gameWorld.getGhosts()) {
            ghost.runFromPacman();
        }
    }

    @Override
    public void pacmanEatsGhost(Ghost ghost) {
        scorePanel.addScore(500);
        scorePanel.repaint();
    }

    @Override
    public void pacmanChangedState(boolean state) {

    }

    private void drawGame() {

        Graphics g = view.getGameWorldGraphics();

        if (g != null && gameWorld != null) {
            gameWorld.draw(g);

            view.drawGameWorld();
        }
    }

    public View getView() {
        return view;
    }

    public void newGame() {
//        if (gameState == GameState.PREGAME) {
            gameWorld = new GameWorld(this, level1);
            scorePanel.resetStats();
            gameState = GameState.RUNNING;
            drawGame();
            gameTimer.start();
            stopWatch.reset();
            stopWatch.start();
//        }
    }

    public void pauzeGame() {
        if (gameState == GameState.RUNNING) {
            for (Ghost ghost : gameWorld.getGhosts()) {
                ghost.stopTimer();
            }
            gameTimer.stop();
            stopWatch.stop();
            gameState = GameState.PAUSED;
        }
        else if (gameState == GameState.PAUSED){
            for (Ghost ghost : gameWorld.getGhosts()) {
                ghost.startTimer();
            }
            gameTimer.start();
            stopWatch.start();
            gameState = GameState.RUNNING;
        }
    }

    public void gameTimerActionPerformed(ActionEvent e) {
        scorePanel.setTime(stopWatch.getElepsedTimeMinutesSeconds());
        scorePanel.repaint();
        
    }

    @Override
    public void pacmanFoundCherry() {
        scorePanel.addScore(100);
        scorePanel.repaint();
    }

    private void gameOver() {
        pauzeGame();
        JOptionPane.showMessageDialog(null, "Game over!\nYour score: " + scorePanel.getScore(), "Game over!", JOptionPane.ERROR_MESSAGE);
        gameWorld = null;
        gameState = GameState.PREGAME;
        view.repaint();
        gameTimer.stop();
        stopWatch.stop();
    }
    
    public GameState getGameState(){
        return gameState;
    }

}
