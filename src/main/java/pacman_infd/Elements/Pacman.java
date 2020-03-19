/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.Elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman_infd.Games.Cell;
import pacman_infd.Enums.Direction;
import pacman_infd.Listeners.ElementEventListener;

/**
 *
 * @author ivanweller
 */
public class Pacman extends MovingGameElement implements KeyListener {
    private Direction currentDirection;

    public Pacman(Cell cell, ElementEventListener gameEventListener, int speed) {
        super(cell, gameEventListener, speed);
    }

    /**
     * Move to the next cell according to currentDirection. Will not move if the
     * next cell has a wall. Check for collisions after move is complete.
     */
    @Override
    protected void move() {
        Cell moveTo = cell.getNeighbor(currentDirection);
        if (moveTo != null && !moveTo.hasWall()) {
            moveTo.addMovingElement(this);
            cell.removeMovingElement(this);
            setCell(moveTo);
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
     * @param g
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
        Direction newDirection = Direction.getDirection(e, currentDirection);

        if (cell.getNeighbor(newDirection) != null && !cell.getNeighbor(newDirection).hasWall()) {
            currentDirection = newDirection;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
