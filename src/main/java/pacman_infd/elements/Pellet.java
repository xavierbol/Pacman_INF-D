/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.elements;

import java.awt.Color;
import java.awt.Graphics;
import pacman_infd.games.Cell;
import pacman_infd.utils.SoundManager;
import pacman_infd.listeners.ElementEventListener;

/**
 *
 * @author ivanweller
 */
public class Pellet extends GameElement implements Eatable {
    private static final int VALUE = 5;
    
    public Pellet(Cell cell, ElementEventListener evtl) {
        super(cell, evtl);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(
                (int)getPosition().getX() + getCell().getSize()/ 2 - 3, 
                (int)getPosition().getY() + getCell().getSize()/ 2 - 3, 
                6, 6
        );
    }

    @Override
    public void eatMe() {
        elementEventListener.eatableElementEaten(this);
        
        if(cell.getStaticElement().equals(this)){
            cell.setStaticElement(null);
            cell.setFruitSpawn(true);
        }

        SoundManager.playSound("chomp");
    }

    @Override
    public int getValue() {
        return VALUE;
    }
}
