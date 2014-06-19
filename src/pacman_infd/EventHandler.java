/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.util.ArrayList;
import pacman_infd.Elements.Ghost;
import pacman_infd.Elements.MovingGameElement;
import pacman_infd.Elements.Pacman;

/**
 *
 * @author Marinus
 */
public class EventHandler implements ElementEventListener {

    GameEventListener gameEventListener;
    GameWorld gameWorld;

    public EventHandler(GameEventListener gameEventListener, GameWorld gameWorld) {
        this.gameEventListener = gameEventListener;
        this.gameWorld = gameWorld;
    }

    private void checkCollisions(Ghost g) {
        Cell cell = g.getCell();

        boolean pacmanFound = false;
        for (MovingGameElement element : cell.getMovingElements()) {
            if (element instanceof Pacman) {
                pacmanFound = true;
            }
        }
        if (pacmanFound) {
            g.eatMe();
        }
    }

    private void checkCollisions(Pacman p) {

        Cell cell = p.getCell();

        ArrayList<Eatable> eatables = new ArrayList();

        if (cell.getStaticElement() instanceof Eatable) {
            Eatable element = (Eatable) cell.getStaticElement();
            eatables.add(element);
        }

        for (MovingGameElement element : cell.getMovingElements()) {
            if (element instanceof Eatable) {
                Eatable eatable = (Eatable) element;
                eatables.add(eatable);
            }
        }

        for (Eatable eatable : eatables) {
            eatable.eatMe();
        }

    }

    @Override
    public void movingElementActionPerformed(MovingGameElement e) {
        if (e instanceof Pacman) {
            checkCollisions((Pacman) e);
            gameEventListener.refocus();
        } else if (e instanceof Ghost) {
            checkCollisions((Ghost) e);
        }

    }

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
        for (Cell cell : gameWorld.getCells()) {
            for (MovingGameElement mover : cell.getMovingElements()) {
                movers.add(mover);
            }
        }
        for (MovingGameElement mover : movers) {
            mover.reset();
        }

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
