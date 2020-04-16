package pacman_infd.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.enums.Direction;
import pacman_infd.enums.ElementType;
import pacman_infd.enums.GhostState;
import pacman_infd.games.Cell;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

import java.awt.*;
import java.util.concurrent.TimeUnit;

class GhostTest extends GameElementTest {
    @Test
    public void testCreateGhosts() {
        gameWorld.createGhost(ElementType.BLINKY_GHOST, gameWorld.getCell(0,0));
        gameWorld.createGhost(ElementType.CLYDE_GHOST, gameWorld.getCell(0,1));
        gameWorld.createGhost(ElementType.INKY_GHOST, gameWorld.getCell(1,0));
        gameWorld.createGhost(ElementType.PINKY_GHOST, gameWorld.getCell(1,1));

        for (Cell cell : gameWorld.getCells()) {
            assert (cell.getMovingElements().get(0) instanceof Ghost);
        }

        Ghost blinky = (Ghost) gameWorld.getCell(0,0).getMovingElements().get(0);
        Ghost clyde = (Ghost) gameWorld.getCell(0,1).getMovingElements().get(0);
        Ghost inky = (Ghost) gameWorld.getCell(1,0).getMovingElements().get(0);
        Ghost pinky = (Ghost) gameWorld.getCell(1,1).getMovingElements().get(0);

        assert (blinky.getColor().equals(Color.RED));
        assert (clyde.getColor().equals(Color.ORANGE));
        assert (inky.getColor().equals(Color.CYAN));
        assert (pinky.getColor().equals(Color.PINK));
    }

    @Test
    public void testEatMe() {
        gameWorld.createGhost(ElementType.CLYDE_GHOST, gameWorld.getCell(1,1));
        Ghost clyde = (Ghost) gameWorld.getCell(1,1).getMovingElements().get(0);
        assert (clyde.getState().equals(GhostState.NORMAL));

        Pacman pacman = new Pacman(gameWorld.getCell(0,0), gameWorld.getEventHandler(), 0, new KeyControlledStrategy(gameWorld));
        new SuperPellet(gameWorld.getCell(1,0), gameWorld.getEventHandler());
        pacman.changeDirection(Direction.RIGHT);
        pacman.move();
        gameWorld.getEventHandler().movingElementActionPerformed(pacman);

        assert (pacman.getCell().getXPos() == 1 && pacman.getCell().getStaticElement() == null);
        assert (pacman.getCell().getStaticElement() == null);
        assert (clyde.getState().equals(GhostState.VULNERABLE));

        pacman.changeDirection(Direction.DOWN);
        pacman.move();
        gameWorld.getEventHandler().movingElementActionPerformed(pacman);

        assert (pacman.getCell().getYPos() == 1);
        assert (clyde.getState().equals(GhostState.DEAD));
        assert (scorePanel.getScore() == 250);
    }

    @Test
    public void testGhostEatsPacman() throws InterruptedException {
        gameWorld.createGhost(ElementType.CLYDE_GHOST, gameWorld.getCell(1,1));
        Ghost clyde = (Ghost) gameWorld.getCell(1,1).getMovingElements().get(0);
        assert (scorePanel.getLives() == 3);
        assert (clyde.getState().equals(GhostState.NORMAL));

        Pacman pacman = new Pacman(gameWorld.getCell(0,0), gameWorld.getEventHandler(), 0, new KeyControlledStrategy(gameWorld));
        pacman.changeDirection(Direction.RIGHT);
        pacman.move();
        gameWorld.getEventHandler().movingElementActionPerformed(pacman);

        TimeUnit.SECONDS.sleep(1); // we need to wait a few moment, the time to reset the game and decrease the life of pacman
        assert (scorePanel.getLives() == 2);
    }
}