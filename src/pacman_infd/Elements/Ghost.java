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
import pacman_infd.Cell;
import pacman_infd.GameElement;
import pacman_infd.Strategy;
import javax.swing.Timer;
import pacman_infd.GameEventListener;

/**
 *
 * @author Marinus
 */
public class Ghost extends GameElement implements ActionListener{
    
    private Strategy strategy;
    private Timer timer;
    
    public Ghost(Cell cell, GameEventListener gameEventListener, Strategy strategy)
    {
        super(cell, gameEventListener);
        this.strategy = strategy;
        
        int delay = 100;
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void draw(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRoundRect(
                (int)getPosition().getX(), 
                (int)getPosition().getY(), 
                getCell().getSize(), 
                getCell().getSize(), 
                10, 5
        );
    }
    
    private void move()
    {
           setCell(strategy.giveNextCell(getCell()));
           checkCollisions();
    }
    
    private void checkCollisions()
    {
        for(GameElement e : getCell().getElements())
        {
            if(e instanceof Pacman){
                Pacman pacman = (Pacman)e;
                if(pacman.isInvincible()){
                    // die
                }
                else{
                    getCell().removeElement(e);
                    gameEventListener.pacmanDied(pacman);
                    break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        gameEventListener.gameElementPerfomedAction(this);
    }
     
}
