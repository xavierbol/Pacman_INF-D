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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman_infd.Cell;
import pacman_infd.Direction;
import pacman_infd.GameElement;
import pacman_infd.GameEventListener;

/**
 *
 * @author ivanweller
 */
public class Pacman extends MovingGameElement implements ActionListener, KeyListener {

    private boolean isInvincible;
    private boolean keyPressed;
    private Direction currentDirection;

    public Pacman(Cell cell, GameEventListener gameEventListener, int speed) {
        super(cell, gameEventListener, speed);
        keyPressed = false;
        isInvincible = false;   
    }

    /**
     * Move to the next cell accoring to currentDirection. Will not move if the
     * next cell has a wall.
     * Check for collisions after move is complete.
     */
    public void move() {
        Cell moveTo = cell.getNeighbor(currentDirection);
        if (moveTo != null && !moveTo.hasWall()) {
            moveTo.addElement(this);
            cell.removeElement(this);
            setCell(moveTo);
            
  
        }
    }

    /**
     * Draw Pacman.
     * @param g 
     */
    @Override
    public void draw(Graphics g) {

        g.setColor(Color.ORANGE);
        g.fillOval(
                (int)getPosition().getX() - 5, 
                (int)getPosition().getY() - 5, 
                cell.getSize() + 10, 
                cell.getSize() + 10
        );
    }
    
    /**
     * Looks for GameElements that are in the same cell and interacts with them accordingly.
     */
    @Override
    protected void checkCollisions()
    {
        for(GameElement e : cell.getElements())
        {
            if(e instanceof Pellet){
                interactWithPellet((Pellet)e);
                break;
            }
            if(e instanceof SuperPellet) {
                interactWithSuperPellet((SuperPellet)e);
                break;
            }
        }
    }

    private void interactWithSuperPellet(SuperPellet sp) {
        cell.removeElement(sp);
        becomeInvincible();
        gameEventListener.pacmanFoundSuperPellet();
    }

    private void interactWithPellet(Pellet p) {
        cell.removeElement(p);
        gameEventListener.pacmanFoundPellet();
    }
    
    public void resetPacman()
    {
        cell.removeElement(this);
        cell = getStartCell();
    }
    
    public boolean isInvincible()
    {
        return isInvincible;
    }
    
    private void becomeInvincible()
    {
        isInvincible = true;
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        //not implemented.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction newDirection = currentDirection;
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                newDirection = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                newDirection = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                newDirection = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                newDirection = Direction.RIGHT;
                break;
        }
        if(!cell.getNeighbor(newDirection).hasWall()){
            currentDirection = newDirection;
        }
        gameEventListener.pacmanMoved();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        checkCollisions();  
        gameEventListener.gameElementPerfomedAction(this);
    }

}
