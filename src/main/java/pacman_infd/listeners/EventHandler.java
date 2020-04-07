/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pacman_infd.games.Cell;
import pacman_infd.elements.Eatable;
import pacman_infd.elements.Ghost;
import pacman_infd.elements.MovingGameElement;
import pacman_infd.elements.Pacman;
import pacman_infd.games.GameWorld;

/**
 *
 * @author Marinus
 */
public class EventHandler implements ElementEventListener {
    private GameEventListener gameEventListener;
    private GameWorld gameWorld;

    public EventHandler(GameEventListener gameEventListener, GameWorld gameWorld) {
        this.gameEventListener = gameEventListener;
        this.gameWorld = gameWorld;
    }

    /**
     * Checks if ghost is in the same cell as pacman
     * @param g 
     */
    private void checkCollisions(Ghost g) {
        Cell cell = g.getCell();

        if (cell.containsPacman()) {
            g.eatMe();
        }
    }

    /**
     * checks if pacman found any eatable object.
     * @param p 
     */
    private void checkCollisions(Pacman p) {
        Cell cell = p.getCell();

        ArrayList<Eatable> eatables = cell.getAllEatableElements();

        for (Eatable eatable : eatables) {
            eatable.eatMe();
        }
    }

    /**
     * This is called whenever a moving gameElement has moved.
     * @param e 
     */
    @Override
    public void movingElementActionPerformed(MovingGameElement e) {
        if (e instanceof Pacman) {
            checkCollisions((Pacman) e);
            gameEventListener.refocus();
        } else if (e instanceof Ghost) {
            checkCollisions((Ghost) e);
        }
    }

    /**
     * this is called whenever an eatable object has been eaten.
     * @param e 
     */
    @Override
    public void eatableElementEaten(Eatable e) {
        gameEventListener.increasePoints(e.getValue());
        gameWorld.placeCherryOnRandomEmptyCell();
    }

    @Override
    public void killPacman() {
        gameEventListener.decreaseLife();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ArrayList<MovingGameElement> movers = new ArrayList();

        List<Cell> listCellsWithMovingElements = gameWorld.getCells().parallelStream().filter(cell -> !cell.getMovingElements().isEmpty()).collect(Collectors.toList());

        for (Cell cell : listCellsWithMovingElements) {
            movers.addAll(cell.getMovingElements());
        }
        movers.parallelStream().forEach(MovingGameElement::reset);
    }

    @Override
    public void makeGhostsVulnerable() {
        for (Cell cell : gameWorld.getCells()) {
            for (MovingGameElement element : cell.getMovingElements()) {
                if (element instanceof Ghost) {
                    Ghost ghost = (Ghost) element;
                    ghost.flee();
                }
            }
        }
    }
}
