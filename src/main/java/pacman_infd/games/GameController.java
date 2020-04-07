/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.games;

import pacman_infd.enums.GameState;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import pacman_infd.elements.MovingGameElement;
import pacman_infd.listeners.GameEventListener;
import pacman_infd.utils.SoundManager;

/**
 *
 * @author Marinus
 */
public class GameController implements GameEventListener {
    private GameWorld gameWorld;
    private View view;
    private ScorePanel scorePanel;
    private GameState gameState;
    private Timer gameTimer;
    private StopWatch stopWatch;
    private LevelManager levelManager;
    private int gameSpeed;
    
    private static final int REFRESH_RATE = 10;
    private static final int DEFAULT_GAME_SPEED = 250;

    public GameController(View view, ScorePanel scorePanel) {
        this.view = view;
        this.scorePanel = scorePanel;
        gameState = GameState.PREGAME;
        levelManager = new LevelManager();
        gameSpeed = DEFAULT_GAME_SPEED;

        ActionListener gameTimerAction = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameTimerActionPerformed(evt);
            }
        };

        gameTimer = new Timer(REFRESH_RATE, gameTimerAction);
        stopWatch = new StopWatch();
    }

    /**
     * give focus back to the view
     */
    @Override
    public void refocus() {
        view.requestFocus();
    }

    /**
     * draw the game.
     */
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

    /**
     * Start a new game, or, if the game is already running, restart the game.
     */
    public void newGame() {
        if(gameState.equals(GameState.RUNNING)){
            pauseGame();
            gameWorld.clearGameWorld();
            gameWorld = null;
        }
        
        gameWorld = new GameWorld(this, levelManager.getFirstLevel(), gameSpeed);
        scorePanel.resetStats();
        gameState = GameState.RUNNING;
        drawGame();
        gameTimer.start();
        stopWatch.reset();
        stopWatch.start();
    }

    /**
     * Go to the next level.
     */
    public void nextLevel() {
        SoundManager.playSound("win");
        pauseGame();
        JOptionPane.showMessageDialog(
                null,
                "Well done!\nGet ready for the next level!",
                "Level Complete",
                JOptionPane.ERROR_MESSAGE
        );

        if(gameSpeed > 100){
            gameSpeed -= 10;
        }
        gameWorld = null;
        gameWorld = new GameWorld(this, levelManager.getNextLevel(), gameSpeed);
    }

    /**
     * Pause game will stop all timers
     */
    public void pauseGame() {
        if (gameState.equals(GameState.RUNNING)) {
            for (Cell cell : gameWorld.getCells()) {
                for (MovingGameElement element: cell.getMovingElements()) {
                    element.stopTimer();
                }
            }

            gameTimer.stop();
            stopWatch.stop();
            gameState = GameState.PAUSED;
        } else if (gameState.equals(GameState.PAUSED)) {

            for (Cell cell : gameWorld.getCells()) {
                for (MovingGameElement element: cell.getMovingElements()) {
                    element.startTimer();
                }

            }
            gameTimer.start();
            stopWatch.start();
            gameState = GameState.RUNNING;
        }
    }

    /**
     * draw the game at each tick of the game timer.
     * @param e 
     */
    public void gameTimerActionPerformed(ActionEvent e) {
        drawGame();
        scorePanel.setTime(stopWatch.getElapsedTimeMinutesSeconds());
        scorePanel.repaint();
    }
    
    private void gameOver() {
       
        pauseGame();
        drawGame();     
        JOptionPane.showMessageDialog(
                null,
                "Game over!\nYour score: " + scorePanel.getScore(),
                "Game over!",
                JOptionPane.ERROR_MESSAGE
        );
        gameWorld.clearGameWorld();
        gameWorld = null;
        gameState = GameState.PREGAME;

    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void decreaseLife() {
        scorePanel.loseLife();
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
    
    public void mouseClicked(int x, int y, int mouseButton){
        if(gameWorld != null){
             gameWorld.spawnPortal(x, y, mouseButton);  
        }
    }
}
