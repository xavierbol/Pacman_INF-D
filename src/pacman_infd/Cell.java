/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import pacman_infd.Elements.MovingGameElement;
import pacman_infd.Elements.Wall;

/**
 *
 * @author Marinus
 */
public class Cell {

    private int xPos;
    private int yPos;
    private int size;

    private Map<Direction, Cell> neighbors;
    private ArrayList<MovingGameElement> movingElements;
    private GameElement staticElement;

    private Cell pathParent;

    public Cell(int x, int y, int size) {
        xPos = x;
        yPos = y;
        this.size = size;

        movingElements = new ArrayList<>();
        neighbors = new HashMap<Direction, Cell>();
    }

    public void draw(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.drawRect(xPos * size, yPos * size, size, size);
        drawElements(g);
    }

    /**
     * Draw all movingElements contained by this cell.
     *
     * @param g Graphics object
     */
    public void drawElements(Graphics g) {
        if (staticElement != null) {
            staticElement.draw(g);
        }
        if (movingElements != null) {
            for (GameElement element : movingElements) {
                element.draw(g);
            }
        }
    }

    /**
     * Returns true if one of the movingElements on this Cell is a Wall.
     *
     * @return
     */
    public boolean hasWall() {
        boolean hasWall = false;

        if (staticElement instanceof Wall) {
            hasWall = true;
        }

        return hasWall;
    }

    /**
     * Add a GameElement to this cell.
     *
     * @param e GameElement
     */
    public void addElement(MovingGameElement e) {
        movingElements.add(e);
    }

    /**
     * Remove a GameElement from this cell.
     *
     * @param e GameElement
     */
    public void removeElement(MovingGameElement e) {
        if (movingElements.contains(e)) {
            movingElements.remove(e);
        }
    }

    /**
     *
     * @return a list of all GameElements on this cell.
     */
    public ArrayList<MovingGameElement> getElements() {
        return movingElements;
    }

    /**
     * Set the neighboring cells for this cell
     *
     * @param neighbors List of neighbor cells.
     */
    public void setNeighbor(Direction dir, Cell cell) {
        neighbors.put(dir, cell);
    }

    /**
     *
     * @param dir Direction of cell
     * @return Cell neighbor of this cell in the direction specified.
     */
    public Cell getNeighbor(Direction dir) {
        return neighbors.get(dir);
    }

    /**
     *
     * @return a HashMap of neighboring Cells.
     */
    public Map getNeighbors() {
        return neighbors;
    }

    public GameElement getStaticElement() {
        return staticElement;
    }

    public void setStaticElement(GameElement element) {
        staticElement = element;
    }

    /**
     *
     * @return the size of this cell.
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return the x position of this cell.
     */
    public int getXpos() {
        return xPos;
    }

    /**
     *
     * @return the y position of this cell.
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Set the parent for this Cell. This is used by the PathFinder class to
     * create a path between two cells.
     *
     * @param cell parent of this Cell.
     */
    public void setPathParent(Cell cell) {
        pathParent = cell;
    }

    /**
     *
     * @return parent of this Cell in a path
     */
    public Cell getPathParent() {
        return pathParent;
    }

    @Override
    public String toString() {
        return "xPos: " + xPos + "\nYPos: " + yPos + "\nNumber of Neighbors: " + neighbors.size();
    }
}
