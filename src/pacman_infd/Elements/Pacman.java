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
import javax.swing.Timer;
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

    public void move(Direction direction) {
        Cell moveTo = getCell().getNeighbor(direction);
        if (moveTo != null && !moveTo.hasWall()) {
            moveTo.addElement(this);
            getCell().removeElement(this);
            setCell(moveTo);
            
            checkCollisions();    
        }
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.ORANGE);
        g.fillOval(
                (int)getPosition().getX(), 
                (int)getPosition().getY(), 
                getCell().getSize(), 
                getCell().getSize()
        );
    }
    
    protected void checkCollisions()
    {
        for(GameElement e : getCell().getElements())
        {
            if(e instanceof Pellet){
                getCell().removeElement(e);
                gameEventListener.pacmanFoundPellet();
                break;
            }
            if(e instanceof SuperPellet) {
                getCell().removeElement(e);
                becomeInvincible();
                gameEventListener.pacmanFoundSuperPellet();
                break;
            }
        }
    }
    
    public void resetPacman()
    {
        setCell(getStartCell());
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
        if (!keyPressed) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if(!getCell().getNeighbor(Direction.UP).hasWall()){  
                        currentDirection = Direction.UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(!getCell().getNeighbor(Direction.DOWN).hasWall()){
                        currentDirection = Direction.DOWN;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(!getCell().getNeighbor(Direction.LEFT).hasWall()){
                        currentDirection = Direction.LEFT;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(!getCell().getNeighbor(Direction.RIGHT).hasWall()){
                        currentDirection = Direction.RIGHT;
                    }
                    break;
            }
            gameEventListener.pacmanMoved();
            keyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(currentDirection);
    }

}
