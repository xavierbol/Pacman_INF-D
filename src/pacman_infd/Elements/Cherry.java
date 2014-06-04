/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.Elements;

import java.awt.Color;
import java.awt.Graphics;
import pacman_infd.Cell;
import pacman_infd.GameElement;


/**
 *
 * @author Marinus
 */
public class Cherry extends GameElement{

    public Cherry(Cell cell) {
        super(cell, null);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawOval(
                (int)getPosition().getX(),
                (int)getPosition().getY(), 
                15, 
                15
        );
    }
    
}
