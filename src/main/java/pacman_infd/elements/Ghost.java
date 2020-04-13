/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.elements;

import pacman_infd.enums.GhostState;
import pacman_infd.games.Cell;
import pacman_infd.listeners.ElementEventListener;
import pacman_infd.strategies.ghost.FleeStrategy;
import pacman_infd.strategies.ghost.GhostStrategy;
import pacman_infd.strategies.ghost.ReturnHomeSrategy;
import pacman_infd.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Marinus
 */
public class Ghost extends MovingGameElement implements Eatable {
    private GhostStrategy ghostStrategy;
    private GhostStrategy initialGhostStrategy;
    private Color defaultColor;
    private Color color;
    private GhostState state;

    private Timer stateSoonChangeTimer; // To say when the state ghost change from VULNERABLE to NORMAL
    private Timer vulnerabilityTimer;
    private Timer deathTimer;
    private byte pauseState;
    private static final int VULNERABLE_TIMER_DELAY = 7000;
    private static final int STATE_SOON_CHANGE_DELAY = 2000;
    private static final int DEATH_TIMER_DELAY = 5000;

    private static final int VALUE = 400;

    public Ghost(Cell cell, ElementEventListener gameEventListener, int speed, GhostStrategy ghostStrategy, Color color) {
        super(cell, gameEventListener, speed);
        this.ghostStrategy = ghostStrategy;
        this.defaultColor = color;
        initialGhostStrategy = ghostStrategy;
        state = GhostState.NORMAL;

        ActionListener vulnerabilityTimerAction = this::vulnerabilityTimerActionPerformed;

        ActionListener deathTimerAction = this::deathTimerActionPerformed;

        ActionListener changeStateAction = this::changeStateActionPerformed;

        stateSoonChangeTimer = new Timer(STATE_SOON_CHANGE_DELAY, changeStateAction);
        vulnerabilityTimer = new Timer(VULNERABLE_TIMER_DELAY, vulnerabilityTimerAction);
        deathTimer = new Timer(DEATH_TIMER_DELAY, deathTimerAction);
    }

    /**
     * Returns the ghost color.
     *
     * @return the ghost color.
     */
    public Color getColor() {
        return defaultColor;
    }

    /**
     * Change the color of the ghost.
     *
     * @param g     The Graphics object.
     * @param color The color.
     */
    private void setColor(Graphics g, Color color) {
        this.color = color;
        g.setColor(color);
    }

    /**
     * Draw this Ghost
     *
     * @param g
     */
    public void draw(Graphics g) {
        if (state.equals(GhostState.VULNERABLE)) {
            if (stateSoonChangeTimer.isRunning() && this.color.equals(Color.BLUE)) {
                this.setColor(g, Color.WHITE);
            } else {
                this.setColor(g, Color.BLUE);
            }
        } else if (state.equals(GhostState.DEAD)) {
            this.setColor(g, Color.BLACK);
        } else {
            this.setColor(g, defaultColor);
        }
        //body
        g.fillRoundRect(
                (int) getPosition().getX() - 5,
                (int) getPosition().getY() - 5,
                getCell().getSize() + 10,
                getCell().getSize() + 10,
                10, 5
        );
        // eyes
        g.setColor(Color.WHITE);
        // left eye
        g.fillOval(
                (int) getPosition().getX(),
                (int) getPosition().getY(),
                12,
                12
        );
        // right eye
        g.fillOval(
                (int) getPosition().getX() + 20,
                (int) getPosition().getY(),
                12,
                12
        );
        // pupils
        g.setColor(Color.BLACK);
        // left pupil
        g.fillOval(
                (int) getPosition().getX() + 4,
                (int) getPosition().getY() + 2,
                6,
                6
        );
        // right pupil
        g.fillOval(
                (int) getPosition().getX() + 24,
                (int) getPosition().getY() + 2,
                6,
                6
        );
    }

    /**
     * Move to the next cell. Uses its current strategy to determine which cell
     * to move to.
     */
    protected void move() {
        Cell nextCell = ghostStrategy.giveNextCell(cell);
        if (nextCell != null) {
            nextCell.addMovingElement(this);
            cell.removeMovingElement(this);
            setCell(nextCell);
        }
    }

    /**
     * Change current strategy to FleeStrategy and lowers the speed of this
     * Ghost by 50% This is called when Pacman eats a superPellet.
     */
    public void flee() {
        if (state.equals(GhostState.VULNERABLE)) {
            vulnerabilityTimer.restart();
            stateSoonChangeTimer.stop();
        } else if (state.equals(GhostState.NORMAL)) {
            this.ghostStrategy = new FleeStrategy();
            state = GhostState.VULNERABLE;
            setSpeed((int) (speed * 1.50));
            vulnerabilityTimer.restart();
        }
    }

    /**
     * Revert back to the initial Strategy and initial speed and stops.
     */
    public void backToNormal() {
        ghostStrategy = initialGhostStrategy;
        state = GhostState.NORMAL;
        setSpeed(speed);
        stateSoonChangeTimer.stop();
        vulnerabilityTimer.stop();
        deathTimer.stop();
        this.elementEventListener.ghostsBackToNormal();
    }

    public void dead() {
        setSpeed(speed);
        ghostStrategy = new ReturnHomeSrategy(startCell);
        state = GhostState.DEAD;
        vulnerabilityTimer.stop();
        stateSoonChangeTimer.stop();
        deathTimer.start();
    }

    @Override
    public void reset() {
        super.reset();
        backToNormal();
    }

    public GhostState getState() {
        return state;
    }

    /**
     * This is called each 'tick' of the timer. This is used by the GameWorld to
     *
     * @param e
     */
    @Override
    public void moveTimerActionPerformed(ActionEvent e) {
        move();
        //checkCollisions();
        elementEventListener.movingElementActionPerformed(this);
    }

    public void changeStateActionPerformed(ActionEvent e) {
        stateSoonChangeTimer.stop();
        if (state.equals(GhostState.VULNERABLE)) {
            backToNormal();
        }
    }

    private void vulnerabilityTimerActionPerformed(ActionEvent evt) {
        vulnerabilityTimer.stop();
        if (state.equals(GhostState.VULNERABLE)) {
            stateSoonChangeTimer.start();
        }
    }

    private void deathTimerActionPerformed(ActionEvent evt) {
        backToNormal();
    }

    @Override
    public void eatMe() {
        if (state.equals(GhostState.VULNERABLE)) {
            SoundManager.playSound("ghost");
            elementEventListener.eatableElementEaten(this);
            dead();
        } else if (state.equals(GhostState.NORMAL)) {
            SoundManager.playSound("death");
            elementEventListener.killPacman();
        }
    }

    @Override
    public int getValue() {
        return VALUE;
    }

    @Override
    public void startTimer() {
        super.startTimer();

        if (pauseState == 1) {
            vulnerabilityTimer.start();
        } else if (pauseState == 2) {
            stateSoonChangeTimer.start();
        } else if (pauseState == 3) {
            deathTimer.start();
        }
    }

    @Override
    public void stopTimer() {
        super.stopTimer();

        pauseState = 0;
        if (vulnerabilityTimer.isRunning()) {
            vulnerabilityTimer.stop();
            pauseState = 1;
        } else if (stateSoonChangeTimer.isRunning()) {
            stateSoonChangeTimer.stop();
            pauseState = 2;
        } else if (deathTimer.isRunning()) {
            deathTimer.stop();
            pauseState = 3;
        }
    }
}
