/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.Elements;

import java.awt.Color;
import java.awt.Graphics;
import pacman_infd.Cell;
import pacman_infd.Direction;
import pacman_infd.GameElement;
import pacman_infd.GameEventListener;

/**
 *
 * @author Marinus
 */
public class OneWayWall extends GameElement {

    Direction passableDirection;
    
    public OneWayWall(Cell cell,Direction passableDirection) {
        super(cell, null);
        this.passableDirection = passableDirection;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.drawLine(
                (int)getPosition().getX(),
                (int)getPosition().getY() + 13,
                (int)getPosition().getX() + cell.getSize(), 
                (int)getPosition().getY() + 13
        );
    }
    
}
