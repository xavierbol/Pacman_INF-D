package pacman_infd.elements;

import org.junit.jupiter.api.Test;
import pacman_infd.enums.Direction;

public class CherryTest extends GameElementTest {
    @Test
    public void testCreateCherry() {
        assert (gameWorld.getCell(1,0).getAllEatableElements().size() == 0);
        new Cherry(gameWorld.getCell(1,0), gameWorld.getEventHandler());

        assert (gameWorld.getCell(1,0).getAllEatableElements().size() > 0);
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof Cherry);
    }

    @Test
    public void testEatMe() {
        Pacman pacman = new Pacman(gameWorld.getCell(0,0), gameWorld.getEventHandler(), 0);
        new Cherry(gameWorld.getCell(1,0), gameWorld.getEventHandler());

        // Check if the pacman and cherry are created and correctly placed
        assert (gameWorld.getCell(0,0).getMovingElements().size() == 1);
        assert (gameWorld.getCell(0,0).getMovingElements().get(0) instanceof Pacman);
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof Cherry);

        pacman.changeDirection(Direction.RIGHT);
        pacman.move();

        gameWorld.getEventHandler().movingElementActionPerformed(pacman);

        assert (pacman.getCell().getXpos() == 1 && pacman.getCell().getYPos() == 0);
        assert (pacman.getCell().getStaticElement() == null);
        assert (scorePanel.getScore() == 100);
    }
}

