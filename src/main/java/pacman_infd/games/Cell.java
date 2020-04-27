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
    private final int xPos;
    private final int yPos;
    private final int size;

    private final Map<Direction, Cell> neighbors;
    private ArrayList<MovingGameElement> movingElements;
    private GameElement staticElement;
    private boolean fruitSpawn;

    private Cell pathParent;

    /**
     * Creates a cell of the board game.
     * @param x     the axis-x position of this cell in the board game.
     * @param y     the axis y position of this cell in the board game.
     * @param size  the size of this cell.
     */
    public Cell(int x, int y, int size) {
        xPos = x;
        yPos = y;
        this.size = size;
        this.fruitSpawn = false;

        movingElements = new ArrayList<>();
        neighbors = new HashMap<>();
    }

    /**
     * Draw this cell and all elements in it.
     *
     * @param g Graphics object to draw the elements contained on this cell.
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
     */
    public boolean hasWall() {
        return staticElement instanceof Wall;
    }

    /**
     * Deletes all the elements contained in the cell.
     */
    public void clearCell() {
        movingElements = null;
        staticElement = null;
    }

    /**
     * Add a moving game element to this cell.
     *
     * @param e the moving game element to add on this cell.
     */
    public void addMovingElement(MovingGameElement e) {
        movingElements.add(e);
    }

    /**
     * Remove a moving game element from this cell.
     *
     * @param e the moving game element.
     */
    public void removeMovingElement(MovingGameElement e) {
        movingElements.remove(e);
    }

    /**
     * Returns the list containing the moving elements on this cell.
     *
     * @return a list of all GameElements on this cell.
     */
    public ArrayList<MovingGameElement> getMovingElements() {
        return movingElements;
    }

    /**
     * Check if Pacman is on this cell.
     *
     * @return true if Pacman is on this cell, otherwise return false.
     */
    public boolean containsPacman() {
        for (GameElement e : this.movingElements) {
            if (e instanceof Pacman) {
                //pacman found
                return true;
            }
        }
        return false;
    }

    /**
     * Return all eatable elements on this cells.
     *
     * @return all eatable elements on this cells.
     */
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
     * Returns all the game elements contained in the cell.
     *
     * @return The game elements contained in the cell
     */
    public ArrayList<GameElement> getAllGameElements() {
        ArrayList<GameElement> gameElements = new ArrayList<>(movingElements);
        gameElements.add(staticElement);
        return gameElements;
    }

    /**
     * Set the neighboring cells for this cell
     *
     * @param dir the direction of the neighbor cell.
     * @param cell the neighbor cell.
     */
    public void setNeighbor(Direction dir, Cell cell) {
        if (cell != null) {
            neighbors.put(dir, cell);
        }
    }

    /**
     * Get the neighbor cell based on the direction given in parameter.
     *
     * @param dir Direction of cell.
     * @return Cell neighbor of this cell in the direction specified.
     */
    public Cell getNeighbor(Direction dir) {
        return neighbors.get(dir);
    }

    /**
     * Get the neighboring cells.
     *
     * @return a HashMap of neighboring Cells.
     */
    public Map<Direction, Cell> getNeighbors() {
        return neighbors;
    }

    /**
     * Get the static element on this cell.
     *
     * @return the static element on this cell.
     */
    public GameElement getStaticElement() {
        return staticElement;
    }

    /**
     * Set the static element on this cell.
     *
     * @param element the static element to set
     */
    public void setStaticElement(GameElement element) {
        staticElement = element;
    }

    /**
     * Get the size of this cell.
     *
     * @return the size of this cell.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the x position of this cell.
     *
     * @return the x position of this cell.
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Get the y position of this cell.
     *
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
     * Get the parent of this cell in a path.
     *
     * @return parent of this Cell in a path.
     */
    public Cell getPathParent() {
        return pathParent;
    }

    @Override
    public String toString() {
        return "(" + xPos + ", " + yPos + ")";
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
        return Objects.hash(xPos, yPos);
    }
}
