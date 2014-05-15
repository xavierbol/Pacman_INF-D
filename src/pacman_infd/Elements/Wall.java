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
public class Wall extends GameElement{

    public Wall(Cell cell) {
        super(cell);
    }

    @Override
    public void draw(Graphics g) {
        int size = getCell().getSize();
        int x = getCell().getXpos() * size;
        int y = getCell().getYPos() * size;

        g.setColor(Color.BLACK);
        g.fillRect(x, y, size, size);
    }
    
}
