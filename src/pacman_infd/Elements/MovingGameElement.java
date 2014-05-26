/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.Elements;

import java.awt.Graphics;
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
public abstract class MovingGameElement extends GameElement implements ActionListener{

    private Cell startCell;
    private Timer timer;
    
    public MovingGameElement(Cell cell, GameEventListener gameEventListener, int speed) {
        super(cell, gameEventListener);
        startCell = cell;
        timer = new Timer(speed, this);
        timer.start();   
    }
    
    //public abstract void move();
    
    protected abstract void checkCollisions();

    @Override
    public abstract void draw(Graphics g); 

    @Override
    public abstract void actionPerformed(ActionEvent e); 
    
    protected Cell getStartCell(){
        return startCell;
    }
    
}
