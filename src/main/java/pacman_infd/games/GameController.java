/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.games;

import pacman_infd.elements.MovingGameElement;
import pacman_infd.enums.GameState;
import pacman_infd.listeners.GameEventListener;
import pacman_infd.strategies.pacman.KeyControlledStrategy;
import pacman_infd.strategies.pacman.PacmanStrategy;
import pacman_infd.strategies.pacman.PriorityToScoreStrategy;
import pacman_infd.strategies.pacman.PriorityToTimeStrategy;
import pacman_infd.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * @author Marinus
 */
public class GameController implements GameEventListener {
    private static final HashMap<String, Class<? extends PacmanStrategy>> PACMAN_STRATEGIES = new HashMap() {
        {
            put("Key Controlled", KeyControlledStrategy.class);
            put("AI - priority to time", PriorityToTimeStrategy.class);
            put("AI - priority to score", PriorityToScoreStrategy.class);
        }
    };
    private static final int REFRESH_RATE = 10;
    private static final int DEFAULT_GAME_SPEED = 200;

    private GameWorld gameWorld;
    private View view;
    private ScorePanel scorePanel;
    private GameState gameState;
    private Timer gameTimer;
    private StopWatch stopWatch;
    private LevelManager levelManager;
    private int gameSpeed;

    /**
     * Creates the game controller of the game.
     *
     * @param view the view of the game.
     * @param scorePanel the score panel.
     */
    public GameController(View view, ScorePanel scorePanel) {
        this.view = view;
        this.scorePanel = scorePanel;
        gameState = GameState.PREGAME;
        levelManager = new LevelManager();
        gameSpeed = DEFAULT_GAME_SPEED;

        ActionListener gameTimerAction = this::gameTimerActionPerformed;

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

    /**
     * Reset the game
     */
    private void reset() {
        scorePanel.resetStats();
        gameState = GameState.RUNNING;
        drawGame();
        gameTimer.start();
        stopWatch.reset();
        stopWatch.start();
    }

    /**
     * Start a new game, or, if the game is already running, restart the game.
     */
    public void newGame() {
        if (gameState.equals(GameState.RUNNING)) {
            pauseGame();
        }

        Class<? extends PacmanStrategy> pacmanStrategyClazz = this.getPacmanStrategy();
        if (pacmanStrategyClazz == null) {
            if (gameState.equals(GameState.PAUSED)) {
                pauseGame();
            }
            return;
        }

        gameWorld = new GameWorld(this, levelManager.getFirstLevel(), gameSpeed, pacmanStrategyClazz);
        reset();
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

        if (gameSpeed > 100) {
            gameSpeed -= 10;
        }

        Class<? extends PacmanStrategy> pacmanStrategy = null;
        while (pacmanStrategy == null) {
            pacmanStrategy = this.getPacmanStrategy();
        }
        gameWorld = new GameWorld(this, levelManager.getNextLevel(), gameSpeed, pacmanStrategy);
        reset();
    }

    /**
     * Pause game will stop all timers
     */
    public void pauseGame() {
        if (gameState.equals(GameState.RUNNING)) {
            for (Cell cell : gameWorld.getCells()) {
                for (MovingGameElement element : cell.getMovingElements()) {
                    element.stopTimer();
                }
            }

            gameTimer.stop();
            stopWatch.stop();
            gameState = GameState.PAUSED;
        } else if (gameState.equals(GameState.PAUSED)) {

            for (Cell cell : gameWorld.getCells()) {
                for (MovingGameElement element : cell.getMovingElements()) {
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
     *
     * @param e the action event.
     */
    public void gameTimerActionPerformed(ActionEvent e) {
        drawGame();
        scorePanel.setTime(stopWatch.getElapsedTimeMinutesSeconds());
        scorePanel.repaint();
    }

    /**
     * Set up the environment of game over.
     */
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

    /**
     * Ask the user for the pacman strategy and return it.
     *
     * @return The pacman strategy class
     */
    private Class<? extends PacmanStrategy> getPacmanStrategy() {
        String pacmanStrategyName = (String) JOptionPane.showInputDialog(
                this.view,
                "Select a pacman strategy",
                "PacMan Strategy",
                JOptionPane.QUESTION_MESSAGE,
                null,
                PACMAN_STRATEGIES.keySet().stream().sorted().toArray(),
                null);

        return PACMAN_STRATEGIES.get(pacmanStrategyName);
    }

    /**
     * Accessor for game state
     *
     * @return The game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Accessor for the view
     *
     * @return The view
     */
    public View getView() {
        return view;
    }

    @Override
    public void decreaseLife() {
        scorePanel.loseLife();
        scorePanel.repaint();
        if (scorePanel.getLives() == 0) {
            gameOver();
        }
    }

    @Override
    public void increasePoints(int amount) {
        scorePanel.addScore(amount);
        scorePanel.repaint();
    }

    @Override
    public void stopStopWatch() {
        stopWatch.stop();
    }

    @Override
    public void restartStopWatch() {
        stopWatch.restart();
    }

    /**
     * Event of the mouse clicked.
     *
     * @param x the x position of the mouse clicked.
     * @param y the y position of the mouse clicked.
     * @param mouseButton the button of the mouse has been clicked.
     */
    public void mouseClicked(int x, int y, int mouseButton) {
        if (gameWorld != null) {
            gameWorld.spawnPortal(x, y, mouseButton);
        }
    }

    @Override
    public void levelWon() {
        this.nextLevel();
    }
}
