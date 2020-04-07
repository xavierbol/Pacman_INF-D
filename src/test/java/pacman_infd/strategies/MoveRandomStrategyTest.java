package pacman_infd.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pacman_infd.games.Cell;

class MoveRandomStrategyTest extends StrategyTest {
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        strategy = new MoveRandomStrategy();
    }

    @Test
    @Override
    public void testGiveNextCell() {
        Cell cell = strategy.giveNextCell(gameWorld.getCell(0, 0));

        // 2 possible cells where the algo can choose in (1, 0) or (0, 1)
        Cell c1 = gameWorld.getCell(1, 0);
        Cell c2 = gameWorld.getCell(0, 1);

        assert (cell.equals(c1) || cell.equals(c2));
    }
}