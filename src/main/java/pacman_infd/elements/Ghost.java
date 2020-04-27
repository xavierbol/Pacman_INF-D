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
import java.util.Objects;

/**
 * @author Marinus
 */
public class Ghost extends MovingGameElement implements Eatable {
    private static final int VULNERABLE_TIMER_DELAY = 5000;
    private static final int STATE_SOON_CHANGE_DELAY = 2000;
    private static final int DEATH_TIMER_DELAY = 5000;
    private static final int VALUE = 200;

    private GhostStrategy ghostStrategy;
    private final GhostStrategy initialGhostStrategy;
    private final Color defaultColor;
    private Color color;
    private GhostState state;
    private byte pauseState;

    private Timer stateSoonChangeTimer; // To say when the state ghost change from VULNERABLE to NORMAL
    private Timer vulnerabilityTimer;
    private Timer deathTimer;

    /**
     * Creates a new ghost in the game.
     *
     * @param cell              the cell where the ghost must appear
     * @param gameEventListener the game listener
     * @param speed             the speed of the ghost
     * @param ghostStrategy     the initial strategy that the ghost must adopt.
     * @param color             the default color of the ghost
     */
    public Ghost(Cell cell, ElementEventListener gameEventListener, int speed, GhostStrategy ghostStrategy, Color color) {
        super(cell, gameEventListener, speed);
        this.ghostStrategy = ghostStrategy;
        this.defaultColor = color;
        initialGhostStrategy = ghostStrategy;
        state = GhostState.NORMAL;

        ActionListener deathTimerAction = this::deathTimerActionPerformed;
        ActionListener changeStateAction = this::changeStateActionPerformed;

        stateSoonChangeTimer = new Timer(STATE_SOON_CHANGE_DELAY, changeStateAction);
        setVulnerabilityTimer(VULNERABLE_TIMER_DELAY);
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

    public void setVulnerabilityTimer(int time) {
        ActionListener vulnerabilityTimerAction = this::vulnerabilityTimerActionPerformed;
        this.vulnerabilityTimer = new Timer(time, vulnerabilityTimerAction);
    }

    /**
     * Draw this Ghost
     *
     * @param g the graphics object to draw the current ghost in the game board
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

    /**
     * Treatment when this ghost die
     */
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

    /**
     * Accessor of the state of the ghost.
     *
     * @return The state of the ghost
     */
    public GhostState getState() {
        return state;
    }

    @Override
    public void moveTimerActionPerformed(ActionEvent e) {
        move();
        elementEventListener.movingElementActionPerformed(this);
    }

    /**
     * Change the state of the ghost, from vulnerable state to normal state.
     * This method is called when the time of the ghost is vulnerable is finished.
     *
     * @param e the action event
     */
    private void changeStateActionPerformed(ActionEvent e) {
        stateSoonChangeTimer.stop();
        if (state.equals(GhostState.VULNERABLE)) {
            backToNormal();
        }
    }

    /**
     * Launch the second timer when the ghost is vulnerable, that is
     * the timer to notify that the state of this ghost will change
     * soon to the normal state.
     *
     * @param evt the action event.
     */
    private void vulnerabilityTimerActionPerformed(ActionEvent evt) {
        vulnerabilityTimer.stop();
        if (state.equals(GhostState.VULNERABLE)) {
            stateSoonChangeTimer.start();
        }
    }

    /**
     * Return this ghost to the normal state when the timer of death state is finished.
     *
     * @param evt the action event.
     */
    private void deathTimerActionPerformed(ActionEvent evt) {
        backToNormal();
    }

    /**
     * Returns if the ghost is in its end of vulnerability phase.
     *
     * @return True if the ghost is in its end of vulnerability phase, false otherwise.
     */
    public boolean isEndingVulnerability() {
        return this.stateSoonChangeTimer.isRunning();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ghost ghost = (Ghost) o;
        return pauseState == ghost.pauseState &&
                Objects.equals(ghostStrategy, ghost.ghostStrategy) &&
                initialGhostStrategy.equals(ghost.initialGhostStrategy) &&
                defaultColor.equals(ghost.defaultColor) &&
                color.equals(ghost.color) &&
                state == ghost.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ghostStrategy, initialGhostStrategy, defaultColor, color, state, pauseState);
    }
}
