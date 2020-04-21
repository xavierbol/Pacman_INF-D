package pacman_infd.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.games.Cell;
import pacman_infd.strategies.ghost.MoveRandomStrategy;

class MoveRandomStrategyTest extends GhostStrategyTest {
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        ghostStrategy = new MoveRandomStrategy();
    }

    @Test
    @Override
    public void testGiveNextCell() {
        Cell cell = ghostStrategy.giveNextCell(gameWorld.getCell(0, 0));

        // 2 possible cells where the algo can choose in (1, 0) or (0, 1)
        Cell c1 = gameWorld.getCell(1, 0);
        Cell c2 = gameWorld.getCell(0, 1);

        assert (cell.equals(c1) || cell.equals(c2));
    }
}