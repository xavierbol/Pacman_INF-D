package pacman_infd.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.elements.Pacman;
import pacman_infd.games.Cell;
import pacman_infd.strategies.ghost.FleeStrategy;
import pacman_infd.strategies.pacman.KeyControlledStrategy;

class FleeStrategyTest extends GhostStrategyTest {
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        ghostStrategy = new FleeStrategy();
    }

    @Test
    public void testGiveNextCell() {
        Pacman pacman = new Pacman(gameWorld.getCell(0,0), null, 100, new KeyControlledStrategy(gameWorld));

        // Let's imagine that the ghost is in (1,0) cell and he is afraid, then he must move in Down direction
        Cell cell = ghostStrategy.giveNextCell(gameWorld.getCell(1,0));

        assert (!cell.equals(pacman.getCell()));
        assert (cell.getXPos() == 1 && cell.getYPos() == 1);
    }
}