package pacman_infd.strategies;

import pacman_infd.games.Cell;
import pacman_infd.strategies.ghost.ReturnHomeSrategy;

class ReturnHomeSrategyTest extends GhostStrategyTest {
    Cell homeCell;

    @Override
    public void setUp() {
        super.setUp();

        gameWorld.getCell(0, 0);
        ghostStrategy = new ReturnHomeSrategy(homeCell);
    }

    @Override
    public void testGiveNextCell() {
        Cell cell = ghostStrategy.giveNextCell(gameWorld.getCell(1, 0));

        assert(cell.equals(homeCell));
    }

}