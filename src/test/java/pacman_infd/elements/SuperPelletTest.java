package pacman_infd.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.enums.Direction;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

class SuperPelletTest extends GameElementTest {
    Pacman pacman;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        pacman = new Pacman(gameWorld.getCell(0,0), gameWorld.getEventHandler(), 0, new KeyControlledStrategy(gameWorld));
    }

    @Test
    public void testCreate() {
        assert (gameWorld.getCell(1,0).getStaticElement() == null);
        new SuperPellet(gameWorld.getCell(1,0), gameWorld.getEventHandler());
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof SuperPellet);
    }

    @Test
    public void testEatMe() {
        new SuperPellet(gameWorld.getCell(1,0), gameWorld.getEventHandler());
        assert (gameWorld.getCell(1,0).getStaticElement() instanceof SuperPellet);

        pacman.changeDirection(Direction.RIGHT);
        pacman.move();
        gameWorld.getEventHandler().movingElementActionPerformed(pacman);

        assert (gameWorld.getCell(1,0).getStaticElement() == null);
        assert (scorePanel.getScore() == 50);
    }
}