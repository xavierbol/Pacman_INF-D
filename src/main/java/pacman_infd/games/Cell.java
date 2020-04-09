/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.games;

import pacman_infd.elements.*;
import pacman_infd.enums.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Marinus
 */
public class Cell {
    private int xPos;
    private int yPos;
    private int size;

    private Map<Direction, Cell> neighbors;
    private ArrayList<MovingGameElement> movingElements;
    private GameElement staticElement;
    private boolean fruitSpawn;

    private Cell pathParent;

    public Cell(int x, int y, int size) {
        xPos = x;
        yPos = y;
        this.size = size;
        this.fruitSpawn = false;

        movingElements = new ArrayList<>();
        neighbors = new HashMap<Direction, Cell>();
    }

    /**
     * Draw this cell and all elements in it.
     *
     * @param g
     */
    public void draw(Graphics g) {
        drawElements(g);
    }

    /**
     * Draw all elements contained by this cell.
     *
     * @param g Graphics object
     */
    private void drawElements(Graphics g) {
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
        return staticElement instanceof Wall;
    }

    public void clearCell() {
        movingElements = null;
        staticElement = null;
    }

    /**
     * Add a GameElement to this cell.
     *
     * @param e GameElement
     */
    public void addMovingElement(MovingGameElement e) {
        movingElements.add(e);
    }

    /**
     * Remove a GameElement from this cell.
     *
     * @param e GameElement
     */
    public void removeMovingElement(MovingGameElement e) {
        movingElements.remove(e);
    }

    /**
     * @return a list of all GameElements on this cell.
     */
    public ArrayList<MovingGameElement> getMovingElements() {
        return movingElements;
    }

    public boolean containsPacman() {
        for (GameElement e : this.movingElements) {
            if (e instanceof Pacman) {
                //pacman found
                return true;
            }
        }

        return false;
    }

    public ArrayList<Eatable> getAllEatableElements() {
        ArrayList<Eatable> eatables = new ArrayList<>();
        if (staticElement instanceof Eatable) {
            eatables.add((Eatable) staticElement);
        }

        for (MovingGameElement element : movingElements) {
            if (element instanceof Eatable) {
                eatables.add((Eatable) element);
            }
        }

        return eatables;
    }

    /**
     * Set the neighboring cells for this cell
     *
     * @param dir
     * @param cell
     */
    public void setNeighbor(Direction dir, Cell cell) {
        neighbors.put(dir, cell);
    }

    /**
     * @param dir Direction of cell
     * @return Cell neighbor of this cell in the direction specified.
     */
    public Cell getNeighbor(Direction dir) {
        return neighbors.get(dir);
    }

    /**
     * @return a HashMap of neighboring Cells.
     */
    public Map<Direction, Cell> getNeighbors() {
        return neighbors;
    }

    /**
     * @return staticElement
     */
    public GameElement getStaticElement() {
        return staticElement;
    }

    /**
     * @param element
     */
    public void setStaticElement(GameElement element) {
        staticElement = element;
    }

    /**
     * @return the size of this cell.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the x position of this cell.
     */
    public int getXpos() {
        return xPos;
    }

    /**
     * @return the y position of this cell.
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Returns if the cell can serve as a spawn for a fruit.
     *
     * @return True if a fruit can spawn in cell.
     */
    public boolean isFruitSpawn() {
        return fruitSpawn;
    }

    /**
     * Sets if the cell can serve as a spawn for a fruit.
     *
     * @param fruitSpawn True if a fruit can spawn in cell.
     */
    public void setFruitSpawn(boolean fruitSpawn) {
        this.fruitSpawn = fruitSpawn;
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
     * @return parent of this Cell in a path
     */
    public Cell getPathParent() {
        return pathParent;
    }

    @Override
    public String toString() {
        return "xPos: " + xPos + "\nYPos: " + yPos + "\nNumber of Neighbors: " + neighbors.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return xPos == cell.xPos &&
                yPos == cell.yPos &&
                size == cell.size &&
                neighbors.equals(cell.neighbors) &&
                movingElements.equals(cell.movingElements) &&
                Objects.equals(staticElement, cell.staticElement) &&
                Objects.equals(pathParent, cell.pathParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos, size, neighbors, movingElements, staticElement, pathParent);
    }
}
