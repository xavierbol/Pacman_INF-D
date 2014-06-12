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
import pacman_infd.Elements.MovingGameElement;

/**
 *
 * @author Marinus
 */
public class GameController implements GameEventListener {

    private GameWorld gameWorld;
    private View view;
    private ScorePanel scorePanel;
    private boolean cherrySpawned; //TODO: dit moet hier weg
    private GameState gameState;
    private Timer gameTimer;
    private StopWatch stopWatch;
    private ResourceManager resourceManager;
    private SoundManager soundManager;

    public GameController(View view, ScorePanel scorePanel) {

        this.view = view;
        this.scorePanel = scorePanel;
        cherrySpawned = false;
        gameState = GameState.PREGAME;
        resourceManager = new ResourceManager();
        soundManager = new SoundManager();

        ActionListener gameTimerAction = new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameTimerActionPerformed(evt);
            }
        };

        gameTimer = new Timer(10, gameTimerAction);
        stopWatch = new StopWatch();

    }

    @Override
    public void refocus(){
        view.requestFocus();
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
        gameWorld = null;
        gameWorld = new GameWorld(this, resourceManager.getFirstLevel());
        scorePanel.resetStats();
        gameState = GameState.RUNNING;
        drawGame();
        gameTimer.start();
        stopWatch.reset();
        stopWatch.start();
//        }
    }

    public void nextLevel() {
        pauseGame();
        JOptionPane.showMessageDialog(
                null,
                "Well done!\nGet ready for the next level!",
                "Level Complete",
                JOptionPane.ERROR_MESSAGE
        );

        gameWorld = null;
        gameWorld = new GameWorld(this, resourceManager.getNextLevel());
    }

    public void pauseGame() {
        if (gameState == GameState.RUNNING) {
            for (Cell cell : gameWorld.getCells()) {
                for (MovingGameElement element: cell.getElements()) {
                    element.stopTimer();
                }
            }

            gameTimer.stop();
            stopWatch.stop();
            gameState = GameState.PAUSED;
        } else if (gameState == GameState.PAUSED) {
            for (Cell cell : gameWorld.getCells()) {
                for (MovingGameElement element: cell.getElements()) {
                    element.startTimer();
                }
            }
            gameTimer.start();
            stopWatch.start();
            gameState = GameState.RUNNING;
        }
    }

    public void gameTimerActionPerformed(ActionEvent e) {
        //checkCollisions(gameWorld.getPacman().getCell());
        drawGame();
        scorePanel.setTime(stopWatch.getElepsedTimeMinutesSeconds());
        scorePanel.repaint();

    }

    private void gameOver() {
        pauseGame();
        view.repaint();
        drawGame();
        JOptionPane.showMessageDialog(
                null,
                "Game over!\nYour score: " + scorePanel.getScore(),
                "Game over!",
                JOptionPane.ERROR_MESSAGE
        );
        gameWorld = null;
        gameState = GameState.PREGAME;

//        gameTimer.stop();
//        stopWatch.stop();
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void decreaseLife() {
        scorePanel.looseLife();
        scorePanel.repaint();
        if(scorePanel.getLives() == 0){
            gameOver();
        }
    }

    @Override
    public void increasePoints(int amount) {
        scorePanel.addScore(amount);
        scorePanel.repaint();
    }
    
    public SoundManager getSoundManager(){
        return soundManager;
    }

}
