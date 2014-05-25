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
 * @author ivanweller
 */
public class Pellet extends GameElement {

    public Pellet(Cell cell) {
        super(cell, null);
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval(
                (int)getPosition().getX() + getCell().getSize()/ 2 - 5, 
                (int)getPosition().getY() + getCell().getSize()/ 2 - 5, 
                10, 10
        );
    }

    public void collect() {

    }

}
