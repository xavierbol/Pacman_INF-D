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
import pacman_infd.Cell;
import pacman_infd.Direction;
import pacman_infd.GameEventListener;

/**
 *
 * @author ivanweller
 */
public class Pacman extends MovingGameElement implements KeyListener {

    private Direction currentDirection;

    public Pacman(Cell cell, GameEventListener gameEventListener, int speed) {
        super(cell, gameEventListener, speed);

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

        g.setColor(Color.YELLOW);
        g.fillOval(
                (int)getPosition().getX() - 5, 
                (int)getPosition().getY() - 5, 
                cell.getSize() + 10, 
                cell.getSize() + 10
        );
        g.setColor(Color.WHITE);
        g.fillOval(
                (int)getPosition().getX() + 13, 
                (int)getPosition().getY() - 1, 
                10, 
                10
        );        
        g.setColor(Color.BLACK);
        g.fillOval(
                (int)getPosition().getX() + 16, 
                (int)getPosition().getY() + 1, 
                5, 
                5
        ); 
        g.fillArc(
                (int)getPosition().getX() - 5, 
                (int)getPosition().getY() - 5, 
                cell.getSize() + 10, 
                cell.getSize() + 10, 
                -25, 
                45
        );

    }
    
//    /**
//     * Looks for GameElements that are in the same cell and interacts with them accordingly.
//     */
//    protected void checkCollisions()
//    {
//        GameElement e = cell.getStaticElement();
//        if(e instanceof Pellet){
//            interactWithPellet((Pellet)e);
//        }
//        else if(e instanceof SuperPellet) {
//            interactWithSuperPellet((SuperPellet)e);
//        }
//        else if(e instanceof Cherry){
//            interactWithCherry((Cherry)e);
//        }
//
//    }
//
//    private void interactWithSuperPellet(SuperPellet sp) {
//        cell.setStaticElement(null);
//        gameEventListener.pacmanFoundSuperPellet();
//    }
//
//    private void interactWithPellet(Pellet p) {
//        cell.setStaticElement(null);
//        gameEventListener.pacmanFoundPellet();
//    }
//    
//    private void interactWithCherry(Cherry c){
//        cell.setStaticElement(null);
//        gameEventListener.pacmanFoundCherry();
//    }
    
    public void resetPacman()
    {
        cell.removeElement(this);
        cell = startCell;
        cell.addElement(this);
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        //Do nothing
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
        //gameEventListener.pacmanMoved();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Do nothing
    }

    @Override
    public void moveTimerActionPerformed(ActionEvent e) {
        move();
        //checkCollisions();  
        gameEventListener.pacmanActionPerformed(this);
    }

}
