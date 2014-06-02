/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.Elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import pacman_infd.Cell;
import pacman_infd.GameElement;
import pacman_infd.GameEventListener;

/**
 *
 * @author Marinus
 */
public abstract class MovingGameElement extends GameElement{

    protected Cell startCell;
    private Timer timer;
    protected int speed;
    
    public MovingGameElement(Cell cell, GameEventListener gameEventListener, int speed) {
        super(cell, gameEventListener);
        startCell = cell;
        this.speed = speed;
        
        ActionListener moveTimerActionListener = new java.awt.event.ActionListener(){

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveTimerActionPerformed(evt);
            }

        };
        
        timer = new Timer(speed, moveTimerActionListener);
        timer.start();
    }
    
    protected abstract void move();
    
    protected abstract void checkCollisions();

    public abstract void moveTimerActionPerformed(ActionEvent e); 
    
    protected Cell getStartCell(){
        return startCell;
    }
    
    public void setSpeed(int speed)
    {
        timer.setDelay(speed);
    }
    

    
}
