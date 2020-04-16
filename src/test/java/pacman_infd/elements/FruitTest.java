package pacman_infd.elements;

import org.junit.jupiter.api.Test;
import pacman_infd.enums.Direction;
import pacman_infd.enums.FruitType;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

public class FruitTest extends GameElementTest {
    @Test
    public void testCreateFruit() {
        assert (gameWorld.getCell(1, 0).getAllEatableElements().size() == 0);
        new Fruit(FruitType.CHERRY, gameWorld.getCell(1, 0), gameWorld.getEventHandler());

        assert (gameWorld.getCell(1, 0).getAllEatableElements().size() > 0);
        assert (gameWorld.getCell(1, 0).getStaticElement() instanceof Fruit);
    }

    @Test
    public void testEatMe() {
        Pacman pacman = new Pacman(gameWorld.getCell(0, 0), gameWorld.getEventHandler(), 0, new KeyControlledStrategy(gameWorld));
        new Fruit(FruitType.CHERRY, gameWorld.getCell(1, 0), gameWorld.getEventHandler());

        // Check if the pacman and cherry are created and correctly placed
        assert (gameWorld.getCell(0, 0).getMovingElements().size() == 1);
        assert (gameWorld.getCell(0, 0).getMovingElements().get(0) instanceof Pacman);
        assert (gameWorld.getCell(1, 0).getStaticElement() instanceof Fruit);

        pacman.changeDirection(Direction.RIGHT);
        pacman.move();

        gameWorld.getEventHandler().movingElementActionPerformed(pacman);

        assert (pacman.getCell().getXPos() == 1 && pacman.getCell().getYPos() == 0);
        assert (pacman.getCell().getStaticElement() == null);
        assert (scorePanel.getScore() == 100);
    }
}

