/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman_infd.games.Cell;
import pacman_infd.enums.Direction;
import pacman_infd.listeners.ElementEventListener;
import pacman_infd.strategies.pacman.PacmanStrategy;

/**
 *
 * @author ivanweller
 */
public class Pacman extends MovingGameElement implements KeyListener {
    private Direction currentDirection;
    private final PacmanStrategy strategy;

    /**
     * Create the Pacman.
     *
     * @param cell the initial position of the Pacman.
     * @param gameEventListener the game listener.
     * @param speed the initial speed of the Pacman.
     * @param strategy the selected strategy for the Pacman.
     */
    public Pacman(Cell cell, ElementEventListener gameEventListener, int speed, PacmanStrategy strategy) {
        super(cell, gameEventListener, speed);
        this.strategy = strategy;
    }

    /**
     * Get the current direction of this Pacman.
     *
     * @return the current direction.
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Change the direction of Pacman.
     *
     * @param newDirection the new direction to adopt.
     */
    public void changeDirection(Direction newDirection) {
        if (cell.getNeighbor(newDirection) != null && !cell.getNeighbor(newDirection).hasWall()) {
            currentDirection = newDirection;
        }
    }

    /**
     * Move to the next cell according to currentDirection. Will not move if the
     * next cell has a wall. Check for collisions after move is complete.
     */
    @Override
    protected void move() {
        Direction nextDirection = strategy.getNextDirection(cell, currentDirection);
        if(currentDirection != nextDirection) {
            currentDirection = nextDirection;
        }
        Cell nextCell = cell.getNeighbor(nextDirection);
        if (nextCell != null && !nextCell.hasWall()) {
            nextCell.addMovingElement(this);
            cell.removeMovingElement(this);
            setCell(nextCell);
        }
    }

    @Override
    public void moveTimerActionPerformed(ActionEvent e) {
        move();
        elementEventListener.movingElementActionPerformed(this);
    }

    /**
     * Draw Pacman.
     *
     * Problem that pacman keeps the same orientation,
     * to change this we need to use Graphics2D and not Graphics
     * See <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html#rotate%28double,%20double,%20double%29">For more details</a>
     *
     * @param g the graphics object to draw the Pacman.
     */
    @Override
    public void draw(Graphics g) {
        //body
        g.setColor(Color.YELLOW);
        g.fillOval(
                (int) getPosition().getX() - 5,
                (int) getPosition().getY() - 5,
                cell.getSize() + 10,
                cell.getSize() + 10
        );
        //eye
        g.setColor(Color.WHITE);
        g.fillOval(
                (int) getPosition().getX() + 13,
                (int) getPosition().getY() - 1,
                10,
                10
        );
        //pupil
        g.setColor(Color.BLACK);
        g.fillOval(
                (int) getPosition().getX() + 16,
                (int) getPosition().getY() + 1,
                5,
                5
        );
        //mouth
        g.fillArc(
                (int) getPosition().getX() - 5,
                (int) getPosition().getY() - 5,
                cell.getSize() + 10,
                cell.getSize() + 10,
                -25,
                45
        );
    }

    @Override
    public void keyPressed(KeyEvent e) {
        changeDirection(strategy.changeDirection(e, this.currentDirection));
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
