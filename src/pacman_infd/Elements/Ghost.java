/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.Elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.Timer;
import pacman_infd.Cell;
import pacman_infd.GameElement;
import pacman_infd.Strategy;
import pacman_infd.GameEventListener;
import pacman_infd.Strategies.FleeStrategy;
import pacman_infd.Strategies.ReturnHomeSrategy;

/**
 *
 * @author Marinus
 */
public class Ghost extends MovingGameElement {

    private Strategy strategy;
    private Strategy initialStrategy;
    private Color color;
    private GhostState state;

    private Timer vulnerabilityTimer;
    private Timer deathTimer;
    private final int VULTIMER_DELAY = 10000;
    private final int DEATH_TIMER_DELAY = 15000;
    
    public enum GhostState{
        NORMAL, DEAD, VULNERABLE
    }

    public Ghost(Cell cell, GameEventListener gameEventListener, int speed, Strategy strategy, Color color) {
        super(cell, gameEventListener, speed);
        this.strategy = strategy;
        this.color = color;
        initialStrategy = strategy;
        state = GhostState.NORMAL;

        ActionListener vulnerabilityTimerAction = new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vulnerabilityTimerActionPerformed(evt);
            }
        };

        ActionListener deathTimerAction = new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deathTimerActionPerformed(evt);
            }
        };

        vulnerabilityTimer = new Timer(VULTIMER_DELAY, vulnerabilityTimerAction);
        deathTimer = new Timer(DEATH_TIMER_DELAY, deathTimerAction);
    }

    /**
     * Draw this Ghost
     *
     * @param g
     */
    public void draw(Graphics g) {
        if (state == GhostState.VULNERABLE) {
            g.setColor(Color.BLUE);
        } else if (state == GhostState.DEAD) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(color);
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
        Cell nextCell = strategy.giveNextCell(cell);
        if (nextCell != null) {
            nextCell.addElement(this);
            cell.removeElement(this);
            setCell(nextCell);
        }

    }

    /**
     * Looks for GameElements that are in the same cell and interacts with them
     * accordingly.
     */
//    protected void checkCollisions() {
//        for (GameElement e : cell.getElements()) {
//            if (e instanceof Pacman) {
//                interactWithPacman((Pacman) e);
//                break;
//            }
//        }
//
//    }

    /**
     * Interact with Pacman. If Pacman is invincible then the ghost will die. If
     * not then Pacman will die.
     *
     * @param pacman
     */
//    private void interactWithPacman(Pacman pacman) {
//        if (state == GhostState.VULNERABLE) {
//            dead();
//            gameEventListener.pacmanEatsGhost(this);
//        } else if (state == GhostState.NORMAL){
//            gameEventListener.pacmanDied(pacman);
//        }
//    }

    /**
     * Change current strategy to FleeStrategy and lowers the speed of this
     * Ghost by 50% This is called when Pacman eats a superPellet.
     */
    public void runFromPacman() {

        if (state == GhostState.VULNERABLE) {
            vulnerabilityTimer.restart();
        } else if (state == GhostState.NORMAL){
            this.strategy = new FleeStrategy();
            state = GhostState.VULNERABLE;
            setSpeed((int) (speed * 1.50));
            vulnerabilityTimer.start();
        }

    }

    /**
     * Revert back to the initial Strategy and initial speed and stops.
     */
    public void backToNormal() {
        strategy = initialStrategy;
        state = GhostState.NORMAL;
        setSpeed(speed);
        vulnerabilityTimer.stop();
        deathTimer.stop();
    }

    public void dead() {
        setSpeed(speed);
        strategy = new ReturnHomeSrategy(startCell);
        state = GhostState.DEAD;
        deathTimer.start();
    }
    
    public void resetGhost(){
        cell.getElements().remove(this);
        cell = startCell;
        cell.addElement(this);
        backToNormal();
    }
    
    public GhostState getState(){
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
        gameEventListener.gameElementPerfomedAction(this);
    }

    private void vulnerabilityTimerActionPerformed(ActionEvent evt) {
        if(state == GhostState.VULNERABLE){
            backToNormal();
        }
        if(state == GhostState.DEAD){
            deathTimer.restart();
        }
        vulnerabilityTimer.stop();
    }

    private void deathTimerActionPerformed(ActionEvent evt) {
        backToNormal();
        deathTimer.stop();
    }

}
