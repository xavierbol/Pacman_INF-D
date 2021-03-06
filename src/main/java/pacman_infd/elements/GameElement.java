/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.elements;

import pacman_infd.games.Cell;
import pacman_infd.listeners.ElementEventListener;

import java.awt.*;
import java.util.Objects;


/**
 * @author ivanweller
 */
public abstract class GameElement {
    protected Cell cell;
    protected ElementEventListener elementEventListener;

    /**
     * Default constructor.
     */
    public GameElement() {
    }

    /**
     * Constructor of this class.
     *
     * @param cell the initial position of the game element.
     * @param elementEventListener the element event listener.
     */
    public GameElement(Cell cell, ElementEventListener elementEventListener) {
        this.cell = cell;
        this.elementEventListener = elementEventListener;
        cell.setStaticElement(this);
    }

    /**
     * Returns the position of the GameElement.
     *
     * @return The point representing the position of the game element
     */
    protected Point getPosition() {
        return new Point(cell.getXPos() * cell.getSize(), cell.getYPos() * cell.getSize());
    }

    /**
     * Draw this GameElement. Must be implemented in all child classes.
     *
     * @param g Graphic object
     */
    public abstract void draw(Graphics g);

    /**
     * returns the cell containing this GameElement.
     *
     * @return cell
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * Set the cell of this GameElement, effectively repositions it.
     *
     * @param cell target cell
     */
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameElement that = (GameElement) o;
        return cell.equals(that.cell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cell);
    }
}
